import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';
import { importProvidersFrom } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AppModule } from './app/app.module';

bootstrapApplication(AppComponent, {
    providers: [
        provideRouter(routes),
        importProvidersFrom(HttpClientModule, FormsModule)
    ]
}).catch(err => console.error(err));
