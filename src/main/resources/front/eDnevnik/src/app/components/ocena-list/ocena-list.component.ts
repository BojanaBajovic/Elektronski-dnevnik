import { Component, OnInit, Input } from '@angular/core';
import { OcenaService } from '../../services/ocena.service';
import { OcenaEntity } from '../../Entities/OcenaEntity';

@Component({
  selector: 'app-ocena-list',
  templateUrl: './ocena-list.component.html',
  styleUrls: ['./ocena-list.component.css']
})
export class OcenaListComponent implements OnInit {

  @Input() model: OcenaEntity[];

  ocene: OcenaEntity[];

  constructor(private ocenaService: OcenaService) { }

  ngOnInit() {
    this.getAllOcena();
  }

  getAllOcena(): void {
    this.ocenaService.getAllOcena()
    .subscribe(ocene => this.ocene = ocene);
  }

}
