import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PartidaInicioComponent } from './partida-inicio.component';

describe('PartidaInicioComponent', () => {
  let component: PartidaInicioComponent;
  let fixture: ComponentFixture<PartidaInicioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PartidaInicioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PartidaInicioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
