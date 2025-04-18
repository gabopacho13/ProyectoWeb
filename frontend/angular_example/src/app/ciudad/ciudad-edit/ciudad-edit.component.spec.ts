import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CiudadEditComponent } from './ciudad-edit.component';

describe('CiudadEditComponent', () => {
  let component: CiudadEditComponent;
  let fixture: ComponentFixture<CiudadEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CiudadEditComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CiudadEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
