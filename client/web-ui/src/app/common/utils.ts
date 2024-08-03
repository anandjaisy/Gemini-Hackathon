import { IOptions } from '@falcon-ng/core';
import { Observable } from 'rxjs';

export function transformToKeyValuePair<T>(array: any[]): IOptions[] {
  return array.map((item) => ({
    key: String(item['id']),
    value: String(item['name']),
  }));
}

export abstract class AHttpOperation {
  abstract find(): Observable<any>;
  abstract get(id: string): Observable<any>;
  abstract post<T>(model: T): Observable<any>;
  abstract put<T>(id: string, model: T): Observable<any>;
  abstract delete(id: string): Observable<any>;
}
