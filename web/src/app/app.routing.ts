import {RouterModule, Routes} from "@angular/router";
import {PageComponent} from "./modules/page/page.component";
import {GuardService} from "./services/guard/guard.service";
import {LoginComponent} from "./components/login/login.component";

const appRoutes: Routes = [
    { path: 'login', component: LoginComponent },
    {
      path: '',
      component: PageComponent,
      canActivate: [GuardService],
      canActivateChild: [GuardService],
      children: [
        { path: ':name', loadChildren: './modules/entity-list/entity-list.module#EntityListModule' },
      ]
    },
    { path: '**', redirectTo: 'login' },
];

export const routing = RouterModule.forRoot(appRoutes);


