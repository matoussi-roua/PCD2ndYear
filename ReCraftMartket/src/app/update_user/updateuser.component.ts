import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ValidatorFn, ValidationErrors } from '@angular/forms';
import { AbstractControl } from '@angular/forms';

import { UserEntity } from '../models/user_entity';
import { UsersService } from '../services/users.service';
import { AuthService } from '../services/auth-service.service';
// Custom validator to check if new password and confirm password match
export const passwordMatchValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  const newPassword = control.get('newPassword');
  const confirmPassword = control.get('confirmPassword');

  if (newPassword && confirmPassword && newPassword.value !== confirmPassword.value) {
    confirmPassword.setErrors({ notSame: true });
    return { notSame: true };
  } else {
    confirmPassword?.setErrors(null);
    return null;
  }
};

@Component({
  selector: 'app-update',
  templateUrl: './updateuser.component.html',
  styleUrls: ['./updateuser.component.css']
})
export class UpdateComponent implements OnInit {
  profileForm!: FormGroup;
  user: UserEntity = new UserEntity();

  constructor(private formBuilder: FormBuilder, private userService: UsersService, private authService: AuthService ) {}
ngOnInit() {
    // Fetch user data from backend by ID
   // const userId = this.authService.getCurrentUser().id;/*!!!!!!!!! */
   const userId ='1';
   this.userService.getUserById(userId).subscribe((userData: UserEntity) => {
      this.user = userData;
      this.initializeForm();
    });
  }
  initializeForm() {
    // Initialize the form with validators
    this.profileForm = this.formBuilder.group({
      firstName: [this.user.firstname, Validators.required],
      lastName: [this.user.lastname, Validators.required],
      email: [this.user.username, [Validators.required, Validators.email]],
      phoneNumber: [this.user.phonenumber, [Validators.required, Validators.pattern(/^\d{8}$/)]],
      address: [this.user.address],
      oldPassword: ['', Validators.required],
      newPassword: ['', Validators.minLength(6)],
      confirmPassword: ['']
    }, { validators: passwordMatchValidator });
  }

  updateProfile() {
    if (this.profileForm.valid) {
      // Perform update logic
      console.log('Profile updated successfully!');
      console.warn(this.profileForm.value);
    } else {
      console.log('Form is invalid. Please check your input.');
    }
  }

  promptForCurrentPassword() {
    const confirmed = confirm('Please enter your current password before updating. Are you sure you want to proceed?');
    if (confirmed) {
      this.updateProfile();
    } else {
      console.log('Update cancelled.');
    }
  }

  changePassword() {
    if (this.profileForm.controls['oldPassword'].valid &&
        this.profileForm.controls['newPassword'].valid &&
        this.profileForm.controls['confirmPassword'].valid) {
      // Perform password change logic
      console.log('Password changed successfully!');
    } else {
      console.log('Password form fields are invalid.');
    }
  }

  deleteProfile() {
    console.log('Profile deleted successfully!');
  }
}
