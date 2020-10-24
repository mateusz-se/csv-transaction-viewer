import { CsvUploadComponent } from './csv-upload.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NzUploadModule } from 'ng-zorro-antd/upload';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzButtonModule } from 'ng-zorro-antd/button';

@NgModule({
  declarations: [CsvUploadComponent],
  imports: [
    CommonModule,
    NzUploadModule,
    NzIconModule,
    NzButtonModule
  ],
  exports: [CsvUploadComponent]
})
export class CsvUploadModule { }
