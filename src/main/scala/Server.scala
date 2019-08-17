import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import dao.CatDao
import model.Cat
import http.CatRoutes

import scala.io.StdIn
import scala.concurrent.ExecutionContextExecutor

object Server extends App with CatRoutes with CatDao {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val dispatcher: ExecutionContextExecutor = system.dispatcher

  ddl.onComplete { _ =>
    // seeding
    create(Cat("Bailey"))
    create(Cat("Max"))
    create(Cat("Charlie"))
    create(Cat("Bella"))
    create(Cat("Lucy"))
    create(Cat("Molly"))

    val bindingFuture = Http().bindAndHandle(routes, "localhost", 8080)

    println(s"server online at http://localhost:8080/\npress return to stop.")

    StdIn.readLine()
    bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())
  }
}
