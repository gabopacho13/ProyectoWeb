import { TestBed } from '@angular/core/testing';

import { CaravanaService } from './caravana.service';

describe('CaravanaService', () => {
  let service: CaravanaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CaravanaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
