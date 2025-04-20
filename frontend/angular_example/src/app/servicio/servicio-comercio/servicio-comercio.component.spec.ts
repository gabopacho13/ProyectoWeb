import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServicioComercioComponent } from './servicio-comercio.component';

describe('ServicioComercioComponent', () => {
  let component: ServicioComercioComponent;
  let fixture: ComponentFixture<ServicioComercioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ServicioComercioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServicioComercioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
