import {Injectable} from "@angular/core";

declare var $: any;
declare var jQuery: any;

@Injectable()
export class NotifierService {

  info(message: string, title ?: string) {
    this.exibirMensagem(message, title, 'primary');
  }

  warn(message: string, title ?: string) {
    this.exibirMensagem(message, title, 'warning');
  }

  error(message: string, title ?: string) {
    this.exibirMensagem(message, title, 'danger');
  }

  success(message: string, title ?: string) {
    this.exibirMensagem(message, title, 'success');
  }

  private exibirMensagem(message : string, title : string, type : string) {
    $.notify({
      title: title ? '<strong>' + title + '</strong>' : '',
      message: message
    }, {
      type: type
    });
  }
}
