import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class KeycloakAdminService {
  private baseUrl: string = environment.keyCloakApiBaseUrl;
  constructor(private httpClient: HttpClient) {}

  public getUsers(): Observable<any[]> {
    return this.httpClient.get<any[]>(`${this.baseUrl}users`);
  }

  getUserRoles(userId: string): Observable<any[]> {
    return this.httpClient.get<any[]>(
      `${this.baseUrl}users/${userId}/role-mappings/realm`
    );
  }
}
