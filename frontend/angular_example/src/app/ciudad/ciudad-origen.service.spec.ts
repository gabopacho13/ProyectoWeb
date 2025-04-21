import { TestBed } from '@angular/core/testing';

import { CiudadOrigenService } from './ciudad-origen.service';

describe('CiudadRutaService', () => {
  let service: CiudadOrigenService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CiudadOrigenService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
