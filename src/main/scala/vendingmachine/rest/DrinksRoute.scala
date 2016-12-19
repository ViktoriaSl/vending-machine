package vendingmachine.rest

import scala.concurrent.Future
import scala.util.{Failure, Success}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes.{InternalServerError, NotFound}
import akka.http.scaladsl.server.Directives.{complete, onComplete}
import akka.http.scaladsl.server.Route
import vendingmachine.persistence.entities.{Coins, DrinkInfo}
import vendingmachine.persistence.entities.JsonProtocol._
import akka.http.scaladsl.model.StatusCodes.{Created, InternalServerError, NotFound}
import SprayJsonSupport._
import vendingmachine.model.{Drink, MachineOperationResult}

trait DrinksRoute {
  def completeGetDrinkAnswer(res: => Future[Either[Drink,MachineOperationResult]]): Route = onComplete(res) {
    case Success(supplierOpt) => supplierOpt match {
      case Left(sup) => complete(sup)
      case Right(r) => complete(NotFound, s"Machine can't give you drink: "+r)
    }
    case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
  }
}
