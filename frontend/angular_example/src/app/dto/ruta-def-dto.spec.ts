import { RutaDefDto } from './ruta-def-dto';

describe('RutaDefDto', () => {
  it('should create an instance', () => {
    expect(new RutaDefDto(0, 0, true, 0, 0, 0)).toBeTruthy();
  });
});
