package com.example.p1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class CinemaActivity : AppCompatActivity() {

    val combinedData = ArrayList<Cinema>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cinema)

        val listView: ListView = findViewById(R.id.ListViewCinema)

        val intent = intent
        val cinemas: ArrayList<Cinema>? = intent.getParcelableArrayListExtra("Cinemas")



        if (cinemas != null) {
            combinedData.addAll(cinemas)
            for (cinema in cinemas) {
                // Access the Supermercado objects and their properties
                Log.d("cinema", "Nome: ${cinema.nome}, CNPJ: ${cinema.CNPJ}, Caixa: ${cinema.caixa}, assentos: ${cinema.assentos}")
            }
        }


        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, combinedData)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->

            val selectedEmpresa = combinedData[position]
            println(selectedEmpresa)

            println("id")
            println(position)


            val builder = AlertDialog.Builder(this)
            builder.setTitle("Deseja excluir o cinema?")


            builder.setMessage("nome: ${selectedEmpresa.nome} \n" +
                    "cnpj: ${selectedEmpresa.CNPJ} \n" +
                    "caixa: ${selectedEmpresa.caixa} \n" +
                    "assentos: ${selectedEmpresa.assentos} \n"
            )

            builder.setPositiveButton(android.R.string.ok) { dialog, which ->

                combinedData.removeAt(position)

                /*listaCarro.remove(listaCarro[i])
                adaptadorCarro = ArrayAdapter(this, android.R.layout.simple_list_item_1,listaCarro)
                lvAutomoveis.adapter = adaptadorCarro*/

                Intent().apply {
                    putParcelableArrayListExtra("modifiedDataCinema", combinedData)
                    setResult(RESULT_OK, this)
                }
                this.finish()

                Toast.makeText(applicationContext,
                    android.R.string.yes, Toast.LENGTH_SHORT).show()
            }

            builder.setNeutralButton("editar"){dialog, which ->
                Toast.makeText(applicationContext,
                    "editar", Toast.LENGTH_SHORT).show()

                mostrarDialogEdit(position)

            }

            builder.setNegativeButton(android.R.string.cancel) { dialog, which ->
                Toast.makeText(applicationContext,
                    android.R.string.no, Toast.LENGTH_SHORT).show()
            }

            builder.show()

        }

    }

    fun mostrarDialogEdit(position: Int) {

        val build = AlertDialog.Builder(this)
        val minhaView = layoutInflater.inflate(R.layout.dialog_cinema,null)
        build.setView(minhaView)


        var editTextNome = minhaView.findViewById<EditText>(R.id.editNome)
        var editTextCnpj = minhaView.findViewById<EditText>(R.id.editCnpj)
        var editTextCaixa = minhaView.findViewById<EditText>(R.id.editCaixa)
        //especifico
        var editAssento = minhaView.findViewById<EditText>(R.id.editAssento)

        build.setPositiveButton(android.R.string.ok) { dialog, which ->



            var cinemaEditado = Cinema(editTextNome.text.toString(),editTextCnpj.text.toString(),editTextCaixa.text.toString().toFloat(),editAssento.text.toString().toInt())

            combinedData[position] = cinemaEditado

            Intent().apply {
                putParcelableArrayListExtra("modifiedDataCinema", combinedData)
                setResult(RESULT_OK, this)
            }
            this.finish()

        }

        build.setNegativeButton(android.R.string.cancel) { dialog, which ->



        }

        var dialog = build.create()
        dialog.show()





        editTextNome.setText(combinedData[position].nome)
        editTextCnpj.setText(combinedData[position].CNPJ)
        editTextCaixa.setText(combinedData[position].caixa.toString())
        editAssento.setText(combinedData[position].assentos.toString())

        println(editTextNome)

    }

}