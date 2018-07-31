import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RoditeljEditComponent } from './roditelj-edit.component';

describe('RoditeljEditComponent', () => {
  let component: RoditeljEditComponent;
  let fixture: ComponentFixture<RoditeljEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RoditeljEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RoditeljEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
