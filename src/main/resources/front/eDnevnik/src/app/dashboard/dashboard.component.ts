import { Component, OnInit } from '@angular/core';
import { UcenikEntity } from '../Entities/ucenik-enitity';
import { UcenikService } from '../services/ucenik.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit {
  ucenici: UcenikEntity[] = [];

  constructor(private ucenikService: UcenikService) { }

  ngOnInit() {
    this.getAllUcenik();
  }

  getAllUcenik(): void {
    this.ucenikService.getAllUcenik()
      .subscribe(ucenici => this.ucenici = ucenici.slice(1, 5));
  }
}
