import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewAtivoComponent } from './new-ativo.component';

describe('NewAtivoComponent', () => {
  let component: NewAtivoComponent;
  let fixture: ComponentFixture<NewAtivoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NewAtivoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NewAtivoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
