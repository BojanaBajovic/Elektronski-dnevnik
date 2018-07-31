import { Component, OnInit, Input } from '@angular/core';
import { PredajeEntity } from '../../Entities/PredajeEntity';
import { PredajeService } from '../../services/predaje.service';

@Component({
  selector: 'app-predaje-list',
  templateUrl: './predaje-list.component.html',
  styleUrls: ['./predaje-list.component.css']
})
export class PredajeListComponent implements OnInit {

  @Input() model: PredajeEntity[];

  predajep: PredajeEntity[];

  constructor(private predajeService: PredajeService) { }

  ngOnInit() {
    this.getAllPredaje();
  }

  getAllPredaje(): void {
    this.predajeService.getAllPredaje()
    .subscribe(predajep => this.predajep = predajep);
  }

}
