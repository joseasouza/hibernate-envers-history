import {Injectable} from "@angular/core";
import {HttpService} from "../http/http.service";
import {Register} from "../../model/Register";
import {Entity} from "../../model/entity";
import {History} from "../../model/history";
import {Message} from "../../model/Message";

@Injectable()
export class EntityService {

  public static URL = "audit/entities";

  constructor(private httpService: HttpService) { }


  listEntities() : Promise<Entity[]> {
    return new Promise((resolve, reject) => {
      this.httpService.get("/entities").subscribe(
        value => {
          resolve(value);
      }, error => {
          reject(error)
      });
    })
  }

  getRegisters(entityName) : Promise<Register[]> {
    return new Promise((resolve, reject) => {
      this.httpService.get("/entities/registers/" + entityName).subscribe(
        value => {
          resolve(value);
        }, error => {
          reject(error)
        });
    });
  }

  getRegister(entityName, id) : Promise<Register> {
    return new Promise((resolve, reject) => {
      this.httpService.get("/entities/registers/" + entityName + "/" + id).subscribe(
        value => {
          resolve(value);
        }, error => {
          reject(error)
        });
    });
  }

  getHistories(entityName, id) : Promise<History[]> {
    return new Promise((resolve, reject) => {
      this.httpService.get("/entities/history/" + entityName + "/" + id).subscribe(
        value => {
          resolve(value);
        }, error => {
          reject(error)
        });
    });
  }

  getHistory(entityName, entityId, revisionId) : Promise<History> {
    return new Promise((resolve, reject) => {
      this.httpService.get("/entities/history/" + entityName + "/" + entityId + "/" + revisionId).subscribe(
        value => {
          resolve(value);
        }, error => {
          reject(error)
        });
    });
  }

  revertRecord(name, id, revision) : Promise<Message> {
    return new Promise((resolve, reject) => {
      this.httpService.post("/entities/revert/", JSON.stringify({ name, id, revision })).subscribe(
        value => {
          resolve(value);
        }, error => {
          reject(error)
        });
    });
  }



}
