export class CaravanaDto {
    public id: number;
    public fechaCreacion: string;
    public nombre: string;
    public velocidadBase: number;
    public velocidadActual: number;
    public capacidadBase: number;
    public capacidadActual: number;
    public dinero: number;
    public saludActual: number;
    public saludMaxima: number;
    public tiempoAcumulado: number;
    public tieneGuardias: boolean;
    constructor(
        id: number,
        fechaCreacion: string,
        nombre: string,
        velocidadBase: number,
        capacidadBase: number,
        dinero: number,
        saludMaxima: number,
        tiempoAcumulado: number
    ) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.nombre = nombre;
        this.velocidadBase = velocidadBase;
        this.velocidadActual = velocidadBase;
        this.capacidadBase = capacidadBase;
        this.capacidadActual = capacidadBase;
        this.dinero = dinero;
        this.saludActual = saludMaxima;
        this.saludMaxima = saludMaxima;
        this.tiempoAcumulado = tiempoAcumulado;
        this.tieneGuardias = false; // Inicializar con valor por defecto
    }
}
