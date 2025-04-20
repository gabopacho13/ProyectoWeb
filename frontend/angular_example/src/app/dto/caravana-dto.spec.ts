import { CaravanaDto } from './caravana-dto';

describe('CaravanaDto', () => {
  it('should create an instance', () => {
    expect(new CaravanaDto(0, "date", "name", 0, 0, 0, 0, 0)).toBeTruthy();
  });
});
