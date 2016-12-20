package vendingmachine.service

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import vendingmachine.model.{Drink, MachineOperationResult, NotEnoughMoney}
import vendingmachine.persistence.entities.{Coins, DrinkInfo}
import vendingmachine.utils.Constants.coinsId
import vendingmachine.utils.{Configuration, PersistenceModule}

class VendingMachineService(modules: Configuration with PersistenceModule) {
  
  def addCoins(coins: Coins): Future[Coins] = {
    val savedCoins = modules.coinsDal.findById(coinsId)
    savedCoins.map { c =>
      if (c.isEmpty) {
        modules.coinsDal.insert(Coins(coinsId, coins.number))
        coins
      }
      else {
        val updatedBalance = c.get.copy(number = c.get.number + coins.number)
        modules.coinsDal.update(updatedBalance).map(_.toLong)
        updatedBalance
      }
    }
  }
  def getDrink(drinkId: Int): Future[Either[Drink, MachineOperationResult]] =
    modules.coinsDal.findById(coinsId).flatMap { payment =>
      if (payment.isEmpty)
        Future {Right(NotEnoughMoney)}
      else {
        val drink = modules.drinksDal.findById(drinkId).mapTo[Option[DrinkInfo]]
        drink.map {
          _.fold[Either[Drink, MachineOperationResult]](Right(NotEnoughMoney)) {canAfford(payment)}
        }
      }
    }
  private def canAfford(payment: Option[Coins])(drink: DrinkInfo): Either[Drink, MachineOperationResult] = {
    val isEfford = drink.price <= payment.get.number
    if (isEfford) {
      val change = payment.get.copy(number = payment.get.number - drink.price)
      modules.coinsDal.update(payment.get.copy(number = 0))
      Left(Drink(drink.drinkName, change))
    }
    else
      Right(NotEnoughMoney)
  }
}
