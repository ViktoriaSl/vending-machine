package vendingmachine.persistence.entities

import spray.json.DefaultJsonProtocol
import vendingmachine.model.Drink
import vendingmachine.model.responces.ResponseStatus

object JsonProtocol extends DefaultJsonProtocol {
  implicit val supplierFormat = jsonFormat2(Coins)
  implicit val simpleSupplierFormat = jsonFormat4(DrinkInfo)
  implicit val createdFormat = jsonFormat2(ResponseStatus)
  implicit val drinkFormat = jsonFormat2(Drink)
}