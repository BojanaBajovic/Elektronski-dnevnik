import { PredajeEntity } from './PredajeEntity';
import { OdeljenjeEntity } from './OdeljenjeEntity';
import { OcenaEntity } from './OcenaEntity';

export class DrziNastavuEntity {
    id: Number;
    predaje: PredajeEntity;
    odeljenje: OdeljenjeEntity;
    ocena: OcenaEntity [];
}
