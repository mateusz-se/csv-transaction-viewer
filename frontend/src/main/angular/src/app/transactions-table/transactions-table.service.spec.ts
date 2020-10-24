import { TestBed } from '@angular/core/testing';

import { TransactionsTableService } from './transactions-table.service';

describe('TransactionsTableService', () => {
  let service: TransactionsTableService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TransactionsTableService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
