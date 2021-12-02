import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CentroSaludComponent } from './centro-salud.component';

describe('CentroSaludComponent', () => {
  let component: CentroSaludComponent;
  let fixture: ComponentFixture<CentroSaludComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CentroSaludComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CentroSaludComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
