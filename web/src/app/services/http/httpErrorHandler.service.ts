/*
 * Copyright© mai/2017 - Logique Sistemas®.
 * All rights reserved.
 */

import {Injectable} from "@angular/core";
import {Router} from "@angular/router";
import {NotifierService} from "../notificacao/notifier.service";
import {Message} from "../../model/Message";
import {LoggedUserService} from "../logged-user/logged-user.service";

/**
 * Serviço responsável por tratar os erros mais comuns que vem de {@link HttpService}
 */
@Injectable()
export class HttpErrorHandler {

  constructor(private router : Router, private notifier : NotifierService,
              private loggedUserService : LoggedUserService) { }

  handle(error : Message) {
    console.debug('error', error);
    if (error.code != null) {
      switch (error.code) {
        case 0:
          this.notifier.error(error.message, "We lost communication with the server!");
          break;
        case 500:
          this.notifier.error(error.message, "Internal Server Error!");
          break;
        case 401:
          this.loggedUserService.setLogged(false);
          this.router.navigate(["login"]);
          this.notifier.warn(error.message, "You must be logged in to continue!");
          break;

        case 403:
          this.notifier.warn(error.message, "Access Denied!");
          break;
        default:
        this.notifier.error(error.message, "Operation failed! ");

      }
    } else {
      this.notifier.error("We lost communication with the server!");
    }

  }

}
