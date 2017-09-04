import { Pipe, PipeTransform } from '@angular/core';
import {RevisionType} from "../model/history";

@Pipe({
  name: 'revisionType',
  pure: false
})
export class RevisionTypePipe implements PipeTransform {
  transform(value, args: string[]): any {
    return RevisionType[value];
  }
}
