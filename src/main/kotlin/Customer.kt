import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**For Fields  of the Customer Class*/
@ExperimentalTime
data class Customer(val name:String,
                    val age:Int,
                    val currentlySick:Boolean=false,
                    var emergency:Boolean=false,
                    var priorityPoint:Int=0,
                    var arrivalTime:Duration= Duration.Companion.milliseconds(-1.0),
                    var waitTime:Duration= Duration.Companion.milliseconds(-1.0),
                    var serviceTime:Duration= Duration.Companion.milliseconds(-1.0),
                    var doneTime:Duration= Duration.Companion.milliseconds(-1.0)
)
