import { Component, OnInit, OnDestroy } from '@angular/core';
import { TransactionsTableService, Transaction } from './transactions-table.service';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { ReplaySubject, of, Observable } from 'rxjs';
import { takeUntil, tap, switchMap, finalize } from 'rxjs/operators';

@Component({
  selector: 'app-transactions-table',
  templateUrl: './transactions-table.component.html',
  styleUrls: ['./transactions-table.component.scss']
})
export class TransactionsTableComponent implements OnInit, OnDestroy {
  private destroyed$: ReplaySubject<boolean> = new ReplaySubject(1);
  transactionCount$ = of(0);
  transactions$: Observable<Transaction[]> = of([]);
  loading = true;
  pageSize = 10;
  pageIndex = 1;

  constructor(private transactionsTableService: TransactionsTableService) {}

  ngOnInit(): void {
    this.transactionCount$ = this.transactionsTableService.getUpdateTransactions()
    .pipe(
      takeUntil(this.destroyed$),
      switchMap(() => this.transactionsTableService.getTransactionCount())
    );
  }

  ngOnDestroy(): void {
    this.destroyed$.next(true);
    this.destroyed$.complete();
  }

  getTransactions(pageIndex: number, pageSize: number) {
    return this.transactionsTableService.getUpdateTransactions()
    .pipe(
      takeUntil(this.destroyed$),
      switchMap(() => this.loadDataFromServer(pageIndex, pageSize))
    );
  }

  loadDataFromServer(pageIndex: number, pageSize: number) {
    this.loading = true;
    return this.transactionsTableService.getTransactions(pageIndex, pageSize)
    .pipe(
      takeUntil(this.destroyed$),
      finalize(() => this.loading = false)
    );
  }

  onQueryParamsChange({ pageSize, pageIndex }: NzTableQueryParams): void {
    this.transactions$ = this.getTransactions(pageIndex, pageSize);
  }

}
