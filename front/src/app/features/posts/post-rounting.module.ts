import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
// import { DetailComponent } from './components/detail/detail.component';
// import { FormComponent } from './components/form/form.component';
import { ListComponent } from './components/list/list.component';

const routes: Routes = [
  { path: '', title: 'List Posts', component: ListComponent },
//   { path: 'create', title: 'Post - create', component: FormComponent },
//   { path: 'detail/:id', title: 'Post - detail', component: DetailComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SessionsRoutingModule {
}
