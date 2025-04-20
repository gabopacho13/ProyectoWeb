import { ServicioCiudadDto } from './servicio-ciudad-dto';

describe('ServicioCiudadDto', () => {
  it('should create an instance', () => {
    expect(new ServicioCiudadDto(0, 0, 0, 0)).toBeTruthy();
  });
});
