import { TestBed } from '@angular/core/testing';

import { PartidaCaravanaService } from './caravana.service';

describe('CaravanaService', () => {
  let service: PartidaCaravanaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PartidaCaravanaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
