import { Injectable } from '@angular/core';
import { AHttpOperation } from '../../../common/utils';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class StudentScoreService implements AHttpOperation {
  studentAssessmentScoreUrl: string = `${environment.baseUrl}/scoring/assessment`;
  constructor(private httpClient: HttpClient) {}
  public find(criteria: any): Observable<any> {
    let params = new HttpParams();
    if (criteria.studentId != null) {
      params = params.set('studentId', criteria.studentId as string);
    }
    if (criteria.questionId != null) {
      params = params.set('questionId', criteria.questionId as string);
    }
    return this.httpClient.get(this.studentAssessmentScoreUrl, { params });
  }
  public get(id: string): Observable<any> {
    throw new Error('Method not implemented.');
  }
  public post<T>(model: T): Observable<any> {
    throw new Error('Method not implemented.');
  }
  public put<T>(id: string, model: T): Observable<any> {
    throw new Error('Method not implemented.');
  }
  public delete(id: string): Observable<any> {
    throw new Error('Method not implemented.');
  }
}
