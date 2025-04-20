import { InventarioCaravanaDto } from './inventario-caravana-dto';

describe('InventarioCaravanaDto', () => {
  it('should create an instance', () => {
    expect(new InventarioCaravanaDto(0, 0, 0, 0)).toBeTruthy();
  });
});
