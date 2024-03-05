package com.example.delilvery2


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.HashMap

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextName: EditText
    private lateinit var textViewError: TextView

    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextName = findViewById(R.id.editTextName)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        textViewError = findViewById(R.id.textViewError)

        buttonRegister.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val name = editTextName.text.toString().trim()

            if (validateInput(email, password, name)) {
                registerUser(email, password, name)
            }
        }
    }

    private fun validateInput(email: String, password: String, name: String): Boolean {
        if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
            textViewError.visibility = View.VISIBLE
            textViewError.text = "Please fill in all fields."
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textViewError.visibility = View.VISIBLE
            textViewError.text = "Invalid email address."
            return false
        }

        if (password.length < 6) {
            textViewError.visibility = View.VISIBLE
            textViewError.text = "Password must be at least 6 characters long."
            return false
        }

        return true
    }

    private fun registerUser(email: String, password: String, name: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    val userId = mAuth.currentUser?.uid
                    val user = HashMap<String, Any>()
                    user.put("email", email)
                    user.put("password", password)
                    user.put("name", name)

                    FirebaseFirestore.getInstance().collection("deliverym").document("usuarios").collection("usuarios").document(userId!!)
                        .set(user)
                        .addOnCompleteListener(object : OnCompleteListener<Void> {
                            override fun onComplete(@NonNull task: Task<Void>) {
                                if (task.isSuccessful) {
                                    Toast.makeText(this@RegisterActivity, "Registration successful.", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                                    finish()
                                } else {
                                    textViewError.visibility = View.VISIBLE
                                    textViewError.text = "Firestore error: " + task.exception?.message
                                }
                            }
                        })
                } else {
                    textViewError.visibility = View.VISIBLE
                    textViewError.text = "Authentication error: " + task.exception?.message
                }
            }
    }
}