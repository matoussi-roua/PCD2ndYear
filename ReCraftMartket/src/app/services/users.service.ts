import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserEntity } from '../models/user_entity';
import { Observable } from 'rxjs';
/*
@Injectable({
  providedIn: 'root'
})
export class UsersService {

    constructor(private httpclient: HttpClient) { }


createUser(user: UserEntity): Observable<UserEntity> {
  return this.httpclient.post<UserEntity>(`${environment.apiUrl}/users`, user);
}

getUsers(): Observable<UserEntity[]> {
  return this.httpclient.get<UserEntity[]>(`${environment.apiUrl}/users`);
}

getUserById(id: number): Observable<UserEntity> {
  return this.httpclient.get<UserEntity>(`${environment.apiUrl}/users/${id}`);
}
/*a verifier*
updateUserProfile(user: UserEntity): Observable<UserEntity> {
  return this.httpclient.put<UserEntity>(`${environment.apiUrl}/users/${user.id}`, user);
}

}*/
/*
@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private apiUrl = 'http://example.com/api/users';

  constructor(private http: HttpClient) { }

  getUsers(): Observable<UserEntity[]> {
    return this.http.get<UserEntity[]>(this.apiUrl);
  }

  addUser(user: UserEntity): Observable<UserEntity> {
    return this.http.post<UserEntity>(this.apiUrl, user);
  }

  updateUserProfile(user: UserEntity): Observable<any> {
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




  getUsers(): Observable<UserEntity[]> {
    return this.httpclient.get<UserEntity[]>("https://jsonplaceholder.typicode.com/users");
  }

getUserById(id: number): Observable<UserEntity> {
  return this.httpclient.get<UserEntity>(`${this.apiUrl}/users/${id}`);
}

  addUser(user: UserEntity): Observable<UserEntity> {
    return this.httpclient.post<UserEntity>(this.apiUrl, user);
  }

    updateUser(user: UserEntity): Observable<any> {
    return this.httpclient.put(`${this.apiUrl}/${user.id}`, user);
  }}


