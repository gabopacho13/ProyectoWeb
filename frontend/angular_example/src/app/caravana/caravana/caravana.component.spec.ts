import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CaravanaComponent } from './caravana.component';

describe('CaravanaComponent', () => {
  let component: CaravanaComponent;
  let fixture: ComponentFixture<CaravanaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CaravanaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CaravanaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
