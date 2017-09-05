import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PageComponent } from './page.component';
import {RouterModule} from "@angular/router";
import {GuardService} from "../../services/guard/guard.service";

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot([
      {path: '', component: PageComponent, canActivateChild: [GuardService], canActivate: [GuardService]}
    ])
  ],
  providers: [
    GuardService
  ],
  declarations: []
})
export class PageModule { }
