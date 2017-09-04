import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ListComponent} from "./list/list.component";
import {routing} from "./entity-list-routing.module";
import {HistoryListComponent} from "./history-list/history-list.component";
import {HistoryDetailComponent} from "./history-detail/history-detail.component";
import {RegisterDetailComponent} from "./register-detail/register-detail.component";
import {KeysPipe} from "../../pipes/keys-pipe";
import {BackButtonComponent} from "../../components/back-button/back-button.component";
import {RevisionTypePipe} from "../../pipes/revision-type";

@NgModule({
  imports: [
    CommonModule,
    routing
  ],
  declarations: [ListComponent, BackButtonComponent, HistoryListComponent, HistoryDetailComponent,
    KeysPipe, RevisionTypePipe, RegisterDetailComponent]
})
export class EntityListModule { }
