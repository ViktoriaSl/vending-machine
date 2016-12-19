package vendingmachine.persistence.entities

import slick.driver.H2Driver.api._
import slick.lifted.ProvenShape

case class Coins(id: Long, number: Int) extends BaseEntity
case class DrinkInfo(id: Long, drinkName: String, price: Int,count: Int) extends BaseEntity

class ConsTable(tag: Tag) extends BaseTable[Coins](tag, "CONS_STORAGE") {
  def num: Rep[Int] = column[Int]("number")
  def * : ProvenShape[Coins] = (id, num) <> (Coins.tupled, Coins.unapply)
}

class DrinksTable(tag: Tag) extends BaseTable[DrinkInfo](tag, "DRINKS_STORAGE") {
  def drinkName: Rep[String] = column[String]("drink_name")
  def price: Rep[Int] = column[Int]("price")
  def count: Rep[Int] = column[Int]("count")
  def * : ProvenShape[DrinkInfo] = (id, drinkName, price, count) <> (DrinkInfo.tupled, DrinkInfo.unapply)
}