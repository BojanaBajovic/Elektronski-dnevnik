import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

import { AdminService } from '../../services/admin.service';
import { AdminEntity } from '../../entities/admin-entity';
import { EKorisnikRole } from '../../Entities/EKorisnikRole';

@Component({
  selector: 'app-admin-form',
  templateUrl: './admin-form.component.html',
  styleUrls: ['./admin-form.component.css']
})
export class AdminFormComponent implements OnInit {
  admin: AdminEntity;

  constructor(private router: Router,
    private location: Location,
    private adminService: AdminService) {
    this.admin = new AdminEntity();
  }

  ngOnInit() {
  }

  addNewAdmin(ime: string, prezime: string, username: string, password: string, repeatedPassword: string, email: string) {
      this.admin.ime = ime;
      this.admin.prezime = prezime;
      this.admin.username = username;
      this.admin.password = password;
      this.admin.repeatedPassword = repeatedPassword;
      this.admin.email = email;

      this.adminService.addNewAdmin(this.admin)
        .subscribe((admin: AdminEntity) => {
          alert('Admin ' + this.admin.username + ' je uspešno dodat!');
          this.router.navigate(['/admin']);
        });
    }

    // goBack() {
    //   this.location.back();
    // }
}
