/*import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';



import { Users } from '../models/users';
declare var $: any;

@Component({
  selector: 'app-log',
  templateUrl: './log.component.html',
  styleUrl: './log.component.scss'

})

export class LogComponent implements OnInit {
  signupUsers:any[]=[];
  signupObj:any={
    username:'',
    adress:'',
    phonenumber:'',
    password:'',
    confirmpassword:''


  };
   LoginObj:any={
    username:'',
    password:''


  };
  constructor() { }

  ngOnInit(): void {

    const localData=localStorage.getItem('signUpUsers');
    if (localData!=null){this.signupUsers=JSON.parse(localData);

    }
    // JavaScript logic here
    $('#signup').click(function() {
      $('.pinkbox').css('transform', 'translateX(80%)');
      $('.signin').addClass('nodisplay');
      $('.signup').removeClass('nodisplay');
    });

    $('#signin').click(function() {
      $('.pinkbox').css('transform', 'translateX(0%)');
      $('.signup').addClass('nodisplay');
      $('.signin').removeClass('nodisplay');
    });

 }
    onSignUp(){
      this.signupUsers.push(this.signupObj);
      localStorage.setItem('signUpUsers',JSON.stringify(this.signupUsers));

        this.signupObj ={
    username:'',
    adress:'',

    phonenumber:'',
    password:'',
    confirmpassword:''

  };
    }
    onLogin(){
      debugger
      const isUserExist=this.signupUsers.find(m => m.username == this.LoginObj.username && m.password == this.LoginObj.password);
if (isUserExist !=undefined){

  alert('User LoginSuccessfully');

}else{alert('Wrong credentials');}
    }

}
*/
/*2eme version
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

declare var $: any;

@Component({
  selector: 'app-log',
  templateUrl: './log.component.html',
  styleUrls: ['./log.component.scss']
})
export class LogComponent implements OnInit {
   signupForm: FormGroup = new FormGroup({});
  signupUsers: any[] = [];//utilisateurs inscrits
  signupObj: any = { //stockage temporaire
    username: '',
    address: '',
    phonenumber: '',
    password: '',
    confirmpassword: ''
  };
  LoginObj: any = {
    username: '',
    password: ''
  };

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    // Initialize the signup form with form controls and validators
    this.signupForm = this.formBuilder.group({
      username: ['', Validators.required],
      address: ['', Validators.required],
      phonenumber: ['', [Validators.required, Validators.pattern(/^\d{8}$/)]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmpassword: ['', Validators.required]
    });

    const localData = localStorage.getItem('signUpUsers');
    if (localData != null) {
      this.signupUsers = JSON.parse(localData);
    }


    $('#signup').click(function () {
      $('.pinkbox').css('transform', 'translateX(80%)');
      $('.signin').addClass('nodisplay');
      $('.signup').removeClass('nodisplay');
    });

    $('#signin').click(function () {
      $('.pinkbox').css('transform', 'translateX(0%)');
      $('.signup').addClass('nodisplay');
      $('.signin').removeClass('nodisplay');
    });
  }

  onSignUp() {

    if (this.signupForm.invalid) {

      this.signupForm.markAllAsTouched();
      return;
    }


    this.signupUsers.push(this.signupObj);
    localStorage.setItem('signUpUsers', JSON.stringify(this.signupUsers));

    this.signupObj = {
      username: '',
      address: '',
      phonenumber: '',
      password: '',
      confirmpassword: ''
    };
  }

  onLogin() {
    debugger;
    const isUserExist = this.signupUsers.find(m => m.username == this.LoginObj.username && m.password == this.LoginObj.password);
    if (isUserExist != undefined) {
      alert('User Login Successfully');
    } else {
      alert('Wrong credentials');
    }
  }
}*/
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';

declare var $: any;

@Component({
  selector: 'app-log',
  templateUrl: './log.component.html',
  styleUrl: './log.component.scss'

})

export class LogComponent  {
  ngOnInit(): void {


    // JavaScript
    $('#signup').click(function() {
      $('.pinkbox').css('transform', 'translateX(80%)');
      $('.signin').addClass('nodisplay');
      $('.signup').removeClass('nodisplay');
    });

    $('#signin').click(function() {
      $('.pinkbox').css('transform', 'translateX(0%)');
      $('.signup').addClass('nodisplay');
      $('.signin').removeClass('nodisplay');
    });

 }
  loginForm= new FormGroup ({


    //user: new FormControl('', [Validators.required,Validators.email]),
   // loginusername: new FormControl('', [Validators.required,Validators.pattern('+@gmail.com')]),
   loginusername: new FormControl('', [Validators.required]),

    loginpassword: new FormControl ('',[Validators.required,Validators.minLength(6)]),
  })

SignupForm= new FormGroup ({



    //signupusername: new FormControl('', [Validators.required,Validators.pattern('+@gmail.com')]),
signupusername: new FormControl('', [Validators.required]),
    adress: new FormControl ('',[Validators.required]),
    phonenumber: new FormControl ('',[Validators.required,Validators.minLength(8)]),
    signuppassword: new FormControl ('',[Validators.required,Validators.minLength(6)]),
    confirmpassword: new FormControl ('',[Validators.required,Validators.minLength(6)]),

  })
  loginUser() {

    console.warn(this.loginForm.value);
  }
  get loginusername ()
  {
    return this.loginForm.get('loginusername');
  }
   get loginpassword ()
   {
    return this.loginForm.get('loginpassword');
  }

 SignupUser() {

    console.warn(this.SignupForm.value);
  }
  get signupusername ()
  {
    return this.SignupForm.get('signupusername');
  }
   get signupphonenumber ()
   {
    return this.SignupForm.get('phonenumber');
  }
    get signupadress ()
   {
    return this.SignupForm.get('adress');
  }
   get signuppassword ()
   {
    return this.SignupForm.get('signuppassword');
  }
   get signupconf_password ()
   {
    return this.SignupForm.get('confirmpassword');
  }




}
