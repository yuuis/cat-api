package http

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.MethodDirectives
import dao.CatDao
import model.Cat

import scala.concurrent.ExecutionContextExecutor

trait CatRoutes extends SprayJsonSupport {
  this: CatDao =>
  implicit val dispatcher: ExecutionContextExecutor

  val routes = pathPrefix("cats") {
    pathEnd {
      get {
        complete(getAll)
      } ~ post {
        entity(as[Cat]) { cat =>
          complete {
            create(cat).map{ result => HttpResponse(entity = "dog has been saved successfully.")}
          }
        }
      }
    } ~
      path(IntNumber) { id =>
        get {
          complete(getById(id))
        } ~ put {
          entity(as[Cat]) { cat =>
            complete{
              val newCat = Cat(cat.name, Option(id))
              update(newCat).map{ result => HttpResponse(entity = "dog has been updated successfully.")}
            }
          }
        } ~ MethodDirectives.delete {
          complete {
            delete(id).map{ result => HttpResponse(entity = "dog has been deleted successfully.")}
          }
        }
      }
  }
}
