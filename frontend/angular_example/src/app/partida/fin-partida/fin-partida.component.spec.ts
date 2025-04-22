import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FinPartidaComponent } from './fin-partida.component';

describe('FinPartidaComponent', () => {
  let component: FinPartidaComponent;
  let fixture: ComponentFixture<FinPartidaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FinPartidaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FinPartidaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
