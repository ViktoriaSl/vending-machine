package vendingmachine.rest

import scala.concurrent.Future
import scala.util.{Failure, Success}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.MediaTypes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import vendingmachine.persistence.entities.{Coins, DrinkInfo}
import vendingmachine.persistence.entities.JsonProtocol._
import SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes._
import org.slf4j.Logger
import spray.json._
import vendingmachine.persistence.entities.JsonProtocol._
import akka.http.scaladsl.model.StatusCodes.{ InternalServerError, NotFound}
import SprayJsonSupport._
import vendingmachine.model.responces.Created


trait ConsRoute {
  def jsonHeaders(block: Coins => Route): Route = post {
    entity(as[Coins]) { data =>
      block(data)
    }
  }
  
  def completePostConsAnswer(res: => Future[Coins]): Route = onComplete(res) {
    case Success(insertedEntities) =>
      println("Created successfully "+ insertedEntities)
      val created = Created(200,insertedEntities.toJson.toString())
      complete(created)
    case Failure(ex) => complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
  }
}
