import {RouterModule, Routes} from "@angular/router";
import {ListComponent} from "./list/list.component";
import {HistoryListComponent} from "./history-list/history-list.component";
import {HistoryDetailComponent} from "./history-detail/history-detail.component";
import {RegisterDetailComponent} from "./register-detail/register-detail.component";

const routes: Routes = [
  { path : '', component: ListComponent },
  { path : ':id', component: RegisterDetailComponent },
  { path : ':id/history', component: HistoryListComponent },
  { path : ':id/history/:revision', component: HistoryDetailComponent }
];

export const routing = RouterModule.forChild(routes);
