export class Character {
  constructor(cid, name, location, hp, strongVs, weakTo, immuneTo) {
    this.cid = cid;
    this.name = name;
    this.location = location;
    this.hp = hp;
    this.strongVs = strongVs;
    this.weakTo = weakTo;
    this.immuneTo = immuneTo;
  }
}
