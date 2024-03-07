import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import CartItem
import com.example.delilvery2.R

class CartAdapter(private val cartItems: List<CartItem>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productNameTextView: TextView = itemView.findViewById(R.id.text_product_name)
        val quantityTextView: TextView = itemView.findViewById(R.id.text_quantity)
        val priceTextView: TextView = itemView.findViewById(R.id.text_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cart_item_layout, parent, false)
        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentItem = cartItems[position]

        holder.productNameTextView.text = currentItem.productName
        holder.quantityTextView.text = "Quantity: ${currentItem.quantity}"
        holder.priceTextView.text = "Price: $${currentItem.price * currentItem.quantity} "
    }

    override fun getItemCount() = cartItems.size
}
