import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServicioViewComponent } from './servicio-view.component';

describe('ServicioViewComponent', () => {
  let component: ServicioViewComponent;
  let fixture: ComponentFixture<ServicioViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ServicioViewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServicioViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
