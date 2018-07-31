import { TestBed, inject } from '@angular/core/testing';

import { DrziNastavuService } from './drzi-nastavu.service';

describe('DrziNastavuService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DrziNastavuService]
    });
  });

  it('should be created', inject([DrziNastavuService], (service: DrziNastavuService) => {
    expect(service).toBeTruthy();
  }));
});
