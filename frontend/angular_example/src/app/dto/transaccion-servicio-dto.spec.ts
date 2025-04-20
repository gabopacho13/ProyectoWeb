import { TransaccionServicioDto } from './transaccion-servicio-dto';

describe('TransaccionServicioDto', () => {
  it('should create an instance', () => {
    expect(new TransaccionServicioDto(0, 0, 0, 0, "type", 0, 0, "date")).toBeTruthy();
  });
});
