import { UploadType } from './upload-type';
import { environment } from './../../environments/environment';
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { CsvUploadService } from './csv-upload.service';
import { HttpResponse } from '@angular/common/http';
import { UploadedFileInfo } from './csv-upload.component';

describe('CsvUploadService', () => {
  let service: CsvUploadService;
  let httpMock: HttpTestingController;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ]
    });
    service = TestBed.inject(CsvUploadService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get current loaded file from backend', (done) => {
    const type = UploadType.TRANSACTION;
    const expectedFileInfo = {
      name: 'test',
      url: 'http://test.asd/test.csv'
    };

    service.currentLoadedFile(type).subscribe(file => {
      expect(file).toEqual(expectedFileInfo);
      done();
    });

    const req = httpMock.expectOne(`${environment.apiUrl}file/${type}/last`);
    expect(req.request.method).toBe('GET');
    req.flush(expectedFileInfo);
  });

  it('should sent file to backend', (done) => {
    const type = UploadType.TRANSACTION;
    const sentFile = {
      name: 'test'
    };
    const expectedFileInfo = {
      name: 'test',
      url: 'http://test.asd/test.csv'
    };

    service.uploadFile(type, sentFile).subscribe((event: HttpResponse<UploadedFileInfo>) => {
      expect((event as any).body).toEqual(expectedFileInfo);
      done();
    });

    const req = httpMock.expectOne(`${environment.apiUrl}file/${type}`);
    expect(req.request.method).toBe('POST');
    req.flush(expectedFileInfo);
  });
});
