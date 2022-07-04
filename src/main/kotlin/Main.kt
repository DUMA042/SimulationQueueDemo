import kotlin.time.ExperimentalTime

//User input options  statement for  the Simulation
fun printOption(){
    print("Press\n (1) to Add Service a Customer\n(2) Print the Current Customer been Serviced\n(3) Print the State of the Queue\n(4) Print all Serviced Customers\n(0) Exit  ")
}


@OptIn(ExperimentalTime::class)
fun main(args: Array<String>) {
    //Singleton Class for the Queue Simulation
    QController

println("\n--------------------------Queuing Center Started------------------------------\n")
val center=Center()
var option:Int=0
   do {
       printOption()
       //To take the user option
        option =Integer.valueOf(readLine())

       //HAving a while loop for the different options of that the user may enter
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



}