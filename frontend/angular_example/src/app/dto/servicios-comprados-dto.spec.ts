import { ServiciosCompradosDto } from './servicios-comprados-dto';

describe('ServiciosCompradosDto', () => {
  it('should create an instance', () => {
    expect(new ServiciosCompradosDto(0, 0, 0, "date")).toBeTruthy();
  });
});
