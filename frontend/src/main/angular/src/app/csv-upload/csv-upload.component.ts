import { CsvUploadService } from './csv-upload.service';
import {
  HttpResponse,
} from '@angular/common/http';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {
  NzUploadChangeParam,
  NzUploadXHRArgs,
} from 'ng-zorro-antd/upload';
import { NzMessageService } from 'ng-zorro-antd/message';
import { take } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

export interface UploadedFileInfo {
  name: string;ó
  url: string;
}

export enum UploadType {
  TRANSACTION = 'transaction',
  RATE = 'rate',
}

@Component({
  selector: 'app-csv-upload',
  templateUrl: './csv-upload.component.html',
  styleUrls: ['./csv-upload.component.scss'],
})
export class CsvUploadComponent implements OnInit {
  @Input() uploadType: string;
  @Output() uploadSuccessful = new EventEmitter<UploadedFileInfo>();

  url: string;

  loadedFile: UploadedFileInfo = null;

  constructor(
    private readonly messageService: NzMessageService,
    private readonly csvUploadService: CsvUploadService
  ) {}

  ngOnInit(): void {
    this.url = `${environment.apiUrl}${this.uploadType}`;
    this.csvUploadService.currentLoadedFile(this.url)
    .pipe(take(1))
    .subscribe(file => {
      this.loadedFile = file;
    });
  }

  uploadRequest = (item: NzUploadXHRArgs) => {
    return this.csvUploadService.uploadFile(item.action, item.file).subscribe(
      (event: HttpResponse<{}>) => {
        this.loadedFile = event.body as UploadedFileInfo;
        item.onSuccess(event.body, item.file, event);
        this.uploadSuccessful.emit(this.loadedFile);
      },
      (err) => {
        this.loadedFile = null;
        item.onError(err, item.file);
      }
    );
  }

  downloadFile() {
    window.open(this.loadedFile.url);
  }

  handleChange(info: NzUploadChangeParam): void {
    const staus = info.file.status;
    if (staus === 'done') {
      this.messageService.success(`${info.file.name} file uploaded successfully`);
    } else if (staus === 'error') {
      this.messageService.error(`${info.file.name} file upload failed.`);
    }
  }
}
