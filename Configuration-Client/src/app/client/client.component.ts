import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {ToastrService} from "ngx-toastr";
import {AppService} from "../app.service";
import {TransactionGeneratorDto} from "../transaction/transactionGenerator.dto";

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./../app.component.scss']
})
export class ClientComponent {
  generateForm!: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpClient, private toaster: ToastrService, private transactionService: AppService) {
    this.generateForm = this.fb.group({
      count: [0, Validators.required],
    });
  }

  onSubmit() {
    if (this.generateForm.valid) {
      const config : TransactionGeneratorDto = this.generateForm.value;
      // this.generateTransactions(config);
      this.transactionService.generateClient(config.count);
    }
  }
}
