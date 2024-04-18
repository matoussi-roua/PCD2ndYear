import { Product } from './product';

describe('Product', () => {
  it(' create Product', () => {

    expect(new Product()).toBeTruthy();
  });
});
