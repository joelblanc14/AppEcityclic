import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TascaListTreballadorComponent } from './tasca-list-treballador.component';

describe('TascaListTreballadorComponent', () => {
  let component: TascaListTreballadorComponent;
  let fixture: ComponentFixture<TascaListTreballadorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TascaListTreballadorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TascaListTreballadorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
