package com.grupo2.plusorder.utils.backendutils

import com.grupo2.plusorder.backend.models.Pedido
import com.grupo2.plusorder.backend.models.PedidoPrato
import com.grupo2.plusorder.backend.models.Prato
import com.grupo2.plusorder.backend.tables.BackendPedidoPrato

object PedidoUtils {

    fun AddPratoToPedido(pedido: Pedido, prato: Prato, qtd: Integer) : Boolean {
        return BackendPedidoPrato.AddPedidoPrato(PedidoPrato(
            prato.id, pedido.id, qtd, prato.preco
        ))
    }
}