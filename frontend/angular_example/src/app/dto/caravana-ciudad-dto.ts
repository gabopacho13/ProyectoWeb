export class CaravanaCiudadDto {
    idCiudad : number;
    caravanasIds : number[];
    constructor(idCiudad: number, caravanasIds: number[]) {
        this.idCiudad = idCiudad;
        this.caravanasIds = caravanasIds;
    }
}
