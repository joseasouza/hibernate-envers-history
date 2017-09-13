import {Component, OnInit} from "@angular/core";
import {EntityService} from "../../../services/model/entity.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {History} from "../../../model/history";
import {Location} from "@angular/common";
import {NotifierService} from "../../../services/notificacao/notifier.service";
import {HttpErrorHandler} from "../../../services/http/httpErrorHandler.service";

@Component({
  selector: 'app-history-detail',
  templateUrl: './history-detail.component.html',
  styleUrls: ['./history-detail.component.css']
})
export class HistoryDetailComponent implements OnInit {

  selectedEntity = "";
  idSelected = "";
  history = new History();

  constructor(private route : ActivatedRoute,
              private location : Location,
              private notifier : NotifierService,
              private errorHandler : HttpErrorHandler,
              private entityService : EntityService) { }

  ngOnInit() {
    this.route.params.subscribe((params: Params) => {
      let id = params['id'];
      let name = params['name'];
      let revision = params['revision'];
      if (id != null) {
        this.idSelected = id;
        this.selectedEntity = name;
        this.entityService.getHistory(name, id, revision).then(value => {
          this.history = value;
        }).catch(error => {
          this.errorHandler.handle(error);
        });
      }
    });
  }

  revert() {

    this.entityService.revertRecord(this.selectedEntity, this.idSelected, this.history.revision).then(value => {
      this.notifier.success(value.message, "Sucesso!");
      this.location.back();
    }).catch(error => {
      this.errorHandler.handle(error);
    });
  }
}
