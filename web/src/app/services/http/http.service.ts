import {Injectable} from "@angular/core";
import {Headers, Http, RequestOptions, Response} from "@angular/http";

import {Observable} from "rxjs/Observable";

import "rxjs/add/operator/map";
import "rxjs/add/observable/throw";
import "rxjs/add/operator/catch";
import "rxjs/add/operator/finally";
import {LoadingService} from "./loading.service";
import {AppSettings} from "../../app.settings";


@Injectable()
export class HttpService {

  headers: Headers;
  options: RequestOptions;
  cabecalhoAlterado = false;

  constructor(private http: Http, private loading : LoadingService) {
    this.headers = new Headers({'Content-Type': 'application/json', 'Accept' : "application/json"});
    this.options = new RequestOptions({headers: this.headers, withCredentials: true});
  }

  post(url: string, parametros ?: any) {
    this.gerarCabecalho();
    this.loading.exibir();
    return this.http
      .post(AppSettings.API_ENDPOINT + url, parametros, this.options)
      .map(this.extractData)
      .catch(this.handleError)
      .finally(() => this.loading.parar());
  }

  get(url: string) {
    this.gerarCabecalho();
    this.loading.exibir();
    return this.http
      .get(AppSettings.API_ENDPOINT + url, this.options)
      .map(this.extractData)
      .catch(this.handleError)
      .finally(() => {this.loading.parar();});
  }

  getBackground(url: string) {
    this.gerarCabecalho();
    return this.http
      .get(AppSettings.API_ENDPOINT + url, this.options)
      .map(this.extractData)
      .catch(this.handleError);
  }

  put(url: string, parametros ?: any) {
    this.gerarCabecalho();
    this.loading.exibir();
    return this.http
      .put(AppSettings.API_ENDPOINT + url, parametros, this.options)
      .map(this.extractData)
      .catch(this.handleError)
      .finally(() => this.loading.parar());
  }

  delete(url: string) {
    this.gerarCabecalho();
    this.loading.exibir();
    return this.http
      .delete(AppSettings.API_ENDPOINT + url, this.options)
      .map(this.extractData)
      .catch(this.handleError)
      .finally(() => this.loading.parar());
  }

  postUpload(url: string, formData: FormData) {
    this.cabecalhoAlterado = true;

    this.headers = new Headers();
    this.headers.append('Accept', 'application/json');
    this.options = new RequestOptions({headers: this.headers});

    this.loading.exibir();
    return this.http
      .post(AppSettings.API_ENDPOINT + url, formData, this.options)
      .map(this.extractData)
      .catch(this.handleError)
      .finally(() => this.loading.parar());
  }

  putUpload(url: string, formData: FormData) {
    this.cabecalhoAlterado = true;

    this.headers = new Headers();
    this.headers.append('Accept', 'application/json');
    this.options = new RequestOptions({headers: this.headers});

    this.loading.exibir();
    return this.http
      .put(AppSettings.API_ENDPOINT + url, formData, this.options)
      .map(this.extractData)
      .catch(this.handleError)
      .finally(() => this.loading.parar());
  }

  private gerarCabecalho() {
    if (this.cabecalhoAlterado) {
      this.headers = new Headers({
        'Content-Type': 'application/json',
      });
      this.options = new RequestOptions({headers: this.headers});
    }
  }

  private extractData(res: Response) {
    console.log('res', res);
    let body = {};
    if (res.text()) {
      body = res.json();
    }
    return body;
  }

  private handleError(error: any) {
    console.error('error', error);

    if (error == null) {
      console.error('Conexão com servidor foi perdida');
    } else if (error.status == 401) {
      console.error('Falha de autenticação: ');
    } else if (error.status == 403) {
      console.error('Acesso negado ao recurso solicitado: ');
    } else if (error.status == 404) {
      console.error('Página solicitada não encontrada: ' + error.statusText);
    } else if (error.status == 500) {
        console.error('Erro interno no servidor', + error);
    } else if (error.status == 0) {
      console.error('Sem comunicação com servidor');
    } else {
      console.error("Erro desconhecido aconteceu: " + error);
    }

    return Observable.throw(error);
  }
}
