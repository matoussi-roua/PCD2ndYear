import { Component, OnInit } from '@angular/core';
import { Users } from '../models/users';
import { UsersService } from '../services/users.service';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrl: './update-user.component.css'
})
export class UpdateUserComponent implements OnInit {
  selectedUser: Users = new Users;
  updateUserForm: FormGroup = this.formBuilder.group({});

  //formReady:boolean = false;
 // component = this;
  formErrors: { [key: string]: any }  = {
    'firstname': '',
    'email': '',
    'lastname': '',
    'address': ''
  };
  validationMessages: { [key: string]: any }  = {
    'firstname': {
      'required':      'Username is required.',

    },
    'email': {
      'required': 'Email is required!',
      'email': 'Email is not valid!',
    },
    'lastName': {
      'required': 'fullName is required!'
    },
    'address': {
      'required': 'address is required!'
    }
  };

  constructor(
    private userService: UsersService,
    private formBuilder: FormBuilder
    ) {
    }

     setupForm(){
    this.updateUserForm = this.formBuilder.group({
      id: new FormControl(this.selectedUser.id),
      firstname: new FormControl(this.selectedUser.firstname, [
        Validators.required
      ]),
      email: new FormControl(this.selectedUser.email, [
        Validators.required,
        Validators.email
      ]),
      lastname: new FormControl(this.selectedUser.lastname, [
        Validators.required
      ]),
      address: new FormControl(this.selectedUser.address, [
        Validators.required
      ])
    });
    this.updateUserForm.valueChanges
      .subscribe(data => this.onValueChanged(data));

  }

  loadUserData(){
    this.selectedUser = new Users()
    this.selectedUser.id = 1;
    this.selectedUser.firstname = 'test1';
    this.selectedUser.email = 'test@gmail.com';
    this.selectedUser.lastname = 'Test';
    this.selectedUser.address = 'mestir';
  }

  ngOnInit(){
      this.updateUserForm.valueChanges.subscribe(value => {
    console.log('Form value:', value);
    console.log('Address control state:', this.updateUserForm.get('address')?.status);
  });
   this.updateUserForm.valueChanges.subscribe(value => {
    console.log('Form value:', value);
    console.log('email control state:', this.updateUserForm.get('email')?.status);
  });
    this.loadUserData();
    this.setupForm()
    //this.formReady = true;

  }

  onSubmit(){
    this.userService.updateUser(this.updateUserForm.value).subscribe(x => {
      window.location.reload();
    },
      error => {
        console.log('Error encountered during form submission');
      });
  }

  onValueChanged(data?: any) {
    if (!this.updateUserForm) { return; }
    const form = this.updateUserForm;

    for (const field in this.formErrors) {
      // clear previous error message (if any)
      this.formErrors[field] = '';
      const control = form.get(field);

      if (control && control.dirty && !control.valid) {
        const messages = this.validationMessages[field];
        for (const key in control.errors) {
          this.formErrors[field] += messages[key] + ' ';
        }
      }
    }
  }

}
