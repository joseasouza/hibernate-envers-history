import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {NotifierService} from "../../services/notificacao/notifier.service";
import {HttpErrorHandler} from "../../services/http/httpErrorHandler.service";
import {LoggedUserService} from "../../services/logged-user/logged-user.service";
import {LoadingService} from "../../services/http/loading.service";
import {LoginService} from "../../services/login/login.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  returnUrl : string;
  username : string;
  password : string;

  constructor( private route: ActivatedRoute,
               private router : Router,
               private notifier : NotifierService,
               private loginService : LoginService,
               private errorHandler: HttpErrorHandler,
               private loggedUserService : LoggedUserService,
               public loading : LoadingService) { }

  ngOnInit() {
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '';
    if (this.loggedUserService.isUserLogged()) {
      console.debug('this.router.routerState', this.router.routerState);
      this.router.navigate([this.returnUrl], { queryParams: { }});
    }
  }

  login() {
    this.loading.show();
    this.loginService.login(this.username, this.password)
      .then(data => {
        this.loading.stop();
        console.debug('data', data);
        console.debug('[this.returnUrl]', [this.returnUrl]);
        this.router.navigate([this.returnUrl]);
      })
      .catch(error => {
        this.loading.stop();
        switch (error.code) {
          case 500:
          case 401:
            this.notifier.error(error.message, "Login failed!");
            break;
          default:
            this.errorHandler.handle(error);
            break;
        }
      });
  }

}
