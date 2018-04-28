import { TestBed, inject } from '@angular/core/testing';

import { SocketsServiceService } from './sockets-service.service';

describe('SocketsServiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SocketsServiceService]
    });
  });

  it('should be created', inject([SocketsServiceService], (service: SocketsServiceService) => {
    expect(service).toBeTruthy();
  }));
});
