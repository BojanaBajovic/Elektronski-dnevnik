import {EKorisnikRole} from '../Entities/EKorisnikRole';
import { RoditeljEntity } from './RoditeljEntity';
import { OdeljenjeEntity } from './OdeljenjeEntity';
import { OcenaEntity } from './OcenaEntity';

export class UcenikEntity {
    id: Number;
    ime: string;
    prezime: string;
    username: string;
    password: string;
    korisnikRole: EKorisnikRole;
    repeatedPassword: string;
    roditelj: RoditeljEntity;
    odeljenje: OdeljenjeEntity;
    ocena: OcenaEntity [];
}
