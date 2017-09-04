import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";

import {AppComponent} from "./app.component";
import {MenuLeftComponent} from "./components/layout/menu-left/menu-left.component";
import {MenuTopComponent} from "./components/layout/menu-top/menu-top.component";
import {RouterModule} from "@angular/router";
import {routing} from "./app.routing";
import {HttpService} from "./services/http/http.service";
import {EntityService} from "./services/model/entity.service";
import {HttpErrorHandler} from "./services/http/httpErrorHandler.service";
import {HttpModule} from "@angular/http";
import {LoadingService} from "./services/http/loading.service";
import { BackButtonComponent } from './components/back-button/back-button.component';

@NgModule({
  declarations: [
    AppComponent,
    MenuLeftComponent,
    MenuTopComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    routing,
    RouterModule
  ],
  providers: [HttpService, EntityService, HttpErrorHandler, LoadingService],
  bootstrap: [AppComponent]
})
export class AppModule { }
