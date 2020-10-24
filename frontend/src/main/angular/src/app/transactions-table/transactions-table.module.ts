import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TransactionsTableComponent } from './transactions-table.component';
import { NzTableModule } from 'ng-zorro-antd/table';


@NgModule({
  declarations: [TransactionsTableComponent],
  imports: [
    CommonModule,
    NzTableModule,
    HttpClientModule
  ],
  exports: [TransactionsTableComponent]
})
export class TransactionsTableModule { }
