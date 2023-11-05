import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainRequestsComponent } from './main-requests/main-requests.component';
import { ReactiveFormsModule } from '@angular/forms';
import { SelectButtonModule } from 'primeng/selectbutton';
import { InputTextModule } from 'primeng/inputtext';
import { MatTableModule } from '@angular/material/table';

@NgModule({
  declarations: [
    MainRequestsComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    SelectButtonModule,
    InputTextModule,
    MatTableModule
  ]
})
export class MainModule { }
