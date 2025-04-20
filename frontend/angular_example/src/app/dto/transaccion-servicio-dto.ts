export class TransaccionServicioDto {
    constructor(
        public id: number,
        public idCaravana: number,
        public idServicio: number,
        public idCiudad: number,
        public tipo: string,
        public cantidad: number,
        public precioUnitario: number,
        public fecha: Date
    ) {}
}
