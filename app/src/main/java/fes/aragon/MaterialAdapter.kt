package fes.aragon

import android.animation.Animator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class MaterialAdapter(private val context: Context, private val listaTarjetas: ArrayList<Card>) :
    RecyclerView.Adapter<MaterialAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var code: TextView
        var asentamiento: TextView
        var municipio: TextView
        var estado: TextView
        var imagenView: ImageView
        var card: CardView

        init {
            code = view.findViewById(R.id.code)
            municipio = view.findViewById(R.id.municipio)
            asentamiento = view.findViewById(R.id.asentamiento)
            imagenView = view.findViewById(R.id.image_view)
            card = view.findViewById(R.id.card_layout)
            estado = view.findViewById(R.id.estado)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val li = LayoutInflater.from(parent.context)
        val v = li.inflate(R.layout.card_view_holder, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val code: String? = listaTarjetas[position].codigo
        val estado: String = listaTarjetas[position].estado
        val municipio: String = listaTarjetas[position].municipio
        val asentamiento:String = listaTarjetas[position].asentamiento

        val codeText: TextView = holder.code
        val municipioText: TextView = holder.municipio
        val imagen: ImageView = holder.imagenView
        val asentamientoText:TextView = holder.asentamiento
        val estadoText:TextView = holder.estado

        //rellenando los text views
        codeText.text = code
        estadoText.text = estado
        municipioText.text = municipio
        asentamientoText.text = asentamiento
        imagen.setImageResource(R.drawable.correos)

        /*
        holder.card.setOnClickListener {
            Toast.makeText(context, "Carta " + name, Toast.LENGTH_SHORT).show()
        }
        */
    }
    override fun getItemCount(): Int {
        return if (listaTarjetas.isEmpty()) {
            0
        } else {
            listaTarjetas.size
        }
    }
    override fun getItemId(position: Int): Long {
        return listaTarjetas[position].id.toLong()
    }
    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        animateCircularReveal(holder.itemView)
    }
    private fun animateCircularReveal(view: View) {
        val centroX = 0
        val centerY = 0
        val inicioRadius = 0.0f
        val finRadius = kotlin.math.max(view.width, view.height)
        val animacion: Animator = ViewAnimationUtils.createCircularReveal(
            view,
            centroX,
            centerY,
            inicioRadius,
            finRadius.toFloat()
        )
        view.visibility = View.VISIBLE
        animacion.start()
    }
}