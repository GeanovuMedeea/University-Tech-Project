import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) { }

  register(): void {
    this.authService.register(this.username, this.password).subscribe(
        response => {
          console.log(response.message);
          this.router.navigate(['/login']); // Navigate to login
        },
        error => {
          this.errorMessage = error.error.message;
          console.error('There was an error!', error);
        }
    );
  }
}
