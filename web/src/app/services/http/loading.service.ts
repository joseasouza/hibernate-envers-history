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

  isWorking: boolean = false;

  constructor() { }

  show() {
    this.isWorking = true;
  }

  stop() {
    this.isWorking = false;
  }

}
