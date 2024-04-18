import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserEntity } from '../models/user_entity';
import { Observable } from 'rxjs';


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


getUserById(id: string): Observable<UserEntity> {
  return this.httpclient.get<UserEntity>(`${this.apiUrl}/users/${id}`);
}

  addUser(user: UserEntity): Observable<UserEntity> {
    return this.httpclient.post<UserEntity>(this.apiUrl, user);
  }

    updateUser(user: UserEntity): Observable<any> {
    return this.httpclient.put(`${this.apiUrl}/${user.id}`, user);
  }}


