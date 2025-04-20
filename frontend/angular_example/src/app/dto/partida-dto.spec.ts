import { PartidaDto } from './partida-dto';

describe('PartidaDto', () => {
  it('should create an instance', () => {
    expect(new PartidaDto(0, 0, 0, "time", 0)).toBeTruthy();
  });
});
