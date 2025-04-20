export class InventarioCaravanaDto {
    constructor(
        public id: number,
        public caravanaId: number,
        public productoId: number,
        public cantidad: number
    ) {}
}
