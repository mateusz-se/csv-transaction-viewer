import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';

import {
  TransactionsTableService,
  Transaction,
} from './transactions-table.service';
import { type } from 'os';
import { environment } from 'src/environments/environment';
import { UploadType } from '../csv-upload/upload-type';
import { HttpResponse } from '@angular/common/http';
import { UploadedFileInfo } from '../csv-upload/csv-upload.component';

describe('TransactionsTableService', () => {
  let service: TransactionsTableService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(TransactionsTableService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get getTransactions with parameters', (done) => {
    const pageIndex = 2;
    const pageSize = 25;
    const expectedTransactions: Transaction[] = [
      {
        id: 1,
        priceEur: 23,
        pricePln: 10,
        title: 'test',
        transactionDate: '2019-04-22',
      },
      {
        id: 2,
        priceEur: 33,
        pricePln: 15,
        title: 'test 2',
        transactionDate: '2019-04-23',
      }
    ];

    service.getTransactions(pageIndex, pageSize).subscribe((transactions) => {
      expect(transactions).toEqual(expectedTransactions);
      done();
    });

    const req = httpMock.expectOne(`${environment.apiUrl}transactions?page=${pageIndex}&results=${pageSize}`);
    expect(req.request.method).toBe('GET');
    req.flush(expectedTransactions);
  });

  it('should getTransactionsCount', (done) => {
    const expectedCount = 5;
    service
      .getTransactionCount()
      .subscribe(count => {
        expect(count).toEqual(expectedCount);
        done();
      });

    const req = httpMock.expectOne(`${environment.apiUrl}transactions/count`);
    expect(req.request.method).toBe('GET');
    req.flush({
      count: expectedCount
    });
  });
});
