import requests
import random
import argparse
from typing import List, Dict, Any


class TransactionGenerator:
    def __init__(self, base_url: str):
        self.base_url = base_url

    def fetch_clients(self) -> List[Dict[str, Any]]:
        response = requests.get(f'{self.base_url}/clients/clients')
        response.raise_for_status()
        return response.json()

    def post_transaction(self, transaction: Dict[str, Any]):
        response = requests.post(f'{self.base_url}/clients/transaction/postTransaction', json=transaction)
        response.raise_for_status()
        return response.text

    def generate_transactions(self, config: Dict[str, Any], clients: List[Dict[str, Any]]):
        target_count = int(config['count'] * (config['percentage'] / 100))
        origin_count = int(config['count'] * (config['originPercentage'] / 100))

        for i in range(config['count']):
            transaction = {
                'name': f'Transaction {i}',
                'client': config['client'],
                'price': round(random.random() * 100, 2),
                'type': config['type'],
                'origine': config['origin'] if i < origin_count else (
                    'direct' if config['origin'] == 'internet' else 'internet'),
                'targetClient': random.choice(clients)['id'],
                'country': config['targetCountry'] if i < target_count else 'other'
            }
            print(self.post_transaction(transaction))

    def generate_client(self, count: int):
        for i in range(count):
            random_prefix = ''.join(random.choices('abcdefghijklmnopqrstuvwxyz', k=6))
            client_name = f'{random_prefix}_Client_{i}'
            response = requests.post(f'{self.base_url}/clients/register/{client_name}', {})
            response.raise_for_status()
            print(f'Client created: {response.text}')

def validate_percentage(value, min_value=0, max_value=100):
    if not min_value <= value <= max_value:
        raise argparse.ArgumentTypeError(f"Percentage must be between {min_value} and {max_value}.")
    return value

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Generate transactions.')
    parser.add_argument('--client', type=int, required=True, help="Customer id for which transactions will be generated")
    parser.add_argument('--count', type=int, required=True, help="The total amount of transactions that will be generated")
    parser.add_argument('--countryPercentage', type=float, required=True, help="Percentage of transactions that will be in the specified country",)
    parser.add_argument('--targetCountry', type=str, required=True, help="The name of target country")
    parser.add_argument('--origin', type=str, required=True, help="The type of origin: <internet | direct>")
    parser.add_argument('--type', type=str, required=True, help="The type of the transaction <entreprise|particulier>")
    parser.add_argument('--originPercentage', type=float, required=True, help="Percentage of transactions that have the specified origin")

    args = parser.parse_args()
    validate_percentage(args.countryPercentage)
    validate_percentage(args.originPercentage)
    config = {
        'client': args.client,
        'count': args.count,
        'percentage': args.countryPercentage,
        'targetCountry': args.targetCountry,
        'origin': args.origin,
        'type': args.type,
        'originPercentage': args.originPercentage
    }

    generator = TransactionGenerator('http://localhost:8088')
    clients = generator.fetch_clients()
    generator.generate_transactions(config, clients)
