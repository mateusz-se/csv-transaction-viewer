import { UploadType } from './csv-upload/upload-type';
import { TransactionsTableService } from './transactions-table/transactions-table.service';
import { UploadedFileInfo } from './csv-upload/csv-upload.component';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  UploadType = UploadType;

  constructor(private readonly transactionsTableService: TransactionsTableService) { }

  uploadedFileChanged(file: UploadedFileInfo) {
    this.transactionsTableService.updateTranasctions();
  }
}
