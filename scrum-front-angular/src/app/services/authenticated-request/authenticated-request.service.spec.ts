import { TestBed } from '@angular/core/testing';

import { AuthenticatedRequestService } from './authenticated-request.service';

describe('AuthenticatedRequestService', () => {
  let service: AuthenticatedRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthenticatedRequestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
