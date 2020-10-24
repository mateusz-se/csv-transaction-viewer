import { Injectable, EventEmitter } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, ReplaySubject, BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

export interface Transaction {
  id: number;
  title: string;
  transactionDate: string;
  pricePln: number;
  priceEur: number;
}


@Injectable({
  providedIn: 'root'
})
export class TransactionsTableService {
  transactionUrl = `${environment.apiUrl}transaction`;

  transactionUpdate = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient) {}

  getTransactions(
    pageIndex: number,
    pageSize: number
  ): Observable<Transaction[]> {
    const params = new HttpParams()
      .append('page', `${pageIndex}`)
      .append('results', `${pageSize}`);
    return this.http.get<Transaction[]>(`${this.transactionUrl}`, { params });
  }

  getTransactionCount(): Observable<number> {
    return this.http.get<{count: number}>(`${this.transactionUrl}/count`)
      .pipe(map(c => c.count));
  }

}
