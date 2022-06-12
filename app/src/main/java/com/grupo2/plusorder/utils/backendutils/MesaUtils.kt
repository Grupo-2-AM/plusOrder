package com.grupo2.plusorder.utils.backendutils

import com.grupo2.plusorder.backend.models.Mesa
import com.grupo2.plusorder.backend.tables.BackendMesa

object MesaUtils {

    // Get Mesa from qr code
    fun GetMesaByDecodedQrCode(decodedQrCode: String) : Mesa? {
        var mesaFomQrCode: Mesa? = null

        var mesas = BackendMesa.GetAllMesas()
        for (mesa in mesas)
            if ((Mesa.QR_CODE + mesa.nrMesa) == decodedQrCode)
                mesaFomQrCode = mesa

        return mesaFomQrCode
    }
}