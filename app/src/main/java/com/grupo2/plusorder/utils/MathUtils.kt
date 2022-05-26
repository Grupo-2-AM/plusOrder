package com.grupo2.plusorder.utils

object mathUtils {
    // Average of list<Int>
    fun GetAverageIntList(listInt: List<Int>) : Int?{
        var average: Int? = null

        if (listInt.count() > 0) {
            average = 0
            for (intIteration in listInt){
                average += intIteration
            }
        }

        return average
    }
}