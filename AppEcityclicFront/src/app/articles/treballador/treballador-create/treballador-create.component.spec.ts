import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TreballadorCreateComponent } from './treballador-create.component';

describe('TreballadorCreateComponent', () => {
  let component: TreballadorCreateComponent;
  let fixture: ComponentFixture<TreballadorCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TreballadorCreateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TreballadorCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
