package vendingmachine.model

import vendingmachine.persistence.entities.Coins

case class Drink(drink: String, change: Coins)
