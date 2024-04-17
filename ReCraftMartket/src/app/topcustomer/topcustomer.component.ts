import { UsersService } from './../services/users.service';
import { Component, OnInit } from '@angular/core';

import { Users } from '../models/users';


@Component({
  selector: 'app-topcustomer',
  templateUrl: './topcustomer.component.html',
  styleUrl: './topcustomer.component.css'
})/*
export class TopcustomerComponent implements OnInit {

 // UsersList: any[] = [];
  UsersList: Users[] = [];
/**injection de service cad appel de ce service pour utiliser data  "private nomvariable: nameService
  constructor(private UsersService: UsersService) {}

  ngOnInit() {
    this.fetchProducts();
  }

  fetchProducts() {
    this.UsersService.getUsers().subscribe(
      (data: Users[]) => {
        console.log(data);
        this.UsersList = data;
      },
      (error) => {
        console.error("Error fetching products:", error);
      }
    );
  }
   showAllUsers: boolean = false;
}

*/


export class TopcustomerComponent
{}
