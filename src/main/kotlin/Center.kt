import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class Center {

    var currentCustomer:Customer?=null;
    var serviceTime=Duration.Companion.milliseconds(0.0)
    var totalServiceTime=Duration.Companion.milliseconds(0.0)


    //Singleton object




    private val listOfCustomersServiced:MutableList<Customer> = mutableListOf()

    private var listOfCustomersInQueue:MutableList<Customer> = mutableListOf()

    private  fun addToServedList(served:Customer?){
        served?.let {   listOfCustomersServiced.add(it) }

    }

  private  fun addToQueue(){
        QController.getNextGroupBasedOnPredicate(serviceTime).forEach { listOfCustomersInQueue.add(it) }


      //listOfCustomersInQueue=listOfCustomersInQueue.let { listOfCustomersInQueue.sortWith(Priority) as MutableList<Customer> }

    }


   private fun changeCurrentCustomer(){
       var newmax=listOfCustomersInQueue.maxOf { it.priorityPoint }
       var neworder=listOfCustomersInQueue.filter { it.priorityPoint==newmax }.sortedBy { it.arrivalTime }
       currentCustomer=neworder.first()
       listOfCustomersInQueue= listOfCustomersInQueue.filterNot { (it.name.equals(currentCustomer!!.name)) } as MutableList<Customer>

    }

   private fun changeCurrentCustomerOnIdle(){
        currentCustomer=QController.getNextCustomerBaseOnPredicate()
        serviceTime=currentCustomer!!.arrivalTime

    }

    fun addCurrentCustomer(){
        addToServedList(currentCustomer)
        addToQueue()
       if (listOfCustomersInQueue.size==0){
           changeCurrentCustomerOnIdle()

       }
        else{

           changeCurrentCustomer()


       }


        currentCustomer!!.waitTime=serviceTime-currentCustomer!!.arrivalTime

        serviceTime=serviceTime+currentCustomer!!.serviceTime

        currentCustomer!!.doneTime=serviceTime-currentCustomer!!.arrivalTime

        totalServiceTime=totalServiceTime+serviceTime
    }



    fun printCurrentCustomerDetails(){
currentCustomer?.let { println(it) }

    }

    fun printCustomersInQueue(){
        listOfCustomersInQueue.forEach { println(it) }
    }

    fun printListOfCustomersServed(){
        listOfCustomersServiced.forEach { println(it) }
    }




    fun getmaxWaitTime(): Duration? {
     val maxWaitTime=listOfCustomersServiced.maxOfOrNull {it.waitTime?:Duration.Companion.minutes(5.0) }
     println(listOfCustomersServiced.filter { it.waitTime==maxWaitTime })
        return maxWaitTime
    }


    fun getAvgWaitTime():Duration{

        var TotalTime=Duration.Companion.milliseconds(0.0)
        listOfCustomersServiced.forEach { TotalTime=
            it.waitTime?.plus(TotalTime) ?:Duration.Companion.milliseconds(0.0)
        }
       return (TotalTime/listOfCustomersServiced.size)
    }

    fun getMinWaitTime():Duration?{
        val minWaitTime=listOfCustomersServiced.minOfOrNull {it.waitTime?:Duration.Companion.minutes(5.0)  }
        println(listOfCustomersServiced.filter { it.waitTime==minWaitTime })
        return minWaitTime
    }





}
/** val( name,
age,
currentlySick,
emergency,
priorityPoint,
arrivalTime,
waitTime,
serviceTime,
doneTime)=currentCustomer!!**/