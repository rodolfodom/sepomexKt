package fes.aragon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import fes.aragon.databinding.ActivityMainBinding
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private var adapter:MaterialAdapter?=null
    private var listaTarjetas=ArrayList<Card>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var volleyAPI: VolleyAPI
    private val ipPuerto = "192.168.0.254:8080"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        volleyAPI = VolleyAPI(this)
        recyclerView=binding.recyclerView
        recyclerView.layoutManager= LinearLayoutManager(this)
        adapter = MaterialAdapter(this, listaTarjetas)
        recyclerView.adapter=adapter

        binding.button.setOnClickListener {
            Log.d("boton", "presionado")
            listaTarjetas.clear()
            adapter?.notifyDataSetChanged()
            codigoPostal()
        }

    }

    private fun codigoPostal() {

        var cod = binding.editTextText.text
        recyclerView.smoothScrollToPosition(0)
        if (cod.isNotEmpty()) {
            val urlJSON = "http://" + ipPuerto + "/datos/" + cod

            val jsonRequest = object : JsonArrayRequest(
                urlJSON,
                Response.Listener<JSONArray> { response ->
                    Log.d("respuesta", response.toString())
                    if (response.length() > 0) {
                        (0 until response.length()).forEach {
                            val codigo = response.getJSONObject(it)
                            val id = codigo.get("id_cps").toString().toInt()
                            val cp = codigo.get("codigo").toString()
                            val asen = codigo.get("asentamiento").toString()
                            val munObj = codigo.getJSONObject("municipio")
                            val mun = munObj.get("nombre").toString()
                            val estObj = munObj.getJSONObject("estado")
                            val est = estObj.get("estados").toString()
                            val myCard = Card(id, cp, asen, mun, est)
                            Log.d("carta creada", myCard.toString())
                            listaTarjetas.add(myCard)
                            Log.d("carta agregada", myCard.toString())
                        }
                        adapter?.notifyDataSetChanged()
                    } else {
                        Toast.makeText(this, "No se encontraron resultados :c", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener {
                    Toast.makeText(this, "Ocurrió un error :c", Toast.LENGTH_SHORT).show()
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["User-Agent"] = "Mozilla/5.0 (Windows NT 6.1)"
                    return headers
                }
            }
            volleyAPI.add(jsonRequest)
        } else {
            Toast.makeText(this, "Ingresa un codigo postal", Toast.LENGTH_SHORT).show()
        }
    }
}