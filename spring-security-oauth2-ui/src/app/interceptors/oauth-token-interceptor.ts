import {Injectable} from "@angular/core";
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";

import {map, take, tap} from "rxjs/operators";
import {AuthService} from "../auth/auth.service";

@Injectable()
export class OAuthTokenInterceptor implements HttpInterceptor{

  constructor(private authService:AuthService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.authService.isAuthenticated()){
      const req = request.clone({
        //setParams:{access_token: JSON.parse(this.authService.getOAuthToken()).access_token },
        setHeaders:{
          //'Content-type': 'application/x-www-form-urlencoded; charset=utf-8',
          'Authorization': 'Bearer '+JSON.parse(this.authService.getOAuthToken()).access_token
          }
      });
      return next.handle(req).pipe(tap((event: HttpEvent<any>) => {}, (err: any) => {
        if (err instanceof HttpErrorResponse) {
          if(err.status == 401){
            console.log('User has an expired token, logging out user')
            this.authService.logoutUser();
          }
        }
      }));
    }
    return next.handle(request).pipe(tap((event: HttpEvent<any>) => {}, (err: any) => {
      if (err instanceof HttpErrorResponse) {
        console.log('User is not logged in')
      }
    }));
  }
}
