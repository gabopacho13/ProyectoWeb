export class PartidaDto {
    constructor(
        public id: number,
        public tiempoLimite: number,
        public gananciaMinima: number,
        public tiempoInicio: string, // ISO 8601 string para fechas
        public tiempoActual: number
    ) {}
}
