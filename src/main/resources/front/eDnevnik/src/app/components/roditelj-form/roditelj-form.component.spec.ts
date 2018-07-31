import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RoditeljFormComponent } from './roditelj-form.component';

describe('RoditeljFormComponent', () => {
  let component: RoditeljFormComponent;
  let fixture: ComponentFixture<RoditeljFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RoditeljFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RoditeljFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
