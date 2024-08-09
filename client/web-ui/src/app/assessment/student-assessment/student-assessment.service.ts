import { Injectable } from '@angular/core';
import { AHttpOperation } from '../../common/utils';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { QuestionCriteria } from './questionCriteria';

@Injectable({
  providedIn: 'root',
})
export class StudentAssessmentService implements AHttpOperation {
  studentAssessmentUrl: string = `${environment.baseUrl}/assessment/student`;
  constructor(private httpClient: HttpClient) {}
  public find(criteria: any): Observable<any> {
    let params = new HttpParams();
    if (criteria.studentId != null) {
      params = params.set('studentId', criteria.studentId as string);
    }
    if (criteria.questionId != null) {
      params = params.set('questionId', criteria.questionId as string);
    }
    return this.httpClient.get(this.studentAssessmentUrl, { params });
  }
  public get(id: string): Observable<any> {
    throw new Error('Method not implemented.');
  }
  public post<StudentAssessmentDto>(
    model: StudentAssessmentDto
  ): Observable<any> {
    return this.httpClient.post(this.studentAssessmentUrl, model);
  }
  public put<T>(id: string, model: T): Observable<any> {
    throw new Error('Method not implemented.');
  }
  public delete(id: string): Observable<any> {
    throw new Error('Method not implemented.');
  }
}
