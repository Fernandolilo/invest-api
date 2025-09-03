import { TestBed } from '@angular/core/testing';

import { HomebrokerService } from './homebroker.service';

describe('HomebrokerService', () => {
  let service: HomebrokerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HomebrokerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
