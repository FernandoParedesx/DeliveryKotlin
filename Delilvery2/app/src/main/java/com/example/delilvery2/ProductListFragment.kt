import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.delilvery2.CartActivity
import com.example.delilvery2.Product
import com.example.delilvery2.ProductAdapter
import com.example.delilvery2.R
import com.google.firebase.firestore.FirebaseFirestore

class ProductListFragment : Fragment() {

    private lateinit var productAdapter: ProductAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnCarrito: Button
    private val products: MutableList<Product> = mutableListOf()
    private val cartItems: MutableList<CartItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_list, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        productAdapter = ProductAdapter(products) { product ->
            addToCart(product.name, product.price)
        }
        recyclerView.adapter = productAdapter

        btnCarrito = view.findViewById(R.id.btnCarrito)
        btnCarrito.setOnClickListener {
            openCart()
        }

        return view
    }

    private fun loadProductsFromFirestore() {
        val db = FirebaseFirestore.getInstance()

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
                Log.e(TAG, "Error al cargar los productos", exception)
            }
    }

    private fun addToCart(productName: String, price: Double) {
        val existingItem = cartItems.find { it.productName == productName }

        if (existingItem != null) {
            existingItem.quantity++
        } else {
            val newItem = CartItem(productName, 1, price)
            cartItems.add(newItem)
        }
        Log.d("Carrito", "Contenido del carrito:")
        for (item in cartItems) {
            Log.d("Carrito", "Producto: ${item.productName}, Cantidad: ${item.quantity}, Precio: ${item.price}")
        }
    }

    private fun openCart() {
        val intent = Intent(activity, CartActivity::class.java)
        val cartItemsParcelable = ArrayList<CartItem>()
        for (cartItem in cartItems) {
            val parcelableCartItem = CartItem(
                cartItem.productName,
                cartItem.quantity,
                cartItem.price
            )
            cartItemsParcelable.add(parcelableCartItem)
        }
        intent.putParcelableArrayListExtra("cartItems", cartItemsParcelable)
        startActivity(intent)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadProductsFromFirestore()
    }
}
