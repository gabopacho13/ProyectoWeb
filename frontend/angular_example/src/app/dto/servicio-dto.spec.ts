import { ServicioDto } from './servicio-dto';

describe('ServicioDto', () => {
  it('should create an instance', () => {
    expect(new ServicioDto(0, "type")).toBeTruthy();
  });
});
