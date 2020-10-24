import { UploadedFileInfo } from './csv-upload.component';
import { HttpClient, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { filter } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class CsvUploadService {
  constructor(private readonly httpClient: HttpClient) {}

  uploadFile(url: string, file: any) {
    const formData = new FormData();
    formData.append('file', file as any); // tslint:disable-next-line:no-any
    const req = new HttpRequest('POST', url, formData, {
      reportProgress : true,
      withCredentials: false
    });
    return this.httpClient.request(req)
      .pipe(filter(event => event instanceof HttpResponse));
  }

  currentLoadedFile(url: string) {
    return this.httpClient.get<UploadedFileInfo>(`${url}/file/last`);
  }

}
