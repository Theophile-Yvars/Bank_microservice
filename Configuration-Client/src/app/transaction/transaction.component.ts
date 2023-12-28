import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {TransactionGeneratorDto} from "./transactionGenerator.dto";
import {ToastrService} from "ngx-toastr";
import {environment} from "../../environments/environment";
import {TransactionDto} from "./transaction.dto";
import {AppService} from "../app.service";

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./../app.component.scss']
})
export class TransactionComponent {
  generateForm!: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpClient, private toaster: ToastrService, private transactionService: AppService) {
    this.generateForm = this.fb.group({
      client: [0, Validators.required],
      count: [0, Validators.required],
      percentage: [0, [Validators.required, Validators.max(100), Validators.min(0)]],
      originPercentage: [0, [Validators.required, Validators.max(100), Validators.min(0)]],
      targetCountry: ['', Validators.required],
      type: ['', Validators.required],
      origin: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.generateForm.valid) {
      const config : TransactionGeneratorDto = this.generateForm.value;
      this.transactionService.fetchAndGenerateTransactions(config);
    }
  }
}
