import { RutaDto } from './ruta-dto';

describe('RutaDto', () => {
  it('should create an instance', () => {
    expect(new RutaDto(0, 0, true, 0)).toBeTruthy();
  });
});
