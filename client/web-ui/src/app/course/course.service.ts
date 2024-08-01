import { Injectable } from '@angular/core';
import { IGenericHttpClient } from '@falcon-ng/tailwind';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { CourseDto } from './courseDto';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class CourseService {
  courseUrl: string = `${environment.baseUrl}/course`;
  constructor(private httpClient: HttpClient) {}

  public find(): Observable<any> {
    return this.httpClient.get(this.courseUrl);
  }

  public get(id: string): Observable<any> {
    return this.httpClient.get(`${this.courseUrl}/${id}`);
  }
  public post(courseDto: CourseDto): Observable<any> {
    return this.httpClient.post(this.courseUrl, courseDto);
  }

  public put(id: string, courseDto: CourseDto): Observable<any> {
    return this.httpClient.patch(`${this.courseUrl}/${id}`, courseDto);
  }

  public delete(id: string): Observable<any> {
    return this.httpClient.delete(`${this.courseUrl}/${id}`);
  }
}
