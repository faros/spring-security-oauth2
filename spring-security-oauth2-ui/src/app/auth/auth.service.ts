import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {Router} from "@angular/router";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  readonly TOKEN_KEY = "OAuth-token";
  private loggedIn = new BehaviorSubject<boolean>(this.isAuthenticated());
  private invalidLogin = new BehaviorSubject<boolean>(false);

  constructor(private router: Router,
              private http: HttpClient
  ) { }

  loginUser(userName: string, password: string) {
    this.invalidLogin.next(false);

    const body = new HttpParams()
      .set('username', userName)
      .set('password', password)
      .set('grant_type', 'password');

    const headers = {
      headers: new HttpHeaders({
        'Authorization': 'Basic '+btoa("web-client:web-client-secret"),
        'Content-type': 'application/x-www-form-urlencoded; charset=utf-8'
      })
    };

    this.http.post(environment.baseUrl + '/oauth/token', body.toString(), headers)
      .subscribe(
        data => {
          this.saveOAuthToken(data);
          this.router.navigate(['/user']);
        },
        err => {
          this.invalidLogin.next(true);
        }
      );
  }

  saveOAuthToken(token){
    window.sessionStorage.setItem(this.TOKEN_KEY, JSON.stringify(token));
    this.loggedIn.next(this.isAuthenticated());
  }

  getOAuthToken() : string {
    return window.sessionStorage.getItem(this.TOKEN_KEY);
  }

  isAuthenticated() {
    return (this.getOAuthToken() != null);
  }

  logoutUser() {
    window.sessionStorage.removeItem(this.TOKEN_KEY);
    this.router.navigate(['/login']);
    this.loggedIn.next(this.isAuthenticated());
  }

  get isLoggedIn() {
    return this.loggedIn.asObservable();
  }

  get isInvalidLogin() {
    return this.invalidLogin.asObservable();
  }

}
