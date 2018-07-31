import {EKorisnikRole} from '../Entities/EKorisnikRole';

export class AdminEntity {
    id: Number;
    ime: string;
    prezime: string;
    username: string;
    password: string;
    korisnikRole: EKorisnikRole;
    repeatedPassword: string;
    email: string;

}

