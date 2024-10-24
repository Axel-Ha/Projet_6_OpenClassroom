import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { Topic } from 'src/app/interfaces/topic.interface';
import { User } from 'src/app/interfaces/user.interface';
import { SessionService } from 'src/app/services/session.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {
  public sessionInfo: SessionInformation;
  public userId: number;
  public subscribedTopics$: Observable<Topic[]> = of([]);
  public meForm: FormGroup | undefined;

  constructor(
    private sessionService: SessionService,
    private userService: UserService,
    private router: Router,
    private formBuilder: FormBuilder,
    private matSnackBar: MatSnackBar
  ) {
    this.sessionInfo = this.sessionService.sessionInformation!;
    this.userId = this.sessionInfo!.id;
    this.subscribedTopics$ = this.userService.subscriptions$;
  }

  public ngOnInit(): void {
    this.initForm(this.sessionInfo);
    this.fetchSubscriptions();
  }

  private initForm(sessionInfo: SessionInformation): void {
    this.meForm = this.formBuilder.group({
      username: [sessionInfo.username, [Validators.required]],
      email: [sessionInfo.email, [Validators.required, Validators.email]]
    });
  }

  private fetchSubscriptions(): void {
    this.userService.getSubscriptions(this.userId).subscribe();
  }

  public save(): void {
    const user = this.meForm!.value as User;
    this.userService.update(this.userId, user).subscribe({
      next: (sessionInfo: SessionInformation) => {
        if(sessionInfo.message){
          this.matSnackBar.open(sessionInfo.message, 'Fermer', { duration: 3000 });
          return;
        }
        this.sessionInfo = sessionInfo;
        localStorage.setItem('token', sessionInfo.token);
        this.sessionService.logIn(sessionInfo);
        this.matSnackBar.open('Profil sauvegardé avec succès', 'Fermer', { duration: 3000 });
      }
    });
  }

  public logout(): void {
    this.sessionService.logOut();
    this.matSnackBar.open('Déconnexion avec succès', 'Fermer', { duration: 3000 });
    this.router.navigate(['']);
  }

  public unsubscribe(topicId: number) {
    this.userService.unsubscribe(this.userId, topicId).subscribe({
      next: () => {
        this.matSnackBar.open('Désabonnement réussi', 'Fermer', { duration: 3000 });
        this.fetchSubscriptions();
      }
    });
  }

}
