import {Component, OnInit} from "@angular/core";
import {EntityService} from "../../../services/model/entity.service";
import {HttpErrorHandler} from "../../../services/http/httpErrorHandler.service";
import {Entity} from "../../../model/entity";
import {Router} from "@angular/router";

@Component({
  selector: 'app-menu-left',
  templateUrl: './menu-left.component.html',
  styleUrls: ['./menu-left.component.css']
})
export class MenuLeftComponent implements OnInit {

  entities : Entity[] = [];
  selectedEntity : Entity = new Entity();
  isWorking = false;

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
    this.isWorking = true;
    this.entityService.listEntities().then(data => {
      this.isWorking = false;
      this.entities = data;
      if (this.entities.length > 0) {
        this.selectedEntity = this.entities[0];
        this.router.navigate(['/', this.selectedEntity.name]);
      }
    }).catch(error => {
      this.isWorking = false;
      this.errorHandler.handle(error);
    })
  }

}
