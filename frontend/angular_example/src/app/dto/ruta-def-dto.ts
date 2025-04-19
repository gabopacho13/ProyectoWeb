export class RutaDefDto {
    constructor(
        public id:number,
        public distancia:number,
        public es_segura:boolean,
        public dano:number,
        public rutasDestinoIds: number,
        public rutasOrigenIds: number,
    ){}
}
