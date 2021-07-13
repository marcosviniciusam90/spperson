import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

      const roles = route.data.roles;

      if (this.authService.isAccessTokenInvalido()) {
        console.log('Navegação com access token inválido. Obtendo um novo...');

        return this.authService.obterNovoAccessToken()
        .then(() => {

          if (this.authService.isAccessTokenInvalido()) {
            this.router.navigate(['/login']);
            return false;
          }

          if (this.isNaoAutorizado(roles)) {
            this.router.navigate(['/nao-autorizado']);
            return false;
          }

          return true;
        });

      } else if (this.isNaoAutorizado(roles)) {
        this.router.navigate(['/nao-autorizado']);
        return false;
      }


      return true;
  }

  private isNaoAutorizado(roles: any): boolean {
    return roles && !this.authService.temQualquerPermissao(roles);
  }

}
