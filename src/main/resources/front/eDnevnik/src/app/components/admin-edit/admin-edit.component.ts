import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { AdminService } from '../../services/admin.service';
import { AdminEntity } from '../../entities/admin-entity';
import { EKorisnikRole } from '../../Entities/EKorisnikRole';

@Component({
  selector: 'app-admin-edit',
  templateUrl: './admin-edit.component.html',
  styleUrls: ['./admin-edit.component.css']
})
export class AdminEditComponent implements OnInit {
  admin: AdminEntity;

  constructor(private route: ActivatedRoute,
    private router: Router,
    private location: Location,
    private adminService: AdminService) {
this.admin = new AdminEntity();
  }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.adminService.getAdminById(id).subscribe(a => this.admin = a);
  }

  updateAdmin() {
    this.adminService.updateAdmin(this.admin)
    .subscribe((admin: AdminEntity) =>  {
      alert('Admin ' + admin.id + ' je uspešno izmenjen!');
      this.router.navigate(['/admin']);
    });
  }


    // goBack() {
    //   this.location.back();
    // }
}

