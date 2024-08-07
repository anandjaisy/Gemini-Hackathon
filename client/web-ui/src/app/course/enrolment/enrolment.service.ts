import { Injectable } from '@angular/core';
import { AHttpOperation } from '../../common/utils';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { EnrolmentDto } from './enrolment.dto';

@Injectable({
  providedIn: 'root',
})
export class EnrolmentService implements AHttpOperation {
  private enrolmentUrl: string = `${environment.baseUrl}/enrolment`;
  constructor(private httpClient: HttpClient) {}
  find<T>(params?: T): Observable<any> {
    return this.httpClient.get(this.enrolmentUrl);
  }
  get(id: string): Observable<any> {
    return this.httpClient.get(`${this.enrolmentUrl}/${id}`);
  }
  post<EnrolmentDto>(model: EnrolmentDto): Observable<any> {
    return this.httpClient.post(this.enrolmentUrl, model);
  }
  put<EnrolmentDto>(id: string, model: EnrolmentDto): Observable<any> {
    return this.httpClient.patch(`${this.enrolmentUrl}/${id}`, model);
  }
  delete(id: string): Observable<any> {
    return this.httpClient.delete(`${this.enrolmentUrl}/${id}`);
  }
}
