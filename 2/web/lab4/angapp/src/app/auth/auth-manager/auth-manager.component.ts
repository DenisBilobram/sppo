import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-auth-manager',
  templateUrl: './auth-manager.component.html',
  styleUrls: ['./auth-manager.component.scss']
})
export class AuthManagerComponent {

  loginForm: FormGroup;
  registerForm: FormGroup;
  showLogin = true;
  errorMessage: any;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });

    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onLogin() {
    if (this.loginForm.valid) {
      const username = this.loginForm.get('username')?.value ?? '';
      const password = this.loginForm.get('password')?.value ?? '';
      this.authService.login(username, password).subscribe(
        response => {
          localStorage['id_token'] = response.jwt;
          localStorage['username'] = response.username;
          this.router.navigate(["/main"]);
        },
        error => {
          this.errorMessage = error.error;
        }
      );
    }
  }

  onRegister() {
    if (this.registerForm.valid) {
      const username = this.registerForm.get('username')?.value ?? '';
      const password = this.registerForm.get('password')?.value ?? '';
      const passwordConfirm = this.registerForm.get('confirmPassword')?.value ?? '';

      if (password !== passwordConfirm) {
        this.errorMessage = "Пароль и подтверждение пароля не сопадают."
        return;
      }

      this.authService.register(username, password).subscribe(
        response => {
          localStorage['id_token'] = response.jwt;
          localStorage['username'] = response.username;
          this.router.navigate(["/main"]);
        },
        error => {
          this.errorMessage = error.error
        }
      );
    }
  }
}