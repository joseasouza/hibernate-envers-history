/*
 * Copyright© mai/2017 - Logique Sistemas®.
 * All rights reserved.
 */

import {Injectable} from "@angular/core";
import {Router} from "@angular/router";

/**
 * Serviço responsável por tratar os erros mais comuns que vem de {@link HttpService}
 */
@Injectable()
export class HttpErrorHandler {

  constructor(private router : Router) { }

  handle(mensagemErro : Object) {
    console.debug('mensagemErro', mensagemErro);
    // switch (mensagemErro.status) {
    //   case 0:
    //     // this.notificador.error(mensagemErro.notificacoes.join(", "), "Sem comunicação com o servidor! ");
    //     break;
    //   case 500:
    //     // this.notificador.error(mensagemErro.notificacoes.join(", "), "Erro no servidor!");
    //     break;
    //   case 401:
    //     // this.usuarioLogadoService.setLogado(null);
    //     // this.router.navigate(["login"]);
    //     // this.notificador.atencao(mensagemErro.notificacoes.join(", "), "Falha de Autorização! ");
    //     break;
    //
    //   case 403:
    //     // this.notificador.atencao(mensagemErro.notificacoes.join(", "), "Acesso Negado! ");
    //     break;
    //   default:
    //     // this.notificador.error(mensagemErro.notificacoes.join(", "), "Falha na operação! ");
    //
    // }

  }

}
