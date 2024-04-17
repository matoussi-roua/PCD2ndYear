import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ValidatorFn, ValidationErrors } from '@angular/forms';
import { AbstractControl} from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';





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
  templateUrl: './update.component.html',
  styleUrl: './update.component.css'
})

export class UpdateComponent implements  OnInit {
  profileForm!: FormGroup;

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit() {
    // Initialize the form with validators
    this.profileForm = this.formBuilder.group({
    firstName: ['eya',Validators.required ],
      lastName: ['slimen', Validators.required],
      email: ['exemlpe@gmail.com', [Validators.required, Validators.email]],
     phoneNumber: ['21345678', [Validators.required, Validators.pattern(/^\d{8}$/)]],
      address: ['Monastir'],
      oldPassword: ['', Validators.required],
       newPassword: ['',  Validators.minLength(6)],
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

  // Function to handle password change
  changePassword() {
    if (this.profileForm.controls['oldPassword'].valid &&
        this.profileForm.controls['newPassword'].valid &&
        this.profileForm.controls['confirmPassword'].valid) {
      // Perform password change logic
      console.log('Password changed successfully!');
    } else {
      // Password form fields are invalid, handle accordingly
      console.log('Password form fields are invalid.');
    }
  }


  deleteProfile() {

    console.log('Profile deleted successfully!');
  }
}
