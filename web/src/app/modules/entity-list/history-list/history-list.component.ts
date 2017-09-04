import {Component, OnInit} from "@angular/core";
import {EntityService} from "../../../services/model/entity.service";
import {ActivatedRoute, Params} from "@angular/router";
import {History} from "../../../model/history";

@Component({
  selector: 'app-history-list',
  templateUrl: './history-list.component.html',
  styleUrls: ['./history-list.component.css']
})
export class HistoryListComponent implements OnInit {

  idSelected : number = null;
  entitySelected : string = "";
  histories : History[] = [];

  constructor(private router : ActivatedRoute,
              private entityService : EntityService) { }

  ngOnInit() {
    this.router.params.subscribe((params: Params) => {
      let id = params['id'];
      let name = params['name'];
      if (id != null) {
        this.idSelected = id;
        this.entitySelected = name;
        this.entityService.getHistories(this.entitySelected, this.idSelected).then(value => {
          this.histories = value;
        });

      }
    });
  }

}
