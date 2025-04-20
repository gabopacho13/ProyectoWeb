export class CaravanaDto {
    constructor(
        public id: number,
        public fechaCreacion: Date,
        public nombre: string,
        public velocidadBase: number,
        public velocidadActual: number,
        public capacidadBase: number,
        public capacidadActual: number,
        public dinero: number,
        public saludActual: number,
        public saludMaxima: number,
        public tiempoAcumulado: number,
        public tieneGuardias: boolean
    ) {}
}
