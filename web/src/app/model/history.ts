/**
 * Created by victor on 01/09/17.
 */
export class History {
  revision : number = null;
  revisionType : number = null;
  date : Date = null;
  description : String = "";
  object : Object = null;
}

export const RevisionType = {
  0: "INSERT",
  1: "EDIT",
  2: "DELETE"
}
