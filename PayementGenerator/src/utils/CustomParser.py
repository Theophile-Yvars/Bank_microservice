
from argparse import ArgumentParser


class CustomParser(ArgumentParser) :

    def __init__(self):
        super().__init__(description="TransactionsCreator")
        self.add_argument('-country', help="Where the transaction is made")
        self.add_argument('-number', help="The amount of transaction")
        self.add_argument('-client', help="The client ID")
        self.args = self.parse_args()