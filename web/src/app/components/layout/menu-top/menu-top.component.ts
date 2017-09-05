import { Component, OnInit } from '@angular/core';
import {LoginService} from "../../../services/login/login.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-menu-top',
  templateUrl: './menu-top.component.html',
  styleUrls: ['./menu-top.component.css']
})
export class MenuTopComponent implements OnInit {

  constructor(private loginService : LoginService,
              private router : Router) { }

  ngOnInit() {
  }

  logout() {
    this.loginService.logout().then(() => {
      this.router.navigate(['/login']);
    });
  }

}
