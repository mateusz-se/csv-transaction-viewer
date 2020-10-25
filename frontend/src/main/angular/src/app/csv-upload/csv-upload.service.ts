import { environment } from './../../environments/environment';
import { UploadedFileInfo } from './csv-upload.component';
import { HttpClient, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { filter } from 'rxjs/operators';
import { UploadType } from './upload-type';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CsvUploadService {
  constructor(private readonly httpClient: HttpClient) {}

  uploadFile(type: UploadType, file: any) {
    const url = `${environment.apiUrl}file/${type}`;
    const formData = new FormData();
    formData.append('file', file as any); // tslint:disable-next-line:no-any
    const req = new HttpRequest('POST', url, formData, {
      withCredentials: false
    });
    return this.httpClient.request(req)
      .pipe(filter(event => event instanceof HttpResponse));
  }

  currentLoadedFile(type: UploadType): Observable<UploadedFileInfo> {
    return this.httpClient.get<UploadedFileInfo>(`${environment.apiUrl}file/${type}/last`);
  }

}
