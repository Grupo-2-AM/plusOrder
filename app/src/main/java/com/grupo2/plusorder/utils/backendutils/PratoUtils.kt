package com.grupo2.plusorder.utils.backendutils

import com.grupo2.plusorder.backend.models.Prato
import com.grupo2.plusorder.backend.tables.BackendPrato
import java.util.*

object PratoUtils {

    /*
        Only returns active Pratos(pratos.ativo == 1) from queries
     */

    fun GetAllPratos() : List<Prato>{
        return BackendPrato.GetPratosAtivos(BackendPrato.GetAllPratos())
    }

    fun GetAllPratosFromCategory(idCategoria: UUID) : List<Prato> {
        return BackendPrato.GetPratosAtivos(BackendPrato.GetAllPratosByCategoria(idCategoria))
    }

    fun SearchPratosByNameCategory(name: String, idCategoria: UUID) : List<Prato> {
        return BackendPrato.GetPratosAtivos(BackendPrato.SearchPratosByNameCategoria(name, idCategoria))
    }

}