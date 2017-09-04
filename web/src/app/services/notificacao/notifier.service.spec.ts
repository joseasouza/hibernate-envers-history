import {inject, TestBed} from "@angular/core/testing";
import {NotificadorService} from "./notificador.service";


describe('NotificadorService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NotificadorService]
    });
  });

  it('should ...', inject([NotificadorService], (service: NotificadorService) => {
    expect(service).toBeTruthy();
  }));
});
