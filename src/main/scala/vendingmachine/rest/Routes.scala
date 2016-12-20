package vendingmachine.rest

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import vendingmachine.service.VendingMachineService

class Routes(service: VendingMachineService) extends ConsRoute with DrinksRoute {
  
  val routes: Route = pathPrefix("drinkmachine") {consPostRoute ~ drinkGetRoute}
  private val consPostRoute = path("cons") {
    jsonHeaders { coins =>
      completePostConsAnswer(
        service.addCoins(coins)
      )
    }
  }
  private val drinkGetRoute = path("drink" / IntNumber) { (drinkId) =>
    get {
      completeGetDrinkAnswer {
        service.getDrink(drinkId)
      }
    }
  }
  
}
