import {Injectable} from "@angular/core";
import {HttpService} from "../http/http.service";
import {Register} from "../../model/Register";
import {Entity} from "../../model/entity";
import {History} from "../../model/history";
import {reject} from "q";

@Injectable()
export class EntityService {

  public static URL = "audit/entities";

  constructor(private httpService: HttpService) { }


  listEntities() : Promise<Entity[]> {
    return new Promise((resolve, reject) => {
      resolve([
        { name: "ANALISE"},
        { name: "ANALISE_ALARME_NIVEL_PERFORMANCE*"},
        { name: "DASHBOARD"},
        { name: "TABELA_INDICADORES"},
        { name: "TABELA_INDICADORES1"},
        { name: "TABELA_INDICADORES2"},
        { name: "TABELA_INDICADORES3"},
        { name: "TABELA_INDICADORES4"},
        { name: "TABELA_INDICADORES5"},
        { name: "TABELA_INDICADORES6"},
        { name: "TABELA_INDICADORES7"},
        { name: "TABELA_INDICADORES8"},
        { name: "TABELA_INDICADORES9"},
        { name: "TABELA_INDICADORES10"},
        { name: "TABELA_INDICADORES11"},
        { name: "TABELA_INDICADORES12"},
        { name: "TABELA_INDICADORES13"},
        { name: "TABELA_INDICADORES14"},
        { name: "TABELA_INDICADORES15"},
        { name: "TABELA_INDICADORES16"},
        { name: "TABELA_INDICADORES17"},
        { name: "TABELA_INDICADORES18"},
        { name: "TABELA_INDICADORES19"},
        { name: "TABELA_INDICADORES20"},
        { name: "TABELA_INDICADORES21"},
        { name: "TABELA_INDICADORES22"},
        { name: "TABELA_INDICADORES23"}
      ]);
      // this.httpService.get(EntityService.URL).subscribe(
      //   data => resolve(data),
      //   error => reject(error))
    })
  }

  getRegisters(entityName) : Promise<Register[]> {
    return new Promise((resolve, reject) => {
      resolve([
        { id : 1, description : "ANAL_DS_NOME: Analise, ANAL_DS_DESCRICAO: Descricao" },
        { id : 2, description : "ANAL_DS_NOME: Analise Anunciados por tempo, ANAL_DS_DESCRICAO: Descricao Teste" },
        { id : 3, description : "ANAL_DS_NOME: Analise Teste, ANAL_DS_DESCRICAO: Descricao Teste 2" },
        ])
    });
  }

  getRegister(entityName, id) : Promise<Register> {
    return new Promise((resolve, reject) => {
      resolve({ id : 1, description: "ANAL_DS_NOME: Analise", object : {nome: "Análise", quantidade: 5 }})
    });
  }

  getHistories(entityName, id) : Promise<History[]> {
    return new Promise((resolve, reject) => {
      resolve([
        {revision: 1, revisionType: 0, date: '2017-09-01 00:00:00', description : "ANAL_DS_NOME : Analise, ANAL_DS_DESCRICAO : Descricao"},
        {revision: 2, revisionType: 1, date: '2017-09-02 14:10:00', description : "ANAL_DS_NOME : Analise 2, ANAL_DS_DESCRICAO : Descricao 2"},
        {revision: 3, revisionType: 2, date: '2017-09-02 16:11:00', description : "ANAL_DS_NOME : Analise 3, ANAL_DS_DESCRICAO : Descricao 3"},
      ])
    });
  }

  getHistory(entityName, entityId, revisionId) : Promise<History> {
    return new Promise((resolve, reject) => {
      resolve(
        {revision: 1, revisionType: 0, date: '2017-09-01 00:00:00',
          description : "ANAL_DS_NOME : Analise, ANAL_DS_DESCRICAO : Descricao",
          object : { nome : "Análise Anunciados por Tempo", quantidade : 7 }
        },
      )
    });
  }

  revertRecord(entityName, entityId, revistionId) : Promise<any> {
    return new Promise((resolve, reject) => {
      resolve();
    });
  }



}
