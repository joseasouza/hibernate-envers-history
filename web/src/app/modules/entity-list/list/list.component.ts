import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Params} from "@angular/router";
import {Register} from "../../../model/Register";
import {EntityService} from "../../../services/model/entity.service";
import {HttpErrorHandler} from "../../../services/http/httpErrorHandler.service";

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  entitySelected = "No Entity Selected";
  registers: Register[] = [];


  constructor(private router : ActivatedRoute,
              private entityService : EntityService,
              private errorHandler : HttpErrorHandler) { }

  ngOnInit() {
    this.router.params.subscribe((params: Params) => {
      let name = params['name'];
      if (name != null) {
        this.entitySelected = name;

        this.entityService.getRegisters(this.entitySelected).then(value => {
          this.registers = value;
        }).catch(error => {
          this.errorHandler.handle(error);
        });

      }
    });
  }

}
