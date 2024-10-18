import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) { }

  login(): void {
    this.authService.login(this.username, this.password).subscribe(
        response => {
          console.log(response.message, response.username, response.userId);
            sessionStorage.setItem('userId', response.userId);
            this.router.navigate(['/urlcollection']);
        },
        error => {
          this.errorMessage = error.error.message;
          console.error('There was an error!', error);
        }
    );
  }
}
