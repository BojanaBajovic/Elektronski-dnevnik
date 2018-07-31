import { NastavnikEntity } from './NastavnikEntity';
import { PredmetEntity } from './PredmetEntity';
import { DrziNastavuEntity } from './DrziNastavuEntity';

export class PredajeEntity {
    id: Number;
    nastavnik: NastavnikEntity;
    predmet: PredmetEntity;
    drziNastavu: DrziNastavuEntity;
}
