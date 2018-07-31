import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { EKorisnikRole } from '../../Entities/EKorisnikRole';
import { NastavnikEntity } from '../../Entities/NastavnikEntity';
import { NastavnikService } from '../../services/nastavnik.service';

@Component({
  selector: 'app-nastavnik-edit',
  templateUrl: './nastavnik-edit.component.html',
  styleUrls: ['./nastavnik-edit.component.css']
})
export class NastavnikEditComponent implements OnInit {
  nastavnik: NastavnikEntity;

  constructor(private route: ActivatedRoute,
    private router: Router,
    private location: Location,
    private nastavnikService: NastavnikService) {
this.nastavnik = new NastavnikEntity();
  }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.nastavnikService.getNastavnikById(id).subscribe(a => this.nastavnik = a);
  }

  updateNastavnik() {
    this.nastavnikService.updateNastavnik(this.nastavnik)
    .subscribe((nastavnik: NastavnikEntity) =>  {
      alert('Nastavnik ' + nastavnik.id + ' je uspešno izmenjen!');
      this.router.navigate(['/nastavnik']);
    });
  }


    // goBack() {
    //   this.location.back();
    // }
}
