import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { EKorisnikRole } from '../../Entities/EKorisnikRole';
import { RoditeljEntity } from '../../Entities/RoditeljEntity';
import { RoditeljService } from '../../services/roditelj.service';

@Component({
  selector: 'app-roditelj-edit',
  templateUrl: './roditelj-edit.component.html',
  styleUrls: ['./roditelj-edit.component.css']
})
export class RoditeljEditComponent implements OnInit {
  roditelj: RoditeljEntity;

  constructor(private route: ActivatedRoute,
    private router: Router,
    private location: Location,
    private roditeljService: RoditeljService) {
this.roditelj = new RoditeljEntity();
  }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.roditeljService.getRoditeljById(id).subscribe(a => this.roditelj = a);
  }

  updateRoditelj() {
    this.roditeljService.updateRoditelj(this.roditelj)
    .subscribe((roditelj: RoditeljEntity) =>  {
      alert('Roditelj ' + roditelj.id + ' je uspešno izmenjen!');
      this.router.navigate(['/roditelj']);
    });
  }


    // goBack() {
    //   this.location.back();
    // }
}
