import { PredmetEntity } from './PredmetEntity';
import { RazredEntity } from './RazredEntity';

export class RazredImaPredmetEntity {
    id: Number;
    predmet: PredmetEntity;
    razred: RazredEntity [];
    razredImaPredmet: RazredImaPredmetEntity [];
}
