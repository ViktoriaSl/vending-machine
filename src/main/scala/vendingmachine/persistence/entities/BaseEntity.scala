package vendingmachine.persistence.entities

trait BaseEntity {
  val id : Long
  def isValid : Boolean = true
}