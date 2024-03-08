import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthManagerComponent } from './auth-manager/auth-manager.component';
import { AuthRoutingModule } from './auth-routing.module';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    AuthManagerComponent
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    ReactiveFormsModule,
  ]
  
})
export class AuthModule { }
