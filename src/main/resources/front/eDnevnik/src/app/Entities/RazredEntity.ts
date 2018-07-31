import { OdeljenjeEntity } from './OdeljenjeEntity';
import { RazredImaPredmetEntity } from './RazredImaPredmetEntity';

export class RazredEntity {
    id: Number;
    brojRazreda: Number;
    odeljenje: OdeljenjeEntity [];
    razredImaPredmet: RazredImaPredmetEntity [];
}
