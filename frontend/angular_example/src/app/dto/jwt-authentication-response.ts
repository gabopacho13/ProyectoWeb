export class JwtAuthenticationResponse {
    constructor(public token: string, public email: string, public role:string){}
}
