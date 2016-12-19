package vendingmachine.service

import scala.concurrent.{ExecutionContext, Future}
import vendingmachine.persistence.entities.{Coins, DrinkInfo}
import vendingmachine.utils.{Configuration, PersistenceModule}
import scala.concurrent.ExecutionContext.Implicits.global
import vendingmachine.model.{Drink, MachineOperationResult, NotEnoughMoney}

class VendingMachineService(modules: Configuration with PersistenceModule)  {
  
  def addCoins(coins: Coins): Future[Coins] =  {
    //todo ???? replace 1
    val savedCoins = modules.coinsDal.findById(1)
    savedCoins.map { c =>
      if(c.isEmpty) {
        modules.coinsDal.insert(Coins(0, coins.number))
        coins
      }
      else{
        val updatedBalance = c.get.copy(number = c.get.number+coins.number)
        modules.coinsDal.update(updatedBalance).map(_.toLong)
        updatedBalance
    }
    }
  }
  def getDrink(drinkId: Int): Future[Either[Drink,MachineOperationResult]] = {
    modules.coinsDal.findById(1).flatMap{payment =>
      if(payment.isEmpty)
        Future{ Right(NotEnoughMoney) } //todo no money error
        else{
   val drink = modules.drinksDal.findById(drinkId).mapTo[Option[DrinkInfo]]
        drink.map{d =>
          if(d.isDefined){
            val drink = d.get
          val isEfford = drink.price<=payment.get.number
            if(isEfford){
            val change =  payment.get.copy(number = payment.get.number-drink.price)
              modules.coinsDal.update(payment.get.copy(0)) //make change 0
              Left(Drink(drink.drinkName,change))
            }
            else
              Right(NotEnoughMoney)
          }
          else
           Right(NotEnoughMoney) //todo no drink in machine
        }
    }
    }
  }
}
