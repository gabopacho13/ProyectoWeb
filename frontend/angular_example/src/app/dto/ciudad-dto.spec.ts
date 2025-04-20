import { CiudadDto } from './ciudad-dto';

describe('CiudadDto', () => {
  it('should create an instance', () => {
    expect(new CiudadDto(0, "name", 0)).toBeTruthy();
  });
});
