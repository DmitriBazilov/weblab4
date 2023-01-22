import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Token } from '../dto/token';
import { LoginInfo } from '../dto/login-info';
import { RegisterInfo } from '../dto/register-info';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loginUrl = 'http://127.0.0.1:8080/backend-1.0-SNAPSHOT/api/auth/login';
  private signupUrl = 'http://127.0.0.1:8080/backend-1.0-SNAPSHOT/api/auth/register';

  attemptAuth(credentials: LoginInfo): Observable<Token> {
    return this.http.post<Token>(this.loginUrl, credentials, httpOptions);
  }

  signUp(info: RegisterInfo): Observable<string> {
    return this.http.post<string>(this.signupUrl, info, httpOptions);
  }

  constructor(private http: HttpClient) { }
}
