import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../models/product';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FavouriteService {
  constructor(private httpclient :HttpClient) { }
  getFavouriteList(iduser:number):Observable<Product[]>{
    return this.httpclient.get<Product[]>(environment.hostbackend+"/getfavouritelist/"+iduser)
  }
  addProductToFavouriteList(iduser:number,idproduct:number):Observable<any>{
    return this.httpclient.put<any>(environment.hostbackend+"/addtofavourite/"+iduser+"/"+idproduct,{})
  }
  removeProductFromFavouriteList(iduser:number,idproduct:number):Observable<any>{
    return this.httpclient.delete<any>(environment.hostbackend+"/removefromfavourite/"+iduser+"/"+idproduct)
}
}
