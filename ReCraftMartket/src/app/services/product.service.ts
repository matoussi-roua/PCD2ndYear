import { environment } from './../../environments/environment.development';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../models/product';


@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>("https://fakestoreapi.com/products");
  }
  getproductbyid(id: number): Observable<Product> {
    return this.http.get<Product>(environment.hostbackend + "/getproductbyid/" + id);
  }
  //updatejuste description?
    updateproduct(produit: Product): Observable<Product> {
    return this.http.put<Product>(environment.hostbackend + "/updateproduct/" + produit.postId, produit)
  }
    addproduct(produit: Product): Observable<Product> {
    return this.http.post<Product>(environment.hostbackend + "/addproduct", produit);
  }
  deleteproduct(id: number): Observable<Product> {
    return this.http.delete<Product>(environment.hostbackend + "/deleteproduct/" + id)
  }
}




  // Fake database class to simulate product data
