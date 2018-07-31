import { EKorisnikRole } from './EKorisnikRole';
import { UcenikEntity } from './ucenik-enitity';

export class RoditeljEntity {
    id: Number;
    ime: string;
    prezime: string;
    username: string;
    password: string;
    korisnikRole: EKorisnikRole;
    repeatedPassword: string;
    email: string;
    ucenik: UcenikEntity [];

}
