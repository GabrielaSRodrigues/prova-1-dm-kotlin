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

class PostoActivity : AppCompatActivity() {

    val combinedData = ArrayList<Posto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posto)

        val listView: ListView = findViewById(R.id.ListViewPosto)

        val intent = intent
        val postos: ArrayList<Posto>? = intent.getParcelableArrayListExtra("postos")



        if (postos != null) {
            combinedData.addAll(postos)
            for (posto in postos) {
                // Access the Supermercado objects and their properties
                Log.d("Posto", "Nome: ${posto.nome}, CNPJ: ${posto.CNPJ}, Caixa: ${posto.caixa}, capacidade: ${posto.capacidade}")
            }
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, combinedData)
        listView.adapter = adapter

        listView.setOnItemClickListener { adapterView, view, position, l ->

            val selectedEmpresa = combinedData[position]
            println(selectedEmpresa)

            println("id")
            println(position)


            val builder = AlertDialog.Builder(this)
            builder.setTitle("Deseja excluir o Posto?")


            builder.setMessage("nome: ${selectedEmpresa.nome} \n" +
                    "cnpj: ${selectedEmpresa.CNPJ} \n" +
                    "caixa: ${selectedEmpresa.caixa} \n" +
                    "capacidade: ${selectedEmpresa.capacidade} \n"
            )

            builder.setPositiveButton(android.R.string.ok) { dialog, which ->

                combinedData.removeAt(position)

                /*listaCarro.remove(listaCarro[i])
                adaptadorCarro = ArrayAdapter(this, android.R.layout.simple_list_item_1,listaCarro)
                lvAutomoveis.adapter = adaptadorCarro*/

                Intent().apply {
                    putParcelableArrayListExtra("modifiedDataPosto", combinedData)
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
        val minhaView = layoutInflater.inflate(R.layout.dialog_posto,null)
        build.setView(minhaView)


        var editTextNome = minhaView.findViewById<EditText>(R.id.editNome)
        var editTextCnpj = minhaView.findViewById<EditText>(R.id.editCnpj)
        var editTextCaixa = minhaView.findViewById<EditText>(R.id.editCaixa)
        //especifico
        var editCapacidade = minhaView.findViewById<EditText>(R.id.editCapacidade)

        build.setPositiveButton(android.R.string.ok) { dialog, which ->



            var postoEditado = Posto(editTextNome.text.toString(),editTextCnpj.text.toString(),editTextCaixa.text.toString().toFloat(),editCapacidade.text.toString().toFloat())

            combinedData[position] = postoEditado

            Intent().apply {
                putParcelableArrayListExtra("modifiedDataPosto", combinedData)
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
        editCapacidade.setText(combinedData[position].capacidade.toString())

        println(editTextNome)

    }
}