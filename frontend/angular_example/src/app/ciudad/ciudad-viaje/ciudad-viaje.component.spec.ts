import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CiudadViajeComponent } from './ciudad-viaje.component';

describe('CiudadViajeComponent', () => {
  let component: CiudadViajeComponent;
  let fixture: ComponentFixture<CiudadViajeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CiudadViajeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CiudadViajeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
