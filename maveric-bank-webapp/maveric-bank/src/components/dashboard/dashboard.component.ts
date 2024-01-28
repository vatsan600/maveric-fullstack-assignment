import { Component, OnInit } from '@angular/core';
import { BankService } from '../../app/service/bank-service';
import { User } from '../../app/model/User';
import { NgFor } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Account } from '../../app/model/Account';
import { Transaction } from '../../app/model/Transaction';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [NgFor],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {

  user!: User;
  account!: Account;
  balance!:number;
  transactions!:Transaction[];

  constructor(private bankService: BankService, private route: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.bankService.getUserDetails(this.getRouteParam('customerId'))
      .subscribe({
        next: value => {
          this.user = value;
        }
      });
      this.bankService.getAccountDetails(this.getRouteParam('customerId'),'6')
      .subscribe({
        next: value => {
          this.account = value;
        }
      });
      this.bankService.getBalanceDetails(this.getRouteParam('customerId'),'6','5')
      .subscribe({
        next: value => {
          this.balance = value;
        }
      });
      this.bankService.getTransactions(this.getRouteParam('customerId'),'6')
      .subscribe({
        next: value => {
          this.transactions = value;
        }
      });

  }

  getRouteParam(paramKey: string) {
    return this.route.snapshot.paramMap.get(paramKey) || '';
  }

}
