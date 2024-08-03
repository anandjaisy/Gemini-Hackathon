import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { AHttpOperation } from '../../common/utils';
import { QuestionDto } from './QuestionDto';

@Injectable({
  providedIn: 'root',
})
export class QuestionService extends AHttpOperation {
  questionUrl: string = `${environment.baseUrl}/question`;
  constructor(private httpClient: HttpClient) {
    super();
  }

  public find(): Observable<any> {
    return this.httpClient.get(this.questionUrl);
  }

  public get(id: string): Observable<any> {
    return this.httpClient.get(`${this.questionUrl}/${id}`);
  }
  public post<QuestionDto>(questionDto: QuestionDto): Observable<any> {
    return this.httpClient.post(this.questionUrl, questionDto);
  }

  public put<QuestionDto>(
    id: string,
    questionDto: QuestionDto
  ): Observable<any> {
    return this.httpClient.patch(`${this.questionUrl}/${id}`, questionDto);
  }

  public delete(id: string): Observable<any> {
    return this.httpClient.delete(`${this.questionUrl}/${id}`);
  }
}
