import { UploadType } from './csv-upload/csv-upload.component';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  UploadType = UploadType;
}
