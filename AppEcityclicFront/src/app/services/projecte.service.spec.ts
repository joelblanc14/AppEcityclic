import { TestBed } from '@angular/core/testing';

import { ProjecteService } from './projecte.service';

describe('ProjecteService', () => {
  let service: ProjecteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProjecteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
