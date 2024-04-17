import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private tokenKey = 'auth_token'; // Key for storing token in local storage

  constructor() { }

  // Save token to local storage
  setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  // Retrieve token from local storage
  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  // Remove token from local storage
  removeToken(): void {
    localStorage.removeItem(this.tokenKey);
  }

  // Check if user is logged in (token exists)
  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}