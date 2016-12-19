package other

class Test {
//  import akka.actor._
//
//  import akka.actor.SupervisorStrategy._
//
//  import scala.concurrent.duration._
//
//
//
//// Coffee types
//
//  trait CoffeeType
//
//  case object BlackCoffee extends CoffeeType
//
//  case object Latte extends CoffeeType
//
//  case object Espresso extends CoffeeType
//
//
//
//// Commands
//
//  case class Coins(number: Int)
//
//  case class Selection(coffee: CoffeeType)
//
//  case object TriggerOutOfCoffeeBeansFailure
//
//
//
//// Replies
//
//  case class Beverage(coffee: CoffeeType)
//
//
//
//// Errors
//
//  case class NotEnoughCoinsError(message: String)
//
//
//
//// Failures
//
//  case class OutOfCoffeeBeansFailure(customer: ActorRef,
//
//    pendingOrder: Selection,
//
//    nrOfInsertedCoins: Int) extends Exception
//
//
//
//  class CoffeeMachineManager extends Actor {
//
//    override val supervisorStrategy =
//
//      OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1.minute) {
//
//        case e: OutOfCoffeeBeansFailure =>
//
//          println(s"ServiceGuy notified: $e")
//
//          Restart
//
//        case _: Exception =>
//
//          Escalate
//
//      }
//
//
//
//    // to simplify things he is only managing 1 single machine
//
//    val machine = context.actorOf(Props[CoffeeMachine], name = "coffeeMachine")
//
//
//
//    def receive = {
//
//      case request => machine.forward(request)
//
//    }
//
//  }
//
//
//
//  class CoffeeMachine extends Actor {
//
//    val price = 2
//
//    var nrOfInsertedCoins = 0
//
//    var outOfCoffeeBeans = false
//
//    var totalNrOfCoins = 0
//
//
//
//    def receive = {
//
//      case Coins(nr) =>
//
//        nrOfInsertedCoins += nr
//
//        totalNrOfCoins += nr
//
//        println(s"Inserted [$nr] coins")
//
//        println(s"Total number of coins in machine is [$totalNrOfCoins]")
//
//
//
//      case selection @ Selection(coffeeType) =>
//
//        if (nrOfInsertedCoins < price)
//
//          sender.tell(NotEnoughCoinsError(s"Please insert [${price - nrOfInsertedCoins}] coins"), self)
//
//        else {
//
//          if (outOfCoffeeBeans)
//
//            throw new OutOfCoffeeBeansFailure(sender, selection, nrOfInsertedCoins)
//
//          println(s"Brewing your $coffeeType")
//
//          sender.tell(Beverage(coffeeType), self)
//
//          nrOfInsertedCoins = 0
//
//        }
//
//
//
//      case TriggerOutOfCoffeeBeansFailure =>
//
//        outOfCoffeeBeans = true
//
//    }
//
//
//
//    override def postRestart(failure: Throwable): Unit = {
//
//      println(s"Restarting coffee machine...")
//
//      failure match {
//
//        case OutOfCoffeeBeansFailure(customer, pendingOrder, coins) =>
//
//          nrOfInsertedCoins = coins
//
//          outOfCoffeeBeans = false
//
//          println(s"Resubmitting pending order $pendingOrder")
//
//          context.self.tell(pendingOrder, customer) // fake the sender to be the customer®
//
//      }
//
//    }
//
//  }
//
//
//
//  object VendingMachineDemo extends App {
//
//
//
//    val system = ActorSystem("vendingMachineDemo")
//
//    val coffeeMachine = system.actorOf(Props[CoffeeMachineManager], "coffeeMachineManager")
//
//    val customer = Inbox.create(system) // emulates the customer
//
//
//
//    println("-----------------------------------------")
//
//    // Insert 2 coins and get an Espresso
//
//    customer.send(coffeeMachine, Coins(2))
//
//    customer.send(coffeeMachine, Selection(Espresso))
//
//    val Beverage(coffee1) = customer.receive(5.seconds)
//
//    println(s"Got myself an $coffee1")
//
//    assert(coffee1 == Espresso)
//
//
//
//    println("-----------------------------------------")
//
//    // Insert 1 coin and fail to get a Latte
//
//    customer.send(coffeeMachine, Coins(1))
//
//    customer.send(coffeeMachine, Selection(Latte))
//
//    val NotEnoughCoinsError(message) = customer.receive(5.seconds)
//
//    println(s"Got myself a validation error: $message")
//
//    assert(message == "Please insert [1] coins")
//
//
//
//    println("-----------------------------------------")
//
//    // Insert 1 coin (had 1 before) and try to get my Latte
//
//    // Machine should:
//
//    //   1. Fail
//
//    //   2. Restart
//
//    //   3. Resubmit my order
//
//    //   4. Give me my coffee
//
//    customer.send(coffeeMachine, Coins(1))
//
//    customer.send(coffeeMachine, TriggerOutOfCoffeeBeansFailure)
//
//    customer.send(coffeeMachine, Selection(Latte))
//
//    val Beverage(coffee2) = customer.receive(5.seconds)
//
//    println(s"Got myself a $coffee2")
//
//    assert(coffee2 == Latte)
//
//
//
//    system.shutdown()
//
//  }
}
