import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CiudadViewComponent } from './ciudad-view.component';

describe('CiudadViewComponent', () => {
  let component: CiudadViewComponent;
  let fixture: ComponentFixture<CiudadViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CiudadViewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CiudadViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
