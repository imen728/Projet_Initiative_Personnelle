import { Routes } from '@angular/router';

import { HomeComponent } from '../../home/home.component';
import { UserComponent } from '../../user/user.component';
import { TablesComponent } from '../../tables/tables.component';


import { NotificationsComponent } from '../../notifications/notifications.component';


export const AdminLayoutRoutes: Routes = [
    { path: 'dashboard', component: HomeComponent },
    { path: 'user', component: UserComponent },
    { path: 'table', component: TablesComponent },

    { path: 'notifications', component: NotificationsComponent },

];
