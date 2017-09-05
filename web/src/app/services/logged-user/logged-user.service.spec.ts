import { TestBed, inject } from '@angular/core/testing';

import { UsuarioLogadoService } from './usuario-logado.service';

describe('LoggedUserService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [UsuarioLogadoService]
    });
  });

  it('should be created', inject([UsuarioLogadoService], (service: UsuarioLogadoService) => {
    expect(service).toBeTruthy();
  }));
});
