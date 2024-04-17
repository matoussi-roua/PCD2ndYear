import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Users } from '../models/users';
import { Observable } from 'rxjs';
/*
@Injectable({
  providedIn: 'root'
})
export class UsersService {

    constructor(private httpclient: HttpClient) { }


createUser(user: Users): Observable<Users> {
  return this.httpclient.post<Users>(`${environment.apiUrl}/users`, user);
}

getUsers(): Observable<Users[]> {
  return this.httpclient.get<Users[]>(`${environment.apiUrl}/users`);
}

getUserById(id: number): Observable<Users> {
  return this.httpclient.get<Users>(`${environment.apiUrl}/users/${id}`);
}
/*a verifier*
updateUserProfile(user: Users): Observable<Users> {
  return this.httpclient.put<Users>(`${environment.apiUrl}/users/${user.id}`, user);
}

}*/
/*
@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private apiUrl = 'http://example.com/api/users';

  constructor(private http: HttpClient) { }

  getUsers(): Observable<Users[]> {
    return this.http.get<Users[]>(this.apiUrl);
  }

  addUser(user: Users): Observable<Users> {
    return this.http.post<Users>(this.apiUrl, user);
  }

  updateUserProfile(user: Users): Observable<any> {
    const url = `${this.apiUrl}/${user.id}`;
    return this.http.put(url, user);
  }

  deleteUser(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }
}
*/




@Injectable({
  providedIn: 'root'
})

export class UsersService {


  private hostbackend = "http://localhost:8080";
  constructor(private httpclient: HttpClient) { }
  private apiUrl = 'http://example.com/api/users';




  getUsers(): Observable<Users[]> {
    return this.httpclient.get<Users[]>("https://jsonplaceholder.typicode.com/users");
  }

getUserById(id: number): Observable<Users> {
  return this.httpclient.get<Users>(`${this.apiUrl}/users/${id}`);
}

  addUser(user: Users): Observable<Users> {
    return this.httpclient.post<Users>(this.apiUrl, user);
  }

    updateUser(user: Users): Observable<any> {
    return this.httpclient.put(`${this.apiUrl}/${user.id}`, user);
  }}


