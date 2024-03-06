package com.example.delilvery2

// ProductListFragment.kt
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ProductListFragment : Fragment() {

    private lateinit var productAdapter: ProductAdapter
    private lateinit var recyclerView: RecyclerView
    private val products: MutableList<Product> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_list, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        productAdapter = ProductAdapter(products)
        recyclerView.adapter = productAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadProductsFromFirestore()
    }

    private fun loadProductsFromFirestore() {
        val db = FirebaseFirestore.getInstance()

        // Aplica el límite antes de obtener los documentos y establece el límite en 4
        db.collection("productos")
            .limit(4)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val product = Product(
                        name = document.getString("name") ?: "",
                        description = document.getString("desc") ?: "",
                        price = document.getDouble("price") ?: 0.0,
                        image = document.getString("image") ?: ""
                    )
                    products.add(product)
                }
                productAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Manejar errores
                Log.e(TAG, "Error al cargar los productos", exception)
                // Aquí puedes agregar código adicional para manejar el error, como mostrar un mensaje de error al usuario
            }
    }



}
