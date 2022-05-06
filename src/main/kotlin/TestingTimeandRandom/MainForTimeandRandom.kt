package TestingTimeandRandom

import java.lang.Math.random
import java.sql.Time
import kotlin.random.Random
import kotlin.time.*


@OptIn(ExperimentalTime::class)
fun main(args: Array<String>) {
    val words = listOf("a", "abc", "ab", "def", "abcd")
    val jj=words.sortedDescending()
    println(jj)
    val byLength = words.groupBy { it.length }



    println(byLength.keys) // [1, 3, 2, 4]
    println(byLength.values) // [[a], [abc, def], [ab], [abcd]]
    val changetolist=byLength.values.toList()
    val nnn= mutableListOf<String>()
   val newlist= changetolist.forEach {it.sortedDescending().forEach {nnn.add(it)  } }
    println(changetolist)
    println(nnn)


    print("Enter text: ")

    val stringInput = readLine()!!
    println("You entered: $stringInput")

    val fixIt=fix("a",11,10)
    val fixIt1=fix("b",5,7)
    val fixIt2=fix("c",12,5)
    val fixIt3=fix("d",10,2)
    val fixIt4=fix("a",0,1)

    val fixlist=listOf(fixIt,fixIt1,fixIt2)
    val timeavl=compareBy<fix> { it.pp }
    val prioritypoint = compareBy<fix> { it.point }

    println(fixlist.sortedWith((prioritypoint)))


    val nn:Duration =Duration.milliseconds(1000.0)
    val bb:Duration =Duration.milliseconds(1000.0)
    for(i in 1..10){
        val secondAngle = Random.nextDouble(from = 60000.0, until = 1200000.23)

        println(secondAngle)
    }

    println("The total time is ${(bb+nn)/2.0}")

    println(nn.toDouble(DurationUnit.MILLISECONDS))
    println(nn)
     //val tim=Timetester(12.7)
}