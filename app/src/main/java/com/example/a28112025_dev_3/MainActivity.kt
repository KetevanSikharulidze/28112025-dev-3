package com.example.a28112025_dev_3

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a28112025_dev_3.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val dbRef = FirebaseDatabase.getInstance().getReference("companies")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        upload()
        rvInit()
    }

    private fun rvInit() = with(binding){

        val adapter = RecyclerViewAdapter()
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this@MainActivity)

        loadCompanies {
            adapter.submitList(it)
        }

    }

    private fun loadCompanies(onComplete : (List<Company>) -> Unit){

//        dbRef.addListenerForSingleValueEvent()
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val companies = mutableListOf<Company>()
                for (companySnapshot in snapshot.children){
                    val company = companySnapshot.getValue(Company::class.java)
                    if (company != null){
                        companies.add(company)
                    }
                }
                onComplete(companies)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun upload() = with(binding){

        uploadBtn.setOnClickListener {

            val name = companyNameET.text.toString()
            val year = companyFoundedYearET.text.toString()
            val size = companySizeET.text.toString()

            if (name.isEmpty() || year.isEmpty() || size.isEmpty()){
                return@setOnClickListener
            }

            val foundedYear = year.toInt()
            val companySize = size.toInt()

            val company = Company(
                name,
                foundedYear,
                companySize
            )

            dbRef.child(name).setValue(company)
                .addOnCompleteListener { result ->
                if (result.isSuccessful){
                    companyNameET.text.clear()
                    companySizeET.text.clear()
                    companyFoundedYearET.text.clear()
                } else {
                    Toast.makeText(this@MainActivity, result.exception!!.message, Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
            Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
        }


        }

    }
}