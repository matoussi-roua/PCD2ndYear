import { Injectable } from '@angular/core';
import { Product } from '../models/product/product';

@Injectable({
  providedIn: 'root'
})
export class ShoppinglistService {

  constructor() { }
   shoppingList: Product[] = [];
    addToShoppingList(product: Product) {
    this.shoppingList.push(product);
  }

  removeFromShoppingList(product: Product) {
    const index = this.shoppingList.findIndex(item => item.postId === product.postId);
    if (index !== -1) {
      this.shoppingList.splice(index, 1);
    }
  }
}
