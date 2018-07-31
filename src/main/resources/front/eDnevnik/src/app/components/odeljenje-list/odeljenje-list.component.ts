import { Component, OnInit, Input } from '@angular/core';
import { OdeljenjeEntity } from '../../Entities/OdeljenjeEntity';
import { OdeljenjeService } from '../../services/odeljenje.service';

@Component({
  selector: 'app-odeljenje-list',
  templateUrl: './odeljenje-list.component.html',
  styleUrls: ['./odeljenje-list.component.css']
})
export class OdeljenjeListComponent implements OnInit {

  @Input() model: OdeljenjeEntity[];

    odeljenja: OdeljenjeEntity[];

    constructor(private odeljenjeServices: OdeljenjeService) { }

    ngOnInit() {
      this.getAllOdeljenja();
    }

    getAllOdeljenja(): void {
      this.odeljenjeServices.getAllOdeljenja()
      .subscribe(odeljenja => this.odeljenja = odeljenja);
    }

  }
