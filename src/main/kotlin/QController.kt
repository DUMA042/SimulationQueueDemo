import kotlin.math.nextDown
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

@OptIn(ExperimentalTime::class)
object QController {
//To hold the list of classes
    var generatedCustomers=listOf<Customer>()

init {
    //To set up the Queue
setUpQController()
}




/** Having the Function to Set up the range**/
    private fun setUpQController() {
    print("Put in the Arrival rate you want should be <=30: ")
        var amountofCustomer: Int =Integer.valueOf(readLine())
        print("Put in the range of random values 0 for Close Range 1 for Wild Range: ")
        var rangeOfarrivalTime: Int =Integer.valueOf(readLine())
        print("Put in the range of random values 0 for Close Range 1 for Wild Range: ")
        var rangeOfserviceTime: Int =Integer.valueOf(readLine())



        val avlTimefunction:()->Duration
        val serviceTimefunction:()->Duration

        when(rangeOfarrivalTime){
            0->avlTimefunction={ closeRangeArrivalTimeGenerator()}
            1->avlTimefunction={wildRangeArrivalTimeGenerator()}
            else->avlTimefunction={ wildRangeArrivalTimeGenerator()}

        }
        when(rangeOfserviceTime){
            0->serviceTimefunction={ closeRangeServiceTimeGenerator() }
            1->serviceTimefunction={ wildRangeServiceTimeGenerator() }
            else->serviceTimefunction={ wildRangeServiceTimeGenerator() }

        }

        generateRandomCustomerEntitys(amountofCustomer,avlTimefunction,serviceTimefunction)
    }

    private fun generateage():Int{
        return Random.nextInt(from=10,until = 80)
    }
    /**Getting the arrival rate time based on a Poisson distribution  **/

    private fun closeRangeArrivalTimeGenerator(lambda:Double=4.0):Duration{
        val randomavlTimevalue= Random.nextDouble(from = 0.55555555, until = 0.666778)
        var x=Math.log(1-randomavlTimevalue.nextDown())/(-lambda)
        return x.toDuration(DurationUnit.HOURS)

    }






    private fun wildRangeArrivalTimeGenerator(lambda:Double=10.0):Duration{
        val randomavlTimevalue= Random.nextDouble(from = 0.5555555, until = 0.656778)
        var x=Math.log(1-randomavlTimevalue.nextDown())/(-lambda)
        return x.toDuration(DurationUnit.HOURS)

    }

    private fun closeRangeServiceTimeGenerator(mu:Double=8.0):Duration{
        val SevTimevalue= Random.nextDouble(from = 0.5555555, until = 0.656778)
        var x=Math.log(1-SevTimevalue.nextDown())/(1/-mu)
        return x.toDuration(DurationUnit.MINUTES)

    }



    private fun wildRangeServiceTimeGenerator(mu:Double=5.0):Duration{
        val SevTimevalue= Random.nextDouble(from = 0.55, until = 0.7899)
        var x=Math.log(1-SevTimevalue.nextDown())/(1/-mu)
        return x.toDuration(DurationUnit.MINUTES)

    }

    private fun getbooleanvalue():Boolean{
        var booleanresult:Boolean=false
       val num= Random.nextInt(from=0,until = 7)
        if (num<=1){
            booleanresult=true
        }
        return booleanresult
    }

/**Generating the Customer Entity of the Customer for the Simulation**/
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
            it.priorityPoint=0
            }
            if(it.currentlySick){
                it.priorityPoint=0
            }
            if(it.emergency){
                it.priorityPoint=0
            }
        }

        generatedCustomers=tempGeneratedCustomers
        generatedCustomers.forEach { println(it) }
    }



/**Sort the gentrated list based on Arrival time**/
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

    /**Sort the  Next group to be simulated  based on their arrival time,and current time*/
    fun getNextGroupBasedOnPredicate(currentTime:Duration):List<Customer>{
        //Could refactor to a function
        val ArrivalTime=compareBy<Customer> { it.arrivalTime }
        var groupTogoToQueue=generatedCustomers.filter { it.arrivalTime<=currentTime }
        generatedCustomers=generatedCustomers.filterNot { it.arrivalTime<=currentTime}


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