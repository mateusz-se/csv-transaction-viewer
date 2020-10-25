import { TestBed, async } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { TransactionsTableService } from './transactions-table/transactions-table.service';
describe('AppComponent', () => {

  const transactionsTableServiceStub = {
    updateTranasctions: () => {}
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent
      ], providers: [
        {
          provide: TransactionsTableService,
          useValue: transactionsTableServiceStub
        }
      ]
    }).compileComponents();
  }));
  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});
