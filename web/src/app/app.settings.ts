import {Injectable} from "@angular/core";
import {environment} from "../environments/environment";

@Injectable()
export class AppSettings {

  getApiEndPoint() {

    let endpoint = "";
    let baseHref = document.getElementsByTagName('base')[0].href;
    console.info("baseHref", baseHref);

    if (environment.production) {
       endpoint = baseHref;

    } else {
      endpoint = environment.origin;
    }

    while (endpoint.endsWith("/") && endpoint.length > 1) {
      endpoint = endpoint.substring(0, endpoint.length - 1);
    }

    return endpoint;
  }

}
