import { Injectable } from '@angular/core';
import { AHttpOperation } from '../common/utils';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { StudentAssessmentDto } from './student-assessment.dto';

@Injectable({
  providedIn: 'root',
})
export class StudentAssessmentService implements AHttpOperation {
  studentAssessmentUrl: string = `${environment.baseUrl}/assessment/student`;
  constructor(private httpClient: HttpClient) {}
  public find<T>(params?: T): Observable<any> {
    throw new Error('Method not implemented.');
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
