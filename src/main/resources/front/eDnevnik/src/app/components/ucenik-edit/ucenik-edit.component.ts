import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { UcenikEntity } from '../../Entities/ucenik-enitity';
import { UcenikService } from '../../services/ucenik.service';
import { EKorisnikRole } from '../../Entities/EKorisnikRole';

@Component({
  selector: 'app-ucenik-edit',
  templateUrl: './ucenik-edit.component.html',
  styleUrls: ['./ucenik-edit.component.css']
})
export class UcenikEditComponent implements OnInit {
  ucenik: UcenikEntity;

  constructor(private route: ActivatedRoute,
    private router: Router,
    private location: Location,
    private ucenikService: UcenikService) {
this.ucenik = new UcenikEntity();
  }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.ucenikService.getUcenikById(id).subscribe(a => this.ucenik = a);
  }

  updateUcenik() {
    this.ucenikService.updateUcenik(this.ucenik)
    .subscribe((ucenik: UcenikEntity) =>  {
      alert('Ucenik ' + ucenik.id + ' je uspešno izmenjen!');
      this.router.navigate(['/ucenik']);
    });
  }

}
