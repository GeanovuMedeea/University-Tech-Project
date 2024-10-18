import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loginUrl = 'http://localhost/url_project/php/login.php';
  private registerUrl = 'http://localhost/url_project/php/register.php';

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(this.loginUrl, { username, password }, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    });
  }

  register(username: string, password: string): Observable<any> {
    return this.http.post<any>(this.registerUrl, { username, password }, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    });
  }
}
