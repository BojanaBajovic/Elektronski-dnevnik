import { Component, OnInit, Input } from '@angular/core';
import { DrziNastavuService } from '../../services/drzi-nastavu.service';
import { DrziNastavuEntity } from '../../Entities/DrziNastavuEntity';

@Component({
  selector: 'app-drzi-nastavu-list',
  templateUrl: './drzi-nastavu-list.component.html',
  styleUrls: ['./drzi-nastavu-list.component.css']
})
export class DrziNastavuListComponent implements OnInit {

  @Input() model: DrziNastavuEntity[];

  drzeNastavu: DrziNastavuEntity[];

  constructor(private drziNastavuServices: DrziNastavuService) { }

  ngOnInit() {
    this.getAlldrziNastavu();
  }

  getAlldrziNastavu(): void {
    this.drziNastavuServices.getAlldrziNastavu()
    .subscribe(drzeNastavu => this.drzeNastavu = drzeNastavu);
  }

}
