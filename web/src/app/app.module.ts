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
import {NotifierService} from "./services/notificacao/notifier.service";
import {LoginComponent} from "./components/login/login.component";
import {GuardService} from "./services/guard/guard.service";
import {PageComponent} from "./modules/page/page.component";
import {LoggedUserService} from "./services/logged-user/logged-user.service";
import {LoginService} from "./services/login/login.service";
import {FormsModule} from "@angular/forms";
import {AppSettings} from "./app.settings";
import {LoadingComponent} from "./components/loading/loading.component";

@NgModule({
  declarations: [
    AppComponent,
    MenuLeftComponent,
    MenuTopComponent,
    LoadingComponent,
    LoginComponent,
    PageComponent,
  ],
  imports: [
    BrowserModule,
    HttpModule,
    routing,
    RouterModule,
    FormsModule
  ],
  providers: [HttpService, EntityService, HttpErrorHandler, LoadingService, NotifierService, LoggedUserService, GuardService,
    LoggedUserService, LoginService, AppSettings],
  bootstrap: [AppComponent]
})
export class AppModule { }
