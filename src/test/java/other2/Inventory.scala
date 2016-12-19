package other2
//
//class Inventory[A] (inventory :Map[A,Int]){
//
//   def quantity(item: A): Int ={
//     val value = inventory.get(item)
//     if(value.isEmpty) 0 else value.get
//   }
//
//   def add(item: A): Int = {
//     val count = inventory.get(item)
//     inventory(item -> count.map(_+1))
//   }
//
//   def hasItem(item: A):Boolean = {quantity(item)>0}
//
//   def deduct(item: A) = {
//
//   }
//
//   def clear = inventory
////   public int getQuantity(T item){ Integer value = inventory.get(item); return value == null? 0 : value ; }
////   public void add(T item){ int count = inventory.get(item); inventory.put(item, count+1); }
////   public void deduct(T item) { if (hasItem(item)) { int count = inventory.get(item); inventory.put(item, count - 1); } }
////   public boolean hasItem(T item){ return getQuantity(item) > 0; }
//// public void clear(){ inventory.clear(); }
////   public void put(T item, int quantity) { inventory.put(item, quantity); }
//
//
// }
