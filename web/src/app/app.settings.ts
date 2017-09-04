import {environment} from "../environments/environment";
export class AppSettings {

  static obterEnderecoHost() {
    if (environment.host) {
      return environment.host;
    } else {
      return  window.location.protocol + "//" + window.location.host;
    }
  }

  public static API_ENDPOINT = AppSettings.obterEnderecoHost() + "/" + environment.contexto;

}
