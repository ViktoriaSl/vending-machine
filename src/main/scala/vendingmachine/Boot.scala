package vendingmachine

import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import vendingmachine.persistence.entities.DrinkInfo
import vendingmachine.rest.Routes
import vendingmachine.service.VendingMachineService
import vendingmachine.utils.{ActorModuleImpl, ConfigurationModuleImpl, PersistenceModuleImpl}

object Boot extends App {
  // configuring modules for application, cake pattern for DI
  val modules = new ConfigurationModuleImpl with ActorModuleImpl with PersistenceModuleImpl
  implicit val system = modules.system
  implicit val materializer = ActorMaterializer()
  implicit val ec = modules.system.dispatcher
  
  modules.coinsDal.createTable()
  modules.drinksDal.createTable()
  modules.drinksDal.insert(DrinkInfo(1, "Black Tea", 10, 6))
  modules.drinksDal.insert(DrinkInfo(2, "Chocolate", 10, 6))
  modules.drinksDal.insert(DrinkInfo(3, "Espresso", 12, 6))
  
  val vendingMachine = new VendingMachineService(modules)
  val bindingFuture = Http().bindAndHandle(new Routes(vendingMachine).routes, "localhost", 8080)
  
  println(s"Server online at http://localhost:8080/")
}
