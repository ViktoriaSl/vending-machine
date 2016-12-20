package vendingmachine.rest

import scala.concurrent.Future
import scala.util.{Failure, Success}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives.{complete, onComplete}
import akka.http.scaladsl.server.Route
import vendingmachine.model.responces.ResponseStatus
import vendingmachine.model.{Drink, MachineOperationResult}
import vendingmachine.persistence.entities.JsonProtocol._

trait DrinksRoute {
  def completeGetDrinkAnswer(res: => Future[Either[Drink, MachineOperationResult]]): Route = onComplete(res) {
    case Success(supplierOpt) => supplierOpt match {
      case Left(sup) => complete(sup)
      case Right(r) => complete(ResponseStatus(404, s"Machine can't give you drink: " + r))
    }
    case Failure(ex) => complete(ResponseStatus(500, s"An error occurred: ${ex.getMessage}"))
  }
}
