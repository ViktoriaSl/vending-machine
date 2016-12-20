package vendingmachine.utils

import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile
import slick.lifted.TableQuery
import vendingmachine.persistence.dal.{BaseDal, BaseDalImpl}
import vendingmachine.persistence.entities.{Coins, ConsTable, DrinkInfo, DrinksTable}

trait Profile {
  val profile: JdbcProfile
}

trait DbModule extends Profile {
  val db: JdbcProfile#Backend#Database
}

trait PersistenceModule {
  val coinsDal: BaseDal[ConsTable, Coins]
  val drinksDal: BaseDal[DrinksTable, DrinkInfo]
}

trait PersistenceModuleImpl extends PersistenceModule with DbModule {
  this: Configuration =>
  override val coinsDal = new BaseDalImpl[ConsTable, Coins](TableQuery[ConsTable]) {}
  
  override implicit val profile: JdbcProfile = dbConfig.driver
  override implicit val db: JdbcProfile#Backend#Database = dbConfig.db
  override val drinksDal = new BaseDalImpl[DrinksTable, DrinkInfo](TableQuery[DrinksTable]) {}
  val self: PersistenceModuleImpl with Configuration = this
  private val dbConfig: DatabaseConfig[JdbcProfile] = DatabaseConfig.forConfig("h2db")
}


