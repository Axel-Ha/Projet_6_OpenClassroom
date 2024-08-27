import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { RegisterRequest } from '../../interfaces/registerRequest.interface';
import { MatSnackBar } from '@angular/material/snack-bar';
import { passwordValidator} from 'src/app/validators/password.validators';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent {

  public registerForm = this.fb.group({
    email: ['', [Validators.required, Validators.email, Validators.max(50)]],
    username: ['',[Validators.required, Validators.min(8), Validators.max(20)]],
    password: ['',[Validators.required, passwordValidator()]],
  });

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router,
    private matSnackBar: MatSnackBar
  ) {}

  public submit(): void {
    const registerRequest = this.registerForm.value as RegisterRequest;
    this.authService.register(registerRequest).subscribe({
      next: _ => {
        this.router.navigate(['/login'])
        this.matSnackBar.open('Inscription réussie', 'Fermer', { duration: 3000 });
      },
      error: _ => {
        this.matSnackBar.open('Le nom d\'utilisateur ou l\'adresse email existe déjà', 'Fermer', { duration: 3000 });
      }
    });
  }

}
