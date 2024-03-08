import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AuthManagerComponent } from "./auth-manager/auth-manager.component";
import { AuthLogoutGuard } from "./auth-logout.guard";
import { EmptyComponent } from "../empty.component";
import { AuthTokenGuard } from "./auth-token.guard";

const routes: Routes = [
    { path: 'auth', component:  AuthManagerComponent},
    { path: 'auth/logout', canActivate: [AuthLogoutGuard], component: EmptyComponent},
    { path: 'auth/token', canActivate: [AuthTokenGuard], component: EmptyComponent},
  ];
  
  @NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
  })
  export class AuthRoutingModule {}
  