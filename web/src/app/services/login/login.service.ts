import {Injectable} from "@angular/core";

import {HttpService} from "../http/http.service";
import {HttpErrorHandler} from "../http/httpErrorHandler.service";
import {NotifierService} from "../notificacao/notifier.service";
import {Message} from "../../model/Message";
import {LoggedUserService} from "../logged-user/logged-user.service";

@Injectable()
export class LoginService {

  constructor(private api:HttpService,
              private loggedUserService : LoggedUserService,
              private errorHandler : HttpErrorHandler,
              private notifier : NotifierService) { }

  login(user:string, password: string) : Promise<Message> {

    return new Promise((resolve, reject) => {
      this.api.post('/access/login', JSON.stringify({ "username": user, "password": password })).subscribe(data => {
        console.log('data', data);
        if (data.code != 200) {
          reject(data);
        } else {
          this.loggedUserService.setLogged(true);
        }
      resolve(data);
      }, error => reject(error));
    });
  }

  logout() : Promise<boolean> {
    return new Promise((resolve) => {
      this.api.post('/access/logout').subscribe(
        (data) => {

          this.loggedUserService.setLogged(false);
          this.notifier.success("You have logout from Hibernate Envers History!", "Success!");
          resolve(data);
        },
        error => this.errorHandler.handle(error))
    });

  }
}
