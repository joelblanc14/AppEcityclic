import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjecteListComponent } from './projecte-list.component';

describe('ProjecteListComponent', () => {
  let component: ProjecteListComponent;
  let fixture: ComponentFixture<ProjecteListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProjecteListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProjecteListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
