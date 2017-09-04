/*
 * Copyright© mai/2017 - Logique Sistemas®.
 * All rights reserved.
 */

import {Injectable} from "@angular/core";
/**
 * Serviço responsável por tratar o carregando da aplicação
 */
@Injectable()
export class LoadingService {

  carregando: boolean = false;

  constructor() { }

  exibir() {
    this.carregando = true;
    // NProgress.start();
  }

  parar() {
    this.carregando = false;
    // NProgress.done();
  }

}
