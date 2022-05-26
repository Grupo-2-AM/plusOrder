package com.grupo2.plusorder.backend.statistics

import android.text.method.BaseKeyListener
import androidx.core.math.MathUtils
import com.grupo2.plusorder.backend.tables.BackendConta
import com.grupo2.plusorder.utils.mathUtils
import com.grupo2.plusorder.utils.mathUtils.GetAverageIntList

object ContaStatistics {
    // Average age
    fun AverageAge() : Int? {
        val contas = arrayListOf<Int>()

        // Get Contas
        BackendConta.GetAllContas { result ->
            for (conta in result) {
                contas.add(BackendConta.GetAge(conta)!!)
            }
        };

        return mathUtils.GetAverageIntList(contas)
    }
}