import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'src/environments/environment';
import {TransactionGeneratorDto} from "./transaction/transactionGenerator.dto";
import {TransactionDto} from "./transaction/transaction.dto"; // Adjust the path as needed

@Injectable({
  providedIn: 'root'
})
export class AppService {
  private clients: any[] = []; // Holds the fetched clients

  constructor(private http: HttpClient, private toaster: ToastrService) {}

  // Fetch clients before generating transactions
  public fetchAndGenerateTransactions(config: TransactionGeneratorDto): void {
    this.http.get<any[]>(environment.clientUrl + '/clients').subscribe({
      next: (clientList) => {
        this.clients = clientList;
        this.generateTransactions(config);
      },
      error: (error) => {
        this.toaster.error('Error fetching clients:', JSON.stringify(error));
        console.error('Error fetching clients:', error);
      }
    });
  }

  private generateTransactions(config: TransactionGeneratorDto): void {
    if (!this.clients || this.clients.length === 0) {
      this.toaster.error('No clients available to generate transactions.');
      return;
    }

    const targetCount = Math.floor(config.count * (config.percentage / 100));
    const originCount = Math.floor(config.count * (config.originPercentage / 100));

    for (let i = 0; i < config.count; i++) {
      const transaction = new TransactionDto();
      const client = this.clients[this.randomIntFromInterval(0, this.clients.length - 1)];
      const target = this.clients[this.randomIntFromInterval(0, this.clients.length - 1)];

      transaction.name = `Transaction ${i}`;
      transaction.client = config.client;
      transaction.price = Math.round((Math.random() * 100) * 100.0) / 100.0;
      transaction.type = config.type;
      transaction.origine = i < originCount ? config.origin : (config.origin == "internet" ? "direct": "internet");
      transaction.targetClient = target.id;
      transaction.country = i < targetCount ? config.targetCountry : 'other';

      // Post each transaction
      this.http.post(environment.clientUrl + "/transaction/postTransaction", transaction, { responseType: "text" })
        .subscribe({
          next: (response) => {
            this.toaster.success('Transaction saved: ', response);
          },
          error: (error) => {
            this.toaster.error('Error saving transaction: ', JSON.stringify(error));
            console.error(error);
          }
        });
    }
  }

  private randomIntFromInterval(min: number, max: number): number {
    // min and max included
    return Math.floor(Math.random() * (max - min + 1) + min);
  }

  public generateClient(count: number): void {
    for (let i = 0; i < count; i++) {
      // Generate a random string prefix for the client name
      const randomPrefix = Math.random().toString(36).substring(2, 8); // Random string of length 6
      const clientName = `${randomPrefix}_Client_${i}`;

      // POST request to the client registration endpoint with the random client name
      this.http.post(environment.clientUrl + '/clients/register/' + encodeURIComponent(clientName), {})
        .subscribe({
          next: (response) => {
            this.toaster.success('Client created: ', JSON.stringify(response));
          },
          error: (error) => {
            this.toaster.error('Error creating client: ', JSON.stringify(error));
            console.error('Error creating client:', error);
          }
        });
    }
  }
}
