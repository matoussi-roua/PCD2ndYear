import { Component } from '@angular/core';
import { Product } from '../models/product';
import { ShoppinglistService } from '../services/shoppinglist.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  constructor(private shoppingService: ShoppinglistService) {}

  product: Product | undefined;

  icons = [
    { id: 'heartIcon1' },
    { id: 'heartIcon2' },
 { id: 'heartIcon3' },
  { id: 'heartIcon4' },
   { id: 'heartIcon5' },
   { id: 'heartIcon6' },
   { id: 'heartIcon7' },
  ];

  toggleHeartColor(iconId: string) {
    const heartIcon = document.getElementById(iconId);
    if (heartIcon) {
      heartIcon.classList.toggle('red-heart');
    }
  }

toggleStarColor(iconId: string) {
  const starIcon = document.getElementById(iconId);
  if (starIcon) {
    starIcon.classList.toggle('star-clicked');
  }
}


  onAddToShoppingList(product: Product) {
    this.shoppingService.addToShoppingList(product);
  }
}
