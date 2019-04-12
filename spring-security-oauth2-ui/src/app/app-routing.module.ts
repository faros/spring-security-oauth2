import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {UsersComponent} from "./users/users.component";
import {AuthGuard} from "./auth/auth-guard.service";

const routes: Routes = [
  {path: '',          redirectTo: '/user', pathMatch: 'full'},
  {path: 'login',     component: LoginComponent},
  {path: 'user',      component: UsersComponent, canActivate:[AuthGuard]},
  {path: '**',        component: LoginComponent }
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
