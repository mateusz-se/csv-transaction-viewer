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
import { UploadType } from './upload-type';

export interface UploadedFileInfo {
  name: string;
  url: string;
}

@Component({
  selector: 'app-csv-upload',
  templateUrl: './csv-upload.component.html',
  styleUrls: ['./csv-upload.component.scss'],
})
export class CsvUploadComponent implements OnInit {
  @Input() uploadType: UploadType;
  @Output() uploadSuccessful = new EventEmitter<UploadedFileInfo>();

  loadedFile: UploadedFileInfo = null;

  constructor(
    private readonly messageService: NzMessageService,
    private readonly csvUploadService: CsvUploadService
  ) {}

  ngOnInit(): void {
    this.csvUploadService.currentLoadedFile(this.uploadType)
    .pipe(take(1))
    .subscribe(file => {
      this.loadedFile = file;
    });
  }

  uploadRequest = (item: NzUploadXHRArgs) => {
    return this.csvUploadService.uploadFile(this.uploadType, item.file).subscribe(
      (event: HttpResponse<UploadedFileInfo>) => {
        this.loadedFile = event.body;
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
