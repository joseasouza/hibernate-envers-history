import {Component, OnInit, ViewChild} from "@angular/core";
import {ActivatedRoute, Params} from "@angular/router";
import {Register} from "../../../model/Register";
import {EntityService} from "../../../services/model/entity.service";
import {HttpErrorHandler} from "../../../services/http/httpErrorHandler.service";
import {Subject} from "rxjs/Subject";
import {DataTableDirective} from "angular-datatables";
import {DataTablesLanguage} from "../../../model/datatablesPtBrLang";

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  entitySelected = "No Entity Selected";
  registers: Register[] = [];
  dtOptions: DataTables.Settings = {};
  dtTrigger = new Subject();

  @ViewChild(DataTableDirective)
  private dtElement : DataTableDirective;


  constructor(private router : ActivatedRoute,
              private entityService : EntityService,
              private errorHandler : HttpErrorHandler) { }

  ngOnInit() {
    this.dtOptions = {
      language : DataTablesLanguage
    };

    this.router.params.subscribe((params: Params) => {
      let name = params['name'];

      if (name != null) {
        this.entitySelected = name;
        this.entityService.getRegisters(this.entitySelected).then(value => {
          this.registers = value;

          if (this.dtElement && this.dtElement.dtInstance) {
            this.dtElement.dtInstance.then((instance) => {
              instance.destroy();
              this.registers = value;
              this.dtTrigger.next();
            });

          } else {
            setTimeout(() => {
              this.registers = value;
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
