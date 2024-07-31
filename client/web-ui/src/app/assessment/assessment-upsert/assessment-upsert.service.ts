import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AssessmentDto } from '../AssessmentDto';

@Injectable({
  providedIn: 'root',
})
export class AssessmentUpsertService {
  courseUrl: string = `${environment.baseUrl}/assessment`;
  constructor(private httpClient: HttpClient) {}

  public find(): Observable<any> {
    return this.httpClient.get(this.courseUrl);
  }

  public get(id: string): Observable<any> {
    return this.httpClient.get(`${this.courseUrl}/${id}`);
  }
  public post(courseDto: AssessmentDto): Observable<any> {
    return this.httpClient.post(this.courseUrl, courseDto);
  }

  public put(id: string, courseDto: AssessmentDto): Observable<any> {
    return this.httpClient.patch(`${this.courseUrl}/${id}`, courseDto);
  }

  public delete(id: string): Observable<any> {
    return this.httpClient.delete(`${this.courseUrl}/${id}`);
  }
}
