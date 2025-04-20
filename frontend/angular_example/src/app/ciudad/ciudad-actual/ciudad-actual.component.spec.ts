import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CiudadActualComponent } from './ciudad-actual.component';

describe('CiudadActualComponent', () => {
  let component: CiudadActualComponent;
  let fixture: ComponentFixture<CiudadActualComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CiudadActualComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CiudadActualComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
