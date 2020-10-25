import { TransactionsTableService } from './transactions-table.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed, inject } from '@angular/core/testing';

import { TransactionsTableComponent } from './transactions-table.component';
import { of } from 'rxjs';

describe('TransactionsTableComponent', () => {
  let component: TransactionsTableComponent;
  let fixture: ComponentFixture<TransactionsTableComponent>;

  const transactions = [
    {
      id: 1,
      title: 'test',
      transactionDate: '2020-10-11',
      pricePln: 111,
      priceEur: 25
    },
    {
      id: 2,
      title: 'test 2',
      transactionDate: '2020-10-11',
      pricePln: 211,
      priceEur: 55
    }
  ];

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransactionsTableComponent ],
      imports: [HttpClientTestingModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransactionsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get transactions count',
    inject([TransactionsTableService], (transactionsTableService: TransactionsTableService) => {
      spyOn(transactionsTableService, 'getTransactionCount').and.returnValue(of(11));
      component.transactionCount$.subscribe(count => {
        expect(count).toEqual(11);
      });
      component.ngOnInit();
    }));

  it('should get transactions',
    inject([TransactionsTableService], (transactionsTableService: TransactionsTableService) => {
      spyOn(transactionsTableService, 'getTransactions').and.returnValue(of(transactions));

      component.onQueryParamsChange({ pageIndex: 1, pageSize: 10, sort: null, filter: null});
      
      component.transactions$.subscribe(value => {
        expect(value).toEqual(transactions);
      });
    }));
});
