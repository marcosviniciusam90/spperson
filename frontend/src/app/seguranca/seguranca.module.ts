import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';

import { JwtModule, JwtHelperService } from '@auth0/angular-jwt';

import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { MoneyHttpInterceptor } from './money-http-interceptor';

import { LoginComponent } from './login/login.component';
import { SegurancaRoutingModule } from './seguranca-routing.module';
import { AuthGuard } from './auth.guard';

export function tokenGetter(): string {
  return localStorage.getItem('token');
}

@NgModule({
  declarations: [
    LoginComponent
  ],
  imports: [
    CommonModule,
    SegurancaRoutingModule,
    InputTextModule,
    ButtonModule,
    FormsModule,

    JwtModule.forRoot({
      config: {
        tokenGetter,
        allowedDomains: ['localhost:8080'],
        disallowedRoutes: ['http://localhost:8080/oauth/token']
      }
    })
  ],
  providers: [
    JwtHelperService,

    {
      provide: HTTP_INTERCEPTORS,
      useClass: MoneyHttpInterceptor,
      multi: true
    },

    AuthGuard
  ]
})
export class SegurancaModule { }
