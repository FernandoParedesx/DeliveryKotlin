package com.example.delilvery2

// ProductAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductAdapter(private val products: List<Product>) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return products.size
    }
}

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val nameTextView: TextView = itemView.findViewById(R.id.product_name)
    private val descriptionTextView: TextView = itemView.findViewById(R.id.product_description)
    private val priceTextView: TextView = itemView.findViewById(R.id.product_price)
    private val imageView: ImageView = itemView.findViewById(R.id.product_image)

    fun bind(product: Product) {
        nameTextView.text = product.name
        descriptionTextView.text = product.description
        priceTextView.text = "$${product.price}"

        // Cargar la imagen usando Glide
        Glide.with(itemView)
            .load(product.image) // URL de la imagen almacenada en Firestore
            .placeholder(R.drawable.placeholder_image) // Imagen de carga mientras se carga la imagen real
            .error(R.drawable.placeholder_image) // Imagen a mostrar en caso de error al cargar la imagen
            .into(imageView)
    }
}
