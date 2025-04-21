import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViajarComponent } from './viajar.component';

describe('ViajarComponent', () => {
  let component: ViajarComponent;
  let fixture: ComponentFixture<ViajarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViajarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViajarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
