import { TestBed } from '@angular/core/testing';

import { CiudadCaravanaService } from './ciudad-caravana.service';

describe('CiudadCaravanaService', () => {
  let service: CiudadCaravanaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CiudadCaravanaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
