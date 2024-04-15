package fes.aragon

class Card(idcp:Int, cdo:String, asnt: String, mnp: String, edo: String) {
    val id:Int
    val codigo:String
    val asentamiento: String
    val municipio:String
    val estado:String

    init {
        id = idcp
        codigo = cdo
        asentamiento = asnt
        municipio = mnp
        estado = edo
    }
}