import { TransaccionProductoDto } from './transaccion-producto-dto';

describe('TransaccionProductoDto', () => {
  it('should create an instance', () => {
    expect(new TransaccionProductoDto(0, 0, 0, 0, "type", 0, 0, "date")).toBeTruthy();
  });
});
