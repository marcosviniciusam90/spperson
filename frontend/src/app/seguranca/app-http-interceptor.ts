import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, from, of } from 'rxjs';
import { tap, catchError, mergeMap } from 'rxjs/operators';
import { LoadingService } from '../core/loading/loading.service';

import { AuthService } from './auth.service';

export class NotAuthenticatedError { }

@Injectable()
export class AppHttpInterceptor implements HttpInterceptor {

  constructor(
    private authService: AuthService,
    private loadingService: LoadingService
  ) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    this.loadingService.exibir(true);

    if (!req.url.includes('/oauth/token')) {
      if (this.authService.isAccessTokenInvalido()) {
        return from(this.authService.obterNovoAccessToken())
          .pipe(
            mergeMap(() => {
              if (this.authService.isAccessTokenInvalido()) {
                throw new NotAuthenticatedError();
              }

              req = req.clone({
                setHeaders: {
                  Authorization: `Bearer ${localStorage.getItem('token')}`
                }
              });

              return this.handleNextReq(req, next);
            })
          );
      }

      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      });
    }

    return this.handleNextReq(req, next);
  }

  private handleNextReq(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    return next.handle(req).pipe(
      tap(evt => {
        if (evt instanceof HttpResponse) {
          // alguma ação global quando ocorre sucesso
          this.loadingService.exibir(false);
        }
      }),
      catchError((err: any) => {
        if (err instanceof HttpErrorResponse) {
          // algum tratamento global quando ocorre erro
          this.loadingService.exibir(false);
        }
        return of(err);
      })
    );

  }

}
