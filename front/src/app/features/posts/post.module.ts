import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ListComponent } from './components/list/list.component';
import { SessionsRoutingModule } from './post-rounting.module';
// import { MatCardModule } from '@angular/material/card';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatButtonModule } from '@angular/material/button';
// import { MatInputModule } from '@angular/material/input';
// import { FlexLayoutModule } from '@angular/flex-layout';
// import { MatIconModule } from '@angular/material/icon';

// const materialModules = [
//   MatButtonModule,
//   MatCardModule,
//   MatFormFieldModule,
//   MatIconModule,
//   MatInputModule
// ]

@NgModule({
  declarations: [
    ListComponent,

  ],
  imports: [
    SessionsRoutingModule,
    CommonModule,
    // FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule,
    // ...materialModules
  ]
})
export class PostModule { }
