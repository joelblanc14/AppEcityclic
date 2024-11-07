import { TestBed } from '@angular/core/testing';

import { TascaService } from './tasca.service';

describe('TascaService', () => {
  let service: TascaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TascaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
