package model

import connection.MySqlDBImpl
import spray.json.DefaultJsonProtocol

trait CatTable extends DefaultJsonProtocol {
  this: MySqlDBImpl =>
  import driver.api._

  implicit lazy val dogFormat = jsonFormat2(Cat)
  implicit lazy val dogListFormat = jsonFormat1(CatList)

  class CatTable(tag: Tag) extends Table[Cat](tag, "cat") {
    val id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    val name = column[String]("name")

    def * = (name, id.?) <>(Cat.tupled, Cat.unapply)
  }
}

case class Cat(name: String, id: Option[Int] = None)
case class CatList(cats: List[Cat])
