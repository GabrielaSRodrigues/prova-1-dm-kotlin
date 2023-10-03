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

 class SupermercadoActivity : AppCompatActivity() {

    val combinedData = ArrayList<Supermercado>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supermercado)

        val listView: ListView = findViewById(R.id.ListViewSuperMercado)

        val intent = intent
        val supermercados: ArrayList<Supermercado>? = intent.getParcelableArrayListExtra("supermercados")



        if (supermercados != null) {
            combinedData.addAll(supermercados)
            for (supermercado in supermercados) {
                // Access the Supermercado objects and their properties
                Log.d("Supermercado", "Nome: ${supermercado.nome}, CNPJ: ${supermercado.CNPJ}, Caixa: ${supermercado.caixa}, ar: ${supermercado.ar}")
            }
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, combinedData)
        listView.adapter = adapter


        listView.setOnItemClickListener { _, _, position, _ ->
            // Handle item click here
            val selectedEmpresa = combinedData[position]
            println(selectedEmpresa)
            // You can process the selected item as needed
            // For example, you might remove it from the list

            // Notify the adapter that the data has changed
            /*adapter.notifyDataSetChanged()

            // Set the modified data as the result
            val resultIntent = Intent()
            resultIntent.putParcelableArrayListExtra("modifiedData", combinedData)
                setResult(Activity.RESULT_OK, resultIntent)

            // Finish the activity
            finish()*/


            println("id")
            println(position)


            val builder = AlertDialog.Builder(this)
            builder.setTitle("Deseja excluir o Supermercado?")


            builder.setMessage("nome: ${selectedEmpresa.nome} \n" +
                    "cnpj: ${selectedEmpresa.CNPJ} \n" +
                    "caixa: ${selectedEmpresa.caixa} \n" +
                    "ar: ${selectedEmpresa.ar} \n")


            builder.setPositiveButton(android.R.string.ok) { dialog, which ->

                combinedData.removeAt(position)

                /*listaCarro.remove(listaCarro[i])
                adaptadorCarro = ArrayAdapter(this, android.R.layout.simple_list_item_1,listaCarro)
                lvAutomoveis.adapter = adaptadorCarro*/

                Intent().apply {
                    putParcelableArrayListExtra("modifiedDataSuper", combinedData)
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
        val minhaView = layoutInflater.inflate(R.layout.dialog_super,null)
        build.setView(minhaView)


        var editTextNome = minhaView.findViewById<EditText>(R.id.editNome)
        var editTextCnpj = minhaView.findViewById<EditText>(R.id.editCnpj)
        var editTextCaixa = minhaView.findViewById<EditText>(R.id.editCaixa)
        //especificoSuper
        var editRadioAr = minhaView.findViewById<RadioGroup>(R.id.especificoSuper)

        build.setPositiveButton(android.R.string.ok) { dialog, which ->

            var temAr = false

            if(editRadioAr.checkedRadioButtonId == R.id.radioButtonSim){

                temAr = true

            }

            var superEditado = Supermercado(editTextNome.text.toString(),editTextCnpj.text.toString(),editTextCaixa.text.toString().toFloat(),temAr)

            combinedData[position] = superEditado

            Intent().apply {
                putParcelableArrayListExtra("modifiedDataSuper", combinedData)
                setResult(RESULT_OK, this)
            }
            this.finish()

        }

        build.setNegativeButton(android.R.string.cancel) { dialog, which ->



        }

        var dialog = build.create()
        dialog.show()



        if (combinedData[position].ar){
            editRadioAr.check(R.id.radioButtonSim)
        }else{
            editRadioAr.check(R.id.radioButtonNao)
        }

        editTextNome.setText(combinedData[position].nome)
        editTextCnpj.setText(combinedData[position].CNPJ)
        editTextCaixa.setText(combinedData[position].caixa.toString())

        println(editTextNome)

    }

}