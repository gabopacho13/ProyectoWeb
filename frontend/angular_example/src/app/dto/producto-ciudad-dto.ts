export class ProductoCiudadDto {
    constructor(
        public id: number,
        public ciudadId: number,
        public productoId: number,
        public factorDemanda: number,
        public factorOferta: number,
        public stock: number
    ) {}
}