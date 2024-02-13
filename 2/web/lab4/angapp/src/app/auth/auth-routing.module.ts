import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AuthManagerComponent } from "./auth-manager/auth-manager.component";
import { AuthLogoutGuard } from "./authlogout.guard";
import { EmptyComponent } from "../empty.component";

const routes: Routes = [
    { path: 'auth', component:  AuthManagerComponent},
    { path: 'auth/logout', canActivate: [AuthLogoutGuard], component: EmptyComponent},
  ];
  
  @NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
  })
  export class AuthRoutingModule {}
  