package com.iesvirreymorcillo.recyclerviewmontes

import MontesAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.iesvirreymorcillo.recyclerviewmontes.databinding.ActivityRecyclerViewMontesBinding
import java.io.InputStream
import java.nio.charset.Charset

class RecyclerViewMontes : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerViewMontesBinding
    private lateinit var copyList: MutableList<Montes>
    private lateinit var adapter: MontesAdapter

    private val addMonteLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ActivityResultCallback<ActivityResult> { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val nombre = data?.getStringExtra("nombre") ?: return@ActivityResultCallback
                val foto = data.getStringExtra("foto") ?: return@ActivityResultCallback
                val altura = data.getStringExtra("altura") ?: return@ActivityResultCallback
                val continente = data.getStringExtra("continente") ?: return@ActivityResultCallback

                val monte = Montes(nombre, foto, altura, continente)
                val monteMutableList = (binding.rvMonte.adapter as MontesAdapter).getMontesList()
                monteMutableList.add(monte)
                adapter.notifyItemInserted(monteMutableList.size - 1)
            }
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewMontesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        initSearchView()

        binding.btnAddMonte.setOnClickListener {
            val intent = Intent(this, NuevaMonte::class.java)
            addMonteLauncher.launch(intent)  // Lanzamos la actividad
        }
    }

    private fun initRecyclerView() {
        val monteMutableList = getListFromJson().toMutableList()
        adapter = MontesAdapter(
            montesList = monteMutableList,
            onClickListener = { monte -> onItemSelected(monte) },
            onClickDelete = { position -> onDeleteItem(position, monteMutableList) }
        )

        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)
        binding.rvMonte.layoutManager = manager
        binding.rvMonte.adapter = adapter
        binding.rvMonte.addItemDecoration(decoration)
    }

    private fun initSearchView() {
        binding.montes.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val filteredList: MutableList<Montes> = copyList.filter { it.nombre.lowercase().contains(newText, ignoreCase = true) }
                        .toMutableList()
                    adapter.filterByName(filteredList)
                }
                return false
            }
        })
    }

    private fun onItemSelected(monte: Montes) {
        Toast.makeText(this, monte.nombre, Toast.LENGTH_SHORT).show()
    }

    private fun onDeleteItem(position: Int, monteMutableList: MutableList<Montes>) {
        monteMutableList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    private fun getListFromJson(): MutableList<Montes> {
        val json: String? = getJsonFromAssets(this, "montes.json")
        val montesList = Gson().fromJson(json, Array<Montes>::class.java).toMutableList()
        copyList = montesList
        return montesList
    }

    private fun getJsonFromAssets(context: Context, file: String): String? {
        var json = ""
        val stream: InputStream = context.assets.open(file)
        val size: Int = stream.available()
        val buffer = ByteArray(size)
        stream.read(buffer)
        stream.close()

        json = String(buffer, Charset.defaultCharset())
        return json
    }
}
