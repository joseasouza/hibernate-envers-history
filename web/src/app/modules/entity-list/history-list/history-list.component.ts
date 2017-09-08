import {Component, OnInit, ViewChild} from "@angular/core";
import {EntityService} from "../../../services/model/entity.service";
import {ActivatedRoute, Params} from "@angular/router";
import {History} from "../../../model/history";
import {HttpErrorHandler} from "../../../services/http/httpErrorHandler.service";
import {Subject} from "rxjs/Subject";
import {DataTableDirective} from "angular-datatables";

@Component({
  selector: 'app-history-list',
  templateUrl: './history-list.component.html',
  styleUrls: ['./history-list.component.css']
})
export class HistoryListComponent implements OnInit {

  idSelected : number = null;
  entitySelected : string = "";
  histories : History[] = [];

  dtTrigger = new Subject();

  @ViewChild(DataTableDirective)
  private dtElement : DataTableDirective;

  constructor(private router : ActivatedRoute,
              private entityService : EntityService,
              private errorHandler : HttpErrorHandler) { }

  ngOnInit() {
    this.router.params.subscribe((params: Params) => {
      let id = params['id'];
      let name = params['name'];
      if (id != null) {
        this.idSelected = id;
        this.entitySelected = name;
        this.entityService.getHistories(this.entitySelected, this.idSelected).then(value => {
          this.histories = value;

          if (this.dtElement && this.dtElement.dtInstance) {
            this.dtElement.dtInstance.then((instance) => {
              instance.destroy();
              this.histories = value;
              this.dtTrigger.next();
            });

          } else {
            setTimeout(() => {
              this.histories = value;
              this.dtTrigger.next();
            });
          }

        }).catch(error => {
          this.errorHandler.handle(error);
        });

      }
    });
  }

}
