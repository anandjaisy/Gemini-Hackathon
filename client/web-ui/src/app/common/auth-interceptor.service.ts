import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '@falcon-ng/tailwind';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthInterceptorService implements HttpInterceptor {
  constructor(private authService: AuthService) {}
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    // Get the auth token from the service.
    const token = this.authService.getAccessToken();

    // Clone the request and replace the original headers with
    // cloned headers, updated with the authorization.

    const authReq = req.clone({
      headers: req.headers.set('Authorization: Bearer', token),
    });

    // send cloned request with header to the next handler.
    return next.handle(authReq);
  }
}
