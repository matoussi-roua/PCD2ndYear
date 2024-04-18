
import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Product } from '../models/product/product';
import { ProductService } from '../services/product.service';
@Component({
  selector: 'app-cardproduct',
  templateUrl: './cardproduct.component.html',
  styleUrls: ['./cardproduct.component.css']
})


export class CardproductComponent implements OnInit {

  productsList: Product[] = [];

  constructor(private httpclient: HttpClient) {}

  ngOnInit() {
    this.fetchProducts();
  }

  fetchProducts() {
    this.httpclient.get<Product[]>("https://fakestoreapi.com/products").subscribe(
      (data: Product[]) => {
        console.log(data);
        this.productsList = data;
      },
      (error) => {
        console.error("Error fetching products:", error);
      }
    );
  }
}



/*
export class CardproductComponent implements OnInit {

  productsList: Product[] = [];
/**injection de service cad appel de ce service pour utiliser data  "private nomvariable: nameService*
  constructor(private productService: ProductService) {}
/*yelzem t3ayet l fetchproductfy ngOnInit*
  ngOnInit() {
    this.fetchProducts();
  }

  fetchProducts() {
    this.productService.getProducts().subscribe(
      (data: Product[]) => {
        console.log(data);
        this.productsList = data;
      },
      (error) => {
        console.error("Error fetching products:", error);
      }
    );
  }
}
*/
