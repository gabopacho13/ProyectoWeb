import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductoComercioComponent } from './producto-comercio.component';

describe('ServicioComercioComponent', () => {
  let component: ProductoComercioComponent;
  let fixture: ComponentFixture<ProductoComercioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductoComercioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductoComercioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
