import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CiudadListaComponent } from './ciudad-lista.component';

describe('CiudadListaComponent', () => {
  let component: CiudadListaComponent;
  let fixture: ComponentFixture<CiudadListaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CiudadListaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CiudadListaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
