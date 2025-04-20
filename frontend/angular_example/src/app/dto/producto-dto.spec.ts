import { ProductoDto } from './producto-dto';

describe('ProductoDto', () => {
  it('should create an instance', () => {
    expect(new ProductoDto(0, "name")).toBeTruthy();
  });
});
