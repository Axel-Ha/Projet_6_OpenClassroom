import { Component, ViewChild } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { AuthService } from './features/auth/services/auth.service';
import { SessionService } from './services/session.service';
import { SessionInformation } from './interfaces/sessionInformation.interface';
import { Observable } from 'rxjs';
import { MatSidenav } from '@angular/material/sidenav';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  public hideToolbar: boolean = false;

  @ViewChild('sidenav') sidenav: MatSidenav | undefined;
  
  constructor(
    private authService: AuthService,
    private router: Router,
    private sessionService: SessionService) {
  }

  public ngOnInit(): void {
    this.autoLog();
    this.router.events.subscribe(event => {
      if(event instanceof NavigationEnd) {
        this.isToolbarVisible();
      }
    });
  }

  public autoLog(): void {
    this.authService.me().subscribe(
      (sessionInfo: SessionInformation) => {
        this.sessionService.logIn(sessionInfo);
      });
  }

  public $isLogged(): Observable<boolean> {
    return this.sessionService.$isLogged();
  }

  private isToolbarVisible(): void {
    const currentRoute = this.router.url;
    const isMobile = window.innerWidth <= 600;
    const isHomepage = currentRoute === '/';

    this.hideToolbar = (isMobile && (currentRoute.includes('auth/login') || currentRoute.includes('auth/register') || isHomepage)) || (!isMobile && isHomepage);
  }

  public closeSidenav() {
    this.sidenav?.close();
  }

}
