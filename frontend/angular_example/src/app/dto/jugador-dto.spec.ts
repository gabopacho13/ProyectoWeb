import { JugadorDto } from './jugador-dto';

describe('JugadorDto', () => {
  it('should create an instance', () => {
    expect(new JugadorDto(0, "name", "rol")).toBeTruthy();
  });
});
