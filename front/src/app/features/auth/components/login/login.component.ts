import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { SessionService } from 'src/app/services/session.service';
import { LoginRequest } from '../../interfaces/loginRequest.interface';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  public loginForm = this.formBuilder.group({
    email: ['', [Validators.required,Validators.email]],
    password: ['', [Validators.required]]
  });

  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private router: Router,
    private sessionService: SessionService,
    private matSnackBar: MatSnackBar,
  ) { }

  public submit(): void {
    const loginRequest = this.loginForm.value as LoginRequest;
    this.authService.login(loginRequest).subscribe({
      next: (sessionInfo: SessionInformation) => {
        localStorage.setItem('token', sessionInfo.token);
        this.sessionService.logIn(sessionInfo);
        this.router.navigate(['/posts']);
      },
      error: _ => {
        this.matSnackBar.open('Email ou mot de passe incorrect', 'Fermer', { duration: 3000 });
      }
    });
  }
}  