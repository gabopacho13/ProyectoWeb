export class ServiciosCompradosDto {
    constructor(
        public id: number,
        public idServicio: number,
        public idCaravana: number,
        public fechaCompra: String
    ) {}
}

