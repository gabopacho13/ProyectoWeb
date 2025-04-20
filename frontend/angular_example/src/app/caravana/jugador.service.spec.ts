import { TestBed } from '@angular/core/testing';

import { CaravanaJugadorService } from './jugador.service';

describe('JugadorService', () => {
  let service: CaravanaJugadorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CaravanaJugadorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
