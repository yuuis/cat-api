package dao

import connection.MySqlDBImpl
import model.{Cat, CatTable}

import scala.concurrent.Future

trait CatDao extends CatTable with MySqlDBImpl {
  import driver.api._

  protected val catTableQuery = TableQuery[CatTable]

  def create(cat: Cat): Future[Int] = db.run {
    catTableAutoInc += cat
  }

  def update(cat: Cat): Future[Int] = db.run {
    catTableQuery.filter(_.id === cat.id.get).update(cat)
  }

  def getById(id: Int): Future[Option[Cat]] = db.run {
    catTableQuery.filter(_.id === id).result.headOption
  }

  def getAll: Future[List[Cat]] = db.run {
    catTableQuery.to[List].result
  }

  def delete(id: Int): Future[Int] = db.run {
    catTableQuery.filter(_.id === id).delete
  }

  def ddl = db.run {
    catTableQuery.schema.create
  }

  protected def catTableAutoInc = catTableQuery returning catTableQuery.map(_.id)
}
