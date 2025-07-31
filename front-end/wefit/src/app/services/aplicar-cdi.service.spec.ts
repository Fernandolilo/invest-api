import { TestBed } from '@angular/core/testing';

import { AplicarCdiService } from './aplicar-cdi.service';

describe('AplicarCdiService', () => {
  let service: AplicarCdiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AplicarCdiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
