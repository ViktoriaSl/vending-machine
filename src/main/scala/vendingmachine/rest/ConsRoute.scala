package vendingmachine.rest

import scala.concurrent.Future
import scala.util.{Failure, Success}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes.InternalServerError
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import spray.json._
import com.typesafe.scalalogging.LazyLogging
import vendingmachine.model.responces.ResponseStatus
import vendingmachine.persistence.entities.Coins
import vendingmachine.persistence.entities.JsonProtocol._

trait ConsRoute extends LazyLogging{
  def jsonHeaders(block: Coins => Route): Route = post {
    entity(as[Coins]) { data =>
      block(data)
    }
  }
  
  def completePostConsAnswer(res: => Future[Coins]): Route = onComplete(res) {
    case Success(insertedEntities) =>
      logger.debug("Machine got "+insertedEntities.number+ " coins")
      val created = ResponseStatus(200, insertedEntities.toJson.toString())
      complete(created)
    case Failure(ex) =>
      logger.debug("Getting coins failed",ex)
      complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
  }
}
