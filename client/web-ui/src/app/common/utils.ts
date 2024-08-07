import { IOptions } from '@falcon-ng/core';
import { Observable } from 'rxjs';

export function transformToKeyValuePair<T>(array: any[]): IOptions[] {
  return array.map((item) => ({
    key: String(item['id']),
    value: String(item['name']),
  }));
}

export abstract class AHttpOperation {
  public abstract find<T>(params?: T): Observable<any>;
  public abstract get(id: string): Observable<any>;
  public abstract post<T>(model: T): Observable<any>;
  public abstract put<T>(id: string, model: T): Observable<any>;
  public abstract delete(id: string): Observable<any>;
}

export enum Role {
  TEACHER = 'teacher',
  STUDENT = 'student',
  ADMIN = 'admin',
}
