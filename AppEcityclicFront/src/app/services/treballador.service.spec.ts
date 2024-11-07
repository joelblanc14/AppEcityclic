import { TestBed } from '@angular/core/testing';

import { TreballadorService } from './treballador.service';

describe('TreballadorService', () => {
  let service: TreballadorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TreballadorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
