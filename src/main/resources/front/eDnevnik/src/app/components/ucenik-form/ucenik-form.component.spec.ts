import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UcenikFormComponent } from './ucenik-form.component';

describe('UcenikFormComponent', () => {
  let component: UcenikFormComponent;
  let fixture: ComponentFixture<UcenikFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UcenikFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UcenikFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
