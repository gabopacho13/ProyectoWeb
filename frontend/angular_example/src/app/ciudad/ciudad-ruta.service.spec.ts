import { TestBed } from '@angular/core/testing';

import { CiudadRutaService } from './ciudad-ruta.service';

describe('CiudadRutaService', () => {
  let service: CiudadRutaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CiudadRutaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
