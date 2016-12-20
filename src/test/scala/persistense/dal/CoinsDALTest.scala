package persistense.dal

import scala.concurrent.duration._
import akka.util.Timeout
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{AsyncFlatSpec, BeforeAndAfterAll}
import vendingmachine.persistence.entities.Coins

@RunWith(classOf[JUnitRunner])
class CoinsDALTest extends AsyncFlatSpec with AbstractPersistenceTest with BeforeAndAfterAll {
  implicit val timeout = Timeout(5.seconds)
  
  val coins = Coins(1, 5)
  val modules = new Modules {
  }
  
  "coins" should "be created" in {
    modules.coinsDal.createTable().flatMap { c =>
      modules.coinsDal.insert(coins).map(c => assert(c == 1))
      modules.coinsDal.findById(1).map(c => assert(c contains coins))
    }
  }
  
  override def afterAll: Unit = {
    modules.db.close()
  }
}