import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

@OptIn(ExperimentalTime::class)
object QController {

    var generatedCustomers=listOf<Customer>()

init {
setUpQController()
}





    private fun setUpQController() {
        print("Put in the number of Customers you would like to Generate <= 30: ")
        var amountofCustomer: Int =Integer.valueOf(readLine())
        print("Put in the range of arrival time 0 for Close Range 1 for Wild Range: ")
        var rangeOfarrivalTime: Int =Integer.valueOf(readLine())
        print("Put in the range of arrival time 0 for Close Range 1 for Wild Range: ")
        var rangeOfserviceTime: Int =Integer.valueOf(readLine())

        val avlTimefunction:()->Duration
        val serviceTimefunction:()->Duration

        when(rangeOfarrivalTime){
            0->avlTimefunction={ closeRangeArrivalTimeGenerator()}
            1->avlTimefunction={wildRangeArrivalTimeGenerator()}
            else->avlTimefunction={ wildRangeArrivalTimeGenerator()}

        }
        when(rangeOfarrivalTime){
            0->serviceTimefunction={ closeRangeServiceTimeGenerator() }
            1->serviceTimefunction={ wildRangeServiceTimeGenerator() }
            else->serviceTimefunction={ wildRangeServiceTimeGenerator() }

        }

        generateRandomCustomerEntitys(amountofCustomer,avlTimefunction,serviceTimefunction)
    }

    private fun generateage():Int{
        return Random.nextInt(from=10,until = 80)
    }
    private fun closeRangeArrivalTimeGenerator():Duration{
        val avlTimevalue= Random.nextDouble(from = 60000.00, until = 120000.00)

        return avlTimevalue.toDuration(DurationUnit.MILLISECONDS)

    }



    private fun wildRangeArrivalTimeGenerator():Duration{
        val avlTimevalue= Random.nextDouble(from = 60000.00, until = 1200000.00)
        return avlTimevalue.toDuration(DurationUnit.MILLISECONDS)

    }

    private fun closeRangeServiceTimeGenerator():Duration{
        val SevTimevalue= Random.nextDouble(from = 60000.00, until = 120000.00)
        return SevTimevalue.toDuration(DurationUnit.MILLISECONDS)

    }

    private fun wildRangeServiceTimeGenerator():Duration{
        val SevTimevalue= Random.nextDouble(from = 60000.00, until = 600000.00)
        return SevTimevalue.toDuration(DurationUnit.MILLISECONDS)

    }

    private fun getbooleanvalue():Boolean{
        var booleanresult:Boolean=false
       val num= Random.nextInt(from=0,until = 7)
        if (num<=1){
            booleanresult=true
        }
        return booleanresult
    }


    private fun generateRandomCustomerEntitys(amountofCustomer: Int,arrivalTimenumber:()->Duration,serviceTimenumber:()->Duration) {
        var tempGeneratedCustomers= mutableListOf<Customer>()
         for(i in 0 until amountofCustomer){
             val name=uniqueNameList.get(i)
             val age= generateage()
             val avlTimervalue=arrivalTimenumber()
             val svlTimervalue=serviceTimenumber()
             val emergency= getbooleanvalue()
             val currentsick= getbooleanvalue()

             val madeCustomer=Customer(name,age,currentsick,emergency,arrivalTime=avlTimervalue,serviceTime=svlTimervalue)
             tempGeneratedCustomers.add(madeCustomer)

         }
        tempGeneratedCustomers.forEach {
            if (it.age>50 || it.age<=10){
            it.priorityPoint=it.priorityPoint+1
            }
            if(it.currentlySick){
                it.priorityPoint=it.priorityPoint+1
            }
            if(it.emergency){
                it.priorityPoint=5
            }
        }

        generatedCustomers=tempGeneratedCustomers
        generatedCustomers.forEach { println(it) }
    }




    fun sortedCustomersBasedOnArrival(){
        println("This is a List for  ${generatedCustomers.size} Generated Customer  based on their Arrival Time")
        val timeAvl=compareBy<Customer> { it.arrivalTime }
        generatedCustomers=generatedCustomers.sortedWith(timeAvl)
        generatedCustomers.forEach { println(it) }
    }


    fun sortedCustomersBasedOnPriority(){
        println("This is a List for  ${generatedCustomers.size} Generated Customer based on Priority")
        val Priority=compareBy<Customer> { it.priorityPoint }
        generatedCustomers=generatedCustomers.sortedWith(Priority)
        generatedCustomers.forEach { println(it) }
    }

    fun getNextGroupBasedOnPredicate(currentTime:Duration):List<Customer>{
        //Could refactor to a function
        val ArrivalTime=compareBy<Customer> { it.arrivalTime }
        var groupTogoToQueue=generatedCustomers.filter { it.arrivalTime<=currentTime }
        generatedCustomers=generatedCustomers.filterNot { it.arrivalTime<=currentTime}
        /**
      var callist=groupTogoToQueue.groupBy { it.priorityPoint }.values.toList()

      var updatedQueue= mutableListOf<Customer>()
        println("{{{{{{{{{{{{{{{{{{{{{{{{{")
        callist.forEach { it.forEach { println(it) } }
        println("{{{{{{{{{{{{{{{{{{{{{{{{{")

        callist.forEach {it.sortedWith(ArrivalTime).forEach { updatedQueue.add(it)}}

        println("\n-------------This is the UpDataList---------------------")
        updatedQueue.forEach { println(it) }
        println("\n------------- UpDataList ENDED---------------------\n")**/

        return (groupTogoToQueue)

    }

    fun getNextCustomerBaseOnPredicate():Customer?{
        //Could refactor to a function
        val Priority=compareBy<Customer> { it.priorityPoint }
        val minTime= generatedCustomers.minOf { it.arrivalTime }
     val customer=generatedCustomers.filter { it.arrivalTime==minTime }.sortedWith(Priority).find { it.arrivalTime==minTime }
     generatedCustomers=generatedCustomers.filterNot{ (it.arrivalTime==customer?.arrivalTime) && (it.name == customer.name)}
        return (customer)

    }




}