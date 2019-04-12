import { Component, OnInit } from '@angular/core';
import {environment} from "../../environments/environment";
import {AuthService} from "../auth/auth.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  users: any;

  constructor(private http: HttpClient, private authService: AuthService) { }

  ngOnInit() {
    this.getUsers();
  }

  getUsers(){
    return this.http.get(environment.baseUrl + '/api/users')
      .subscribe( data => {
        console.log('test');
        this.users = data;
      });
  }
}
