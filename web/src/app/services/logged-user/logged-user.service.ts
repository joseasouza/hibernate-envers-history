/*
 * Copyright© mai/2017 - Logique Sistemas®.
 * All rights reserved.
 */

import {Injectable} from "@angular/core";
const LOGGED_USER = "loggedUser";

@Injectable()
export class LoggedUserService {

  setLogged(isUserLogged): void {
    if (isUserLogged) {
      localStorage.setItem(LOGGED_USER, JSON.stringify(true));
    } else {
      localStorage.removeItem(LOGGED_USER);
    }
  }

  isUserLogged() : boolean {
    let userJson = localStorage.getItem(LOGGED_USER);
    try {
      return userJson ? JSON.parse(userJson) : null;
    } catch (error) {
      return false;
    }
  }
}
