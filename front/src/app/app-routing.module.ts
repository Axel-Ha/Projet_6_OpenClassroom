import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { MeComponent } from './pages/me/me.component';
import { AuthGuard } from './guards/auth.guards';
import { UnauthGuard } from './guards/unauth.guard';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { TopicComponent } from './pages/topics/topic.component';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '',
    component: HomeComponent 
  },
  {
    path: '',
    canActivate: [UnauthGuard],
    loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'topic',
    canActivate: [AuthGuard],
    component: TopicComponent
  },
  {
    path: 'posts',
    canActivate: [AuthGuard],
    loadChildren: () => import('./features/posts/post.module').then(m => m.PostsModule)
  },
  {
    path: 'me',
    canActivate: [AuthGuard],
    component: MeComponent
  },
  {
    path: 'not-found',
    component: NotFoundComponent
  },
  {
    path: '**',
    redirectTo: 'not-found'
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
