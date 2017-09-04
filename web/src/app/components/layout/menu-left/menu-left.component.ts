import { Component, OnInit } from '@angular/core';
import {EntityService} from "../../../services/model/entity.service";
import {HttpErrorHandler} from "../../../services/http/httpErrorHandler.service";
import {Entity} from "../../../model/entity";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-menu-left',
  templateUrl: './menu-left.component.html',
  styleUrls: ['./menu-left.component.css']
})
export class MenuLeftComponent implements OnInit {

  entities : Entity[] = [];
  selectedEntity : Entity = new Entity();

  constructor(private entityService : EntityService,
              private errorHandler: HttpErrorHandler,
              private router: Router) { }

  ngOnInit() {
    this.list();
  }

  selectEntity(entity) {
    this.selectedEntity = entity;

  }

  list() {
    this.entityService.listEntities().then(data => {
      this.entities = <Entity[]> data;
      if (this.entities.length > 0) {
        this.selectedEntity = this.entities[0];
        this.router.navigate(['/', this.selectedEntity.name]);
      }
    }).catch(error => {
      this.errorHandler.handle(error);
    })
  }

}
