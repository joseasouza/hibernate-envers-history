/*
 * Copyright© mai/2017 - Logique Sistemas®.
 * All rights reserved.
 */

import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {LoggedUserService} from "../logged-user/logged-user.service";

@Injectable()
export class GuardService implements CanActivate {

  constructor(private router: Router, private loggedUserService: LoggedUserService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (this.loggedUserService.isUserLogged()) {
      return true;
    } else {
      console.debug('User is not authenticated, redirecting to login page...');
      this.router.navigate(['/login'], {queryParams: {}});
      return false;
    }
  }

  canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    return this.canActivate(route, state);
  }

}
