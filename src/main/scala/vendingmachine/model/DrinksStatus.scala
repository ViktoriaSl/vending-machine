package vendingmachine.model

sealed trait MachineOperationResult
case object NoDrinks extends MachineOperationResult
case object NotEnoughMoney extends MachineOperationResult

