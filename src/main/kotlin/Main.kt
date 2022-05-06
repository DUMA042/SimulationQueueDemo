import kotlin.time.ExperimentalTime


fun printOption(){
    print("Press\n (1) to Add Service a Customer\n(2) Print the Current Customer been Serviced\n(3) Print the State of the Queue\n(4) Print all Serviced Customers\n(0) Exit  ")
}


@OptIn(ExperimentalTime::class)
fun main(args: Array<String>) {
    QController

println("\n--------------------------Queuing Center Started------------------------------\n")
val center=Center()
var option:Int=0
   do {
       printOption()
        option =Integer.valueOf(readLine())

       when(option){
           1->center.addCurrentCustomer()
           2->center.printCurrentCustomerDetails()
           3->center.printCustomersInQueue()
           4->center.printListOfCustomersServed()
           else-> println("${repeat(15){ print("-")} }\nProgram Q Simulator has Ended")
       }
       println("\n---------------------------------\n")
       QController.sortedCustomersBasedOnArrival()


   } while (option!=0)


    // Try adding program arguments at Run/Debug configuration
    /**
val cu=Customer("we",12)
val c=Center()
c.currentCustomer=cu
c.addToServedList(cu)
println(c.printListOfCustomersServed())
**/


   // println(cu)

}