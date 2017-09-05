import {Injectable} from "@angular/core";
import {Headers, Http, RequestOptions, Response} from "@angular/http";

import {Observable} from "rxjs/Observable";

import "rxjs/add/operator/map";
import "rxjs/add/observable/throw";
import "rxjs/add/operator/catch";
import "rxjs/add/operator/finally";
import {LoadingService} from "./loading.service";
import {AppSettings} from "../../app.settings";
import {Message} from "../../model/Message";


@Injectable()
export class HttpService {

  headers: Headers;
  options: RequestOptions;
  headerChanged = false;

  constructor(private http: Http, private loading : LoadingService,
              private settings : AppSettings) {
    this.headers = new Headers({'Content-Type': 'application/json', 'Accept' : "application/json"});
    this.options = new RequestOptions({headers: this.headers, withCredentials: true});
  }

  post(url: string, parametros ?: any) {
    this.generateHeader();
    this.loading.show();
    return this.http
      .post(this.settings.getApiEndPoint() + url, parametros, this.options)
      .map(this.extractData)
      .catch(this.handleError)
      .finally(() => this.loading.stop());
  }

  get(url: string) {
    this.generateHeader();
    this.loading.show();
    return this.http
      .get(this.settings.getApiEndPoint() + url, this.options)
      .map(this.extractData)
      .catch(this.handleError)
      .finally(() => {this.loading.stop();});
  }

  getBackground(url: string) {
    this.generateHeader();
    return this.http
      .get(this.settings.getApiEndPoint() + url, this.options)
      .map(this.extractData)
      .catch(this.handleError);
  }

  put(url: string, parametros ?: any) {
    this.generateHeader();
    this.loading.show();
    return this.http
      .put(this.settings.getApiEndPoint() + url, parametros, this.options)
      .map(this.extractData)
      .catch(this.handleError)
      .finally(() => this.loading.stop());
  }

  delete(url: string) {
    this.generateHeader();
    this.loading.show();
    return this.http
      .delete(this.settings.getApiEndPoint() + url, this.options)
      .map(this.extractData)
      .catch(this.handleError)
      .finally(() => this.loading.stop());
  }

  postUpload(url: string, formData: FormData) {
    this.headerChanged = true;

    this.headers = new Headers();
    this.headers.append('Accept', 'application/json');
    this.options = new RequestOptions({headers: this.headers});

    this.loading.show();
    return this.http
      .post(this.settings.getApiEndPoint() + url, formData, this.options)
      .map(this.extractData)
      .catch(this.handleError)
      .finally(() => this.loading.stop());
  }

  putUpload(url: string, formData: FormData) {
    this.headerChanged = true;

    this.headers = new Headers();
    this.headers.append('Accept', 'application/json');
    this.options = new RequestOptions({headers: this.headers});

    this.loading.show();
    return this.http
      .put(this.settings.getApiEndPoint() + url, formData, this.options)
      .map(this.extractData)
      .catch(this.handleError)
      .finally(() => this.loading.stop());
  }

  private generateHeader() {
    if (this.headerChanged) {
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
    let messageError = new Message();
    if (error == null) {
      console.error('Connection with the server was lost');
    } else {
      if (error.status == 401) {
        console.error('No authentication ', error);
      } else if (error.status == 403) {
        console.error('Acces denied', error);
      } else if (error.status == 404) {
        console.error('Not found: ', error);
      } else if (error.status == 500) {
        console.error('Internal server error', error);
      } else if (error.status == 0) {
        console.error("Connection with the server was lost", error);
      } else {
        console.error("Unknown error: " + error);
      }

      try {
        messageError = error.json();
      } catch (fail) {
        messageError.code = error.status;
      }

    }

    return Observable.throw(messageError);
  }
}
