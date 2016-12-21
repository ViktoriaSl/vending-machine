package vendingmachine.rest

import scala.concurrent.Future
import scala.util.{Failure, Success}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives.{complete, onComplete}
import akka.http.scaladsl.server.Route
import com.typesafe.scalalogging._
import vendingmachine.model.responces.ResponseStatus
import vendingmachine.model.{Drink, MachineOperationResult}
import vendingmachine.persistence.entities.JsonProtocol._

trait DrinksRoute extends LazyLogging{
  def completeGetDrinkAnswer(res: => Future[Either[Drink, MachineOperationResult]]): Route = onComplete(res) {
    case Success(drink) => drink match {
      case Left(d) =>
        logger.debug("Drink was sold successfully: "+ d.drink)
        complete(d)
      case Right(r) =>
        logger.debug("Machine can't give you drink " + r)
        complete(ResponseStatus(404, s"Machine can't give you drink: " + r))
    }
    case Failure(ex) =>
      logger.debug(s"An error occurred: ${ex.getMessage}")
      complete(ResponseStatus(500, s"An error occurred: ${ex.getMessage}"))
  }
}
