import { Component, OnInit, OnDestroy } from '@angular/core';
import { TransactionsTableService, Transaction } from './transactions-table.service';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { ReplaySubject, of } from 'rxjs';
import { takeUntil, tap, switchMap, finalize } from 'rxjs/operators';

@Component({
  selector: 'app-transactions-table',
  templateUrl: './transactions-table.component.html',
  styleUrls: ['./transactions-table.component.scss']
})
export class TransactionsTableComponent implements OnInit, OnDestroy {
  private destroyed$: ReplaySubject<boolean> = new ReplaySubject(1);
  transactionCount$ = of(0);
  transactions: Transaction[] = [];
  loading = true;
  pageSize = 10;
  pageIndex = 1;

  constructor(private transactionsTableService: TransactionsTableService) {}

  ngOnInit(): void {
    this.transactionsTableService.getUpdateTransactions()
    .pipe(
      takeUntil(this.destroyed$)
    )
    .subscribe(() => {
      this.loadDataFromServer(this.pageIndex, this.pageSize);
      this.transactionCount$ = this.transactionsTableService.getTransactionCount();
    });
  }

  ngOnDestroy(): void {
    this.destroyed$.next(true);
    this.destroyed$.complete();
  }

  loadDataFromServer(pageIndex: number, pageSize: number): void {
    this.loading = true;
    this.transactionsTableService.getTransactions(pageIndex, pageSize)
    .pipe(
      takeUntil(this.destroyed$),
      finalize(() => this.loading = false)
    )
    .subscribe(data => this.transactions = data);
  }


  onQueryParamsChange({ pageSize, pageIndex }: NzTableQueryParams): void {
    this.loadDataFromServer(pageIndex, pageSize);
  }

}
