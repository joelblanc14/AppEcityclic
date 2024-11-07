import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TreballadorUpdateComponent } from './treballador-update.component';

describe('TreballadorUpdateComponent', () => {
  let component: TreballadorUpdateComponent;
  let fixture: ComponentFixture<TreballadorUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TreballadorUpdateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TreballadorUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
