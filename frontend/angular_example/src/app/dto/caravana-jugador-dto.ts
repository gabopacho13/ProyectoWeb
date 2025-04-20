export class CaravanaJugadorDto {
    idCaravana: number;
    jugadoresIds: number[];
    constructor(idCaravana: number, jugadoresIds: number[]) {
        this.idCaravana = idCaravana;
        this.jugadoresIds = jugadoresIds;
    }
}
