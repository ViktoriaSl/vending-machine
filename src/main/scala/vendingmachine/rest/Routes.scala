package vendingmachine.rest

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}
import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes.{Created, InternalServerError}
import akka.stream.Materializer
import vendingmachine.utils.{Configuration, PersistenceModule}
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import vendingmachine.persistence.entities.{Coins, DrinkInfo}
import vendingmachine.service.VendingMachineService

class Routes(service: VendingMachineService) extends ConsRoute with DrinksRoute{
  
  private val consPostRoute = path("cons") {jsonHeaders { coins =>
    completePostConsAnswer(
      service.addCoins(coins)
      )
    }
  }
  
  private val drinkGetRoute = path("drink" / IntNumber) { (drinkId) =>
    get {
      completeGetDrinkAnswer{
        service.getDrink(drinkId)
      }
    }
  }
  
  val routes: Route =   pathPrefix("drinkmachine") { consPostRoute ~ drinkGetRoute}

}
