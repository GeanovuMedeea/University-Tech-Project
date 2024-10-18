import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive, RouterOutlet} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  imports: [
    RouterOutlet,
    RouterLink,
    RouterLinkActive
  ],
  standalone: true
})
export class AppComponent {
  title = 'My Angular App';
}
