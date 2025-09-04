import { TestBed } from '@angular/core/testing';

import { CategoriaInvestService } from './categoria-invest.service';

describe('CategoriaInvestService', () => {
  let service: CategoriaInvestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CategoriaInvestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
