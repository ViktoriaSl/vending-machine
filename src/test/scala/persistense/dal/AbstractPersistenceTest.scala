package persistense.dal

import org.scalatest.{AsyncFlatSpec, Suite}
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile
import slick.lifted.TableQuery
import vendingmachine.persistence.dal.BaseDalImpl
import vendingmachine.persistence.entities.{Coins, ConsTable, DrinkInfo, DrinksTable}
import vendingmachine.utils.{Configuration, ConfigurationModuleImpl, DbModule, PersistenceModule}

trait AbstractPersistenceTest extends AsyncFlatSpec {
  this: Suite =>
  
  trait Modules extends ConfigurationModuleImpl with PersistenceModuleTest {
  }
  
  trait PersistenceModuleTest extends PersistenceModule with DbModule {
    this: Configuration =>
    private val dbConfig: DatabaseConfig[JdbcProfile] = DatabaseConfig.forConfig("h2test")
    override implicit val profile: JdbcProfile = dbConfig.driver
    override implicit val db: JdbcProfile#Backend#Database = dbConfig.db
    override val coinsDal = new BaseDalImpl[ConsTable, Coins](TableQuery[ConsTable]) {}
    override val drinksDal = new BaseDalImpl[DrinksTable, DrinkInfo](TableQuery[DrinksTable]) {}
    val self = this
    
  }
  
}