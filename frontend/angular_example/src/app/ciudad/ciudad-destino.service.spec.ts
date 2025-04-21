import { TestBed } from '@angular/core/testing';

import { CiudadDestinoService } from './ciudad-destino.service';

describe('CiudadDestinoService', () => {
  let service: CiudadDestinoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CiudadDestinoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
