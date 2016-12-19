package vendingmachine.model

import vendingmachine.persistence.entities.Coins

sealed trait MachineOperationResult
case object NoDrinks extends MachineOperationResult
case object NotEnoughMoney extends MachineOperationResult
case class Drink(drink:String,change: Coins)
