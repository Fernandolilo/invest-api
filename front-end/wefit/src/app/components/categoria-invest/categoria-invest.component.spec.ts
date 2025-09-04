import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoriaInvestComponent } from './categoria-invest.component';

describe('CategoriaInvestComponent', () => {
  let component: CategoriaInvestComponent;
  let fixture: ComponentFixture<CategoriaInvestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CategoriaInvestComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CategoriaInvestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
