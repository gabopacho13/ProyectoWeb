export class PartidaCaravanaDto {
    idPartida: number;
    caravanasIds: number[];

    constructor(idPartida: number, caravanasIds: number[]) {
        this.idPartida = idPartida;
        this.caravanasIds = caravanasIds;
    }
}