import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContadorPrincipalComponent } from './contador-principal.component';

describe('ContadorPrincipalComponent', () => {
  let component: ContadorPrincipalComponent;
  let fixture: ComponentFixture<ContadorPrincipalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContadorPrincipalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContadorPrincipalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
