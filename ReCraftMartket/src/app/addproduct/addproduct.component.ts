import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
@Component({
  selector: 'app-addproduct',
  templateUrl: './addproduct.component.html',
  styleUrl: './addproduct.component.css'
})
export class AddproductComponent {

   productName!: string;
  productPrice!: number;
  productDescription!: string;
  productImage!: string;
  productForm: FormGroup;
  url: any;
  showSuccessAlert: boolean = false;


  constructor(private formBuilder: FormBuilder) {
    this.productForm = this.formBuilder.group({
      name:new FormControl ('', [Validators.required,Validators.minLength(6)]),
      price:new FormControl ('', Validators.min(0)),
      description: new FormControl ('', [ Validators.required]),
      image: new FormControl ('',[ Validators.required])
    });
  }

  get f() {
    return this.productForm.controls;
  }

  onSubmit() {
    if (this.productForm.invalid) {
      return;
    }


    //msg
    if (this.productForm.valid) {

      this.showSuccessAlert = true;


      setTimeout(() => {
        this.showSuccessAlert = false;
      }, 5000);
    }
    console.log(this.productForm.value);
  }
}
