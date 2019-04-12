import { Component, OnInit } from '@angular/core';
import {Observable} from "rxjs";
import {NgForm} from "@angular/forms";
import {AuthService} from "../auth/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  isLoggedIn$: Observable<boolean>;
  isInvalidLogin$: Observable<boolean>;

  constructor(private authService: AuthService) { }

  ngOnInit() {
    window.sessionStorage.removeItem(this.authService.TOKEN_KEY);
    this.isLoggedIn$ = this.authService.isLoggedIn;
  }

  onSubmit(form: NgForm) {
    const userName = form.value.userName;
    const password = form.value.password;

    this.authService.loginUser(userName, password);
    this.isInvalidLogin$ = this.authService.isInvalidLogin;
  }

}
