import { Component, OnInit, Input } from '@angular/core';
import { RoditeljEntity } from '../../Entities/RoditeljEntity';
import { RoditeljService } from '../../services/roditelj.service';

@Component({
  selector: 'app-roditelj-list',
  templateUrl: './roditelj-list.component.html',
  styleUrls: ['./roditelj-list.component.css']
})
export class RoditeljListComponent implements OnInit {

  @Input() model: RoditeljEntity[];

  roditelji: RoditeljEntity[];

  constructor(private roditeljService: RoditeljService) { }

  ngOnInit() {
    this.getAllRoditelj();
  }

  getAllRoditelj(): void {
    this.roditeljService.getAllRoditelj()
    .subscribe(roditelji => this.roditelji = roditelji);
  }

}
