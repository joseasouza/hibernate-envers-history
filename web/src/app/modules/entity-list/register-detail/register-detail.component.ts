import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Params} from "@angular/router";
import {EntityService} from "../../../services/model/entity.service";
import {Register} from "../../../model/Register";
import {HttpErrorHandler} from "../../../services/http/httpErrorHandler.service";

@Component({
  selector: 'app-register-detail',
  templateUrl: './register-detail.component.html',
  styleUrls: ['./register-detail.component.css']
})
export class RegisterDetailComponent implements OnInit {

  register : Register = new Register();
  selectedEntity = "";
  idSelected = null;

  constructor(private router : ActivatedRoute,
              private entityService : EntityService,
              private errorHandler : HttpErrorHandler) { }

  ngOnInit() {
    this.router.params.subscribe((params: Params) => {
      this.selectedEntity = params['name'];
      this.idSelected  = params['id'];
      if (this.selectedEntity != null && this.idSelected != null) {
        this.entityService.getRegister(this.selectedEntity, this.idSelected).then(value => {
          this.register = value;
        }).catch(error => {
          this.errorHandler.handle(error);
        });

      }
    });
  }

}
