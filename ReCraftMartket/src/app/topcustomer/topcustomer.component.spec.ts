import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopcustomerComponent } from './topcustomer.component';

describe('TopcustomerComponent', () => {
  let component: TopcustomerComponent;
  let fixture: ComponentFixture<TopcustomerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TopcustomerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TopcustomerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
