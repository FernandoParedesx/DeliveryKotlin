package com.example.delilvery2

import CartAdapter
import CartItem
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private lateinit var textTotal: TextView
    private lateinit var btnPay: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val cartItems = try {
            intent.getSerializableExtra("cartItems") as ArrayList<CartItem>?
        } catch (e: Exception) {
            null
        }


        recyclerView = findViewById(R.id.recycler_view_cart)
        recyclerView.layoutManager = LinearLayoutManager(this)
        cartAdapter = CartAdapter(cartItems ?: arrayListOf())
        recyclerView.adapter = cartAdapter

        textTotal = findViewById(R.id.text_total)
        textTotal.text = "Total: ${calculateTotal(cartItems ?: arrayListOf())}"

        btnPay = findViewById(R.id.btn_pay)
        btnPay.setOnClickListener {
            // Aquí puedes realizar las acciones necesarias para el pago
            // Por ejemplo, podrías iniciar una nueva actividad de confirmación de pago
            // o iniciar un proceso de pago con un proveedor de servicios de pago.
        }
    }

    private fun calculateTotal(cartItems: List<CartItem>): String {
        var total = 0.0
        for (item in cartItems) {
            total += item.price * item.quantity
        }
        return "$$total"
    }
}
