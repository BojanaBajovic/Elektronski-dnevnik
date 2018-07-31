import { PredajeEntity } from './PredajeEntity';
import { RazredImaPredmetEntity } from './RazredImaPredmetEntity';

export class PredmetEntity {
    id: Number;
    nazivPredmeta: string;
    fond: Number;
    predaje: PredajeEntity [];
    razredImaPredmet: RazredImaPredmetEntity [];
}
