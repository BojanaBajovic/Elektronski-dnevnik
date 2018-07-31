import { UcenikEntity } from './ucenik-enitity';
import { DrziNastavuEntity } from './DrziNastavuEntity';

export class OcenaEntity {
    id: Number;
    vrednostOcene: Number;
    datum: string;
    polugodiste: Number;
    ucenik: UcenikEntity;
    drziNastavu: DrziNastavuEntity;
}
