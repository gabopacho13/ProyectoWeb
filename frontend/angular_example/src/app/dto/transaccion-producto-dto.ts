export class TransaccionProductoDto {
    constructor(
        public id: number,
        public idCaravana: number,
        public idProducto: number,
        public idCiudad: number,
        public tipo: string,
        public cantidad: number,
        public precioUnitario: number,
        public fecha: Date
    ) {}
}

