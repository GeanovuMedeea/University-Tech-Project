import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { UrlCollectionComponent } from './urlCollection/urlCollection.component';
import { AppRoutingModule } from './app.routes';
import {EditUrlComponent} from "./editUrl/editUrl.component";
import {AddUrlComponent} from "./addUrl/addUrl.component";

@NgModule({
    declarations: [
        RegisterComponent,
        LoginComponent,
        UrlCollectionComponent,
        EditUrlComponent,
        AddUrlComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        AppRoutingModule
    ],
    providers: [],
    bootstrap: []
})
export class AppModule { }
