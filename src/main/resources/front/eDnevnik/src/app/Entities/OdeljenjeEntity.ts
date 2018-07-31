import { UcenikEntity } from './ucenik-enitity';
import { DrziNastavuEntity } from './DrziNastavuEntity';
import { RazredEntity } from './RazredEntity';

export class OdeljenjeEntity {
    id: Number;
    brojOdeljenja: string;
    ucenik: UcenikEntity [];
    razred: RazredEntity;
    drziNastavu: DrziNastavuEntity [];
}
