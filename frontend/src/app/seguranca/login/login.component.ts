import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';

import { ErrorHandlerService } from 'src/app/core/error-handler.service';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(
    public authService: AuthService,
    private errorHandlerService: ErrorHandlerService,
    private router: Router,
    private title: Title
  ) {}

  ngOnInit(): void {
    this.title.setTitle('Login');
  }

  login(usuario: string, senha: string): void {
    this.authService.login(usuario, senha)
      .then(() => {
        this.router.navigate(['/lancamentos']);
      })
      .catch(erro => this.errorHandlerService.handle(erro));
  }
}
