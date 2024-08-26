import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './features/auth/services/auth.service';
import { SessionService } from './services/session.service';
import { SessionInformation } from './interfaces/sessionInformation.interface';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  constructor(
    private authService: AuthService,
    private router: Router,
    private sessionService: SessionService) {
  }
  
  // public ngOnInit(): void {
  //   this.autoLog();
  // }

  // public autoLog(): void {
  //   this.authService.me().subscribe(
  //     (sessionInfo: SessionInformation) => {
  //       this.sessionService.logIn(sessionInfo);
  //     });
  // }


  public $isLogged(): Observable<boolean> {
    return this.sessionService.$isLogged();
  }

  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate([''])
  }

}
