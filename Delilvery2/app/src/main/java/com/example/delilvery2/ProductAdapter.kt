package com.example.delilvery2

// ProductAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductAdapter(
    private val products: List<Product>,
    private val onProductClickListener: (Product) -> Unit
) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view, onProductClickListener)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return products.size
    }
}

class ProductViewHolder(itemView: View, private val onProductClickListener: (Product) -> Unit) : RecyclerView.ViewHolder(itemView) {
    private val nameTextView: TextView = itemView.findViewById(R.id.product_name)
    private val descriptionTextView: TextView = itemView.findViewById(R.id.product_description)
    private val priceTextView: TextView = itemView.findViewById(R.id.product_price)
    private val imageView: ImageView = itemView.findViewById(R.id.product_image)
    private val btnComprar: Button = itemView.findViewById(R.id.btncomprar)

    fun bind(product: Product) {
        nameTextView.text = product.name
        descriptionTextView.text = product.description
        priceTextView.text = "$${product.price}"

        Glide.with(itemView)
            .load(product.image)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.placeholder_image)
            .into(imageView)

        // Listener para el botón de compra
        btnComprar.setOnClickListener {
            // Llama al oyente de clics y pasa el producto asociado
            onProductClickListener.invoke(product)
        }
    }
}

