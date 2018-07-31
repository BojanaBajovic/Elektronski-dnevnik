import {EKorisnikRole} from '../Entities/EKorisnikRole';
import { PredajeEntity } from './PredajeEntity';

export class NastavnikEntity {
    id: Number;
    ime: string;
    prezime: string;
    username: string;
    password: string;
    korisnikRole: EKorisnikRole;
    repeatedPassword: string;
    email: string;
    predaje: PredajeEntity [];

}

