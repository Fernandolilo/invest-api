import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewInvestimentoComponent } from './new-investimento.component';

describe('NewInvestimentoComponent', () => {
  let component: NewInvestimentoComponent;
  let fixture: ComponentFixture<NewInvestimentoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NewInvestimentoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NewInvestimentoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
