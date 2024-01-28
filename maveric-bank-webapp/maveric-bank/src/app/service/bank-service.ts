import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable, OnInit } from "@angular/core";
import { Observable } from "rxjs";
import { User } from "../model/User";
import { Account } from "../model/Account";
import { Transaction } from "../model/Transaction";

@Injectable({ providedIn: "root" })
export class BankService {

    GATEWAY_URL: string = "http://localhost:8000";

    constructor(private http: HttpClient) {

    }


    getUsers(): Observable<User[]> {
        const getUsers = "/user-service/users";
        let queryParams = new HttpParams();
        queryParams.append("page", 1);
        queryParams.append("pageSize", 10);
        return this.http.get<User[]>(this.GATEWAY_URL.concat(getUsers), { params: queryParams });
    }

    getUserDetails(userId: string) {
        const getUserDetails = '/user-service/users/'+ userId;
        return this.http.get<User>(this.GATEWAY_URL.concat(getUserDetails));
    }

    getAccountDetails(customerId:string ,accountId: string) {
        const getAccountDetails = '/account-service/customers/'+customerId+'/accounts/'+ accountId;
        return this.http.get<Account>(this.GATEWAY_URL.concat(getAccountDetails));
    }

    getBalanceDetails(customerId:string ,accountId: string,balanceId:string) {
        const getBalanceDetails = '/balance-service/customers/'+customerId+'/accounts/'+ accountId+'/balances/'+balanceId;
        return this.http.get<number>(this.GATEWAY_URL.concat(getBalanceDetails));

    }

    getTransactions(customerId:string ,accountId: string): Observable<Transaction[]> {
        const getUsers = '/transaction-service/customers/'+customerId+'/accounts/'+ accountId+'/transactions';
        let queryParams = new HttpParams();
        queryParams.append("page", 1);
        queryParams.append("pageSize", 10);
        return this.http.get<Transaction[]>(this.GATEWAY_URL.concat(getUsers), { params: queryParams });
    }

}