import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { UrlCollectionComponent } from './urlCollection/urlCollection.component';
import {EditUrlComponent} from "./editUrl/editUrl.component";
import {AddUrlComponent} from "./addUrl/addUrl.component";

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'urlcollection', component: UrlCollectionComponent },
    { path: 'edit', component: EditUrlComponent },
    { path: 'add', component: AddUrlComponent },
    { path: '', redirectTo: '/login', pathMatch: 'full' },
];
@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})

export class AppRoutingModule { }
