import {RouterModule, Routes} from "@angular/router";
import {AppComponent} from "./app.component";

const appRoutes: Routes = [
    {
      path: '',
      component: AppComponent,
      canActivate: [],
      canActivateChild: [],
      children: [
        { path: ':name', loadChildren: './modules/entity-list/entity-list.module#EntityListModule' },
      ]
    }
];

export const routing = RouterModule.forRoot(appRoutes);


