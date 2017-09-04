/*
 * Copyright© mai/2017 - Logique Sistemas®.
 * All rights reserved.
 */

import { TestBed, inject } from '@angular/core/testing';

import { HttpService } from './http.service';

describe('HttpErrorHandler', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HttpService]
    });
  });

  it('should be created', inject([HttpService], (service: HttpService) => {
    expect(service).toBeTruthy();
  }));
});
