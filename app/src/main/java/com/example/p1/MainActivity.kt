package com.example.p1

import Empresa
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.lang.reflect.GenericArrayType
import java.util.Objects



class MainActivity : AppCompatActivity() {

    lateinit var buttonInserir: Button
    lateinit var buttonListarSuper : Button
    lateinit var buttonListarPosto : Button
    lateinit var buttonListarCinema : Button
    lateinit var buttonSaveFiles : Button
    lateinit var buttonShowFiles : Button

    lateinit var listaSupermercado :ArrayList<Supermercado>
    lateinit var listaPosto :ArrayList<Posto>
    lateinit var listaCinema : ArrayList<Cinema>

    lateinit var editTextNome: EditText
    lateinit var editTextCnpj: EditText
    lateinit var editTextCaixa: EditText
    lateinit var editTextAssento: EditText
    lateinit var editTextCapacidade: EditText

    lateinit var textViewAr : TextView
    lateinit var textViewFiles : TextView

    lateinit var radioGroupClasses: RadioGroup
    lateinit var radioGroupClassMercado: RadioGroup





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonInserir = findViewById(R.id.buttonInserir)
        buttonListarSuper = findViewById(R.id.buttonListaSuper)
        buttonListarPosto = findViewById(R.id.buttonListaPosto)
        buttonListarCinema = findViewById(R.id.buttonListaCinema)
        buttonSaveFiles = findViewById(R.id.buttonSaveFiles)
        buttonShowFiles = findViewById(R.id.buttonMostrarArquivos)
        editTextNome = findViewById(R.id.editNome)
        editTextCnpj = findViewById(R.id.editCnpj)
        editTextCaixa = findViewById(R.id.editCaixa)
        radioGroupClasses = findViewById(R.id.radioGroupClass)
        radioGroupClassMercado = findViewById(R.id.especificoSuper)
        editTextAssento = findViewById(R.id.editTextAssento)
        editTextCapacidade = findViewById(R.id.editTextCapacidade)

        textViewAr = findViewById(R.id.textViewAr);
        textViewFiles = findViewById(R.id.tvFiles);

        listaCinema = ArrayList<Cinema>();
        listaPosto = ArrayList<Posto>();
        listaSupermercado = ArrayList<Supermercado>();
        val intent = intent

        radioGroupClasses.setOnCheckedChangeListener { radioGroup, i ->

            if (i == R.id.valueSuper){

                Toast.makeText(this, "super mercado" , Toast.LENGTH_SHORT).show()

                radioGroupClassMercado.setVisibility(View.VISIBLE)
                textViewAr.setVisibility(View.VISIBLE)
                editTextAssento.setVisibility(View.GONE)
                editTextCapacidade.setVisibility(View.GONE)

            }

            if (i == R.id.valuePosto){

                Toast.makeText(this, "posto" , Toast.LENGTH_SHORT).show()

                radioGroupClassMercado.setVisibility(View.GONE)
                radioGroupClassMercado.setVisibility(View.GONE)
                textViewAr.setVisibility(View.GONE)
                editTextAssento.setVisibility(View.GONE)
                editTextCapacidade.setVisibility(View.VISIBLE)
            }

            if (i == R.id.valueCinema){

                Toast.makeText(this, "cinema" , Toast.LENGTH_SHORT).show()
                radioGroupClassMercado.setVisibility(View.GONE)
                radioGroupClassMercado.setVisibility(View.GONE)
                textViewAr.setVisibility(View.GONE)
                editTextAssento.setVisibility(View.VISIBLE)
                editTextCapacidade.setVisibility(View.GONE)
            }

        }

       /* val supermercados: ArrayList<Supermercado>? = intent.getParcelableArrayListExtra("modifiedData")
        println("0000000000000000000000000000000000000000000000000000")
        println(supermercados)*/

       // val combinedData = ArrayList<Empresa>()

       /* if (supermercados != null) {
            //combinedData.addAll(supermercados)
            println("______________aq__________________")
            for (supermercado in supermercados) {
                // Access the Supermercado objects and their properties
                Log.d("Supermercado", "Nome: ${supermercado.nome}, CNPJ: ${supermercado.CNPJ}, Caixa: ${supermercado.caixa}, ar: ${supermercado.ar}")
            }
        }*/

        println("----------------------")
        println(listaSupermercado)

        buttonInserir.setOnClickListener {



            val cpfExisteEmSupermercado = listaSupermercado.any { it.CNPJ == editTextCnpj.text.toString() }
            val cpfExisteEmPosto = listaPosto.any { it.CNPJ == editTextCnpj.text.toString() }
            val cpfExisteEmCinema = listaCinema.any { it.CNPJ == editTextCnpj.text.toString() }
            val cpfExisteEmAlgumaLista = cpfExisteEmSupermercado || cpfExisteEmPosto || cpfExisteEmCinema

            Log.d("vendo o valor", cpfExisteEmAlgumaLista.toString())

            if (!cpfExisteEmAlgumaLista){

                 if ( radioGroupClasses.checkedRadioButtonId == R.id.valueSuper){

                     var temAr = false

                     if(radioGroupClassMercado.checkedRadioButtonId == R.id.radioButtonSim){

                         temAr = true

                     }


                     listaSupermercado.add(Supermercado(editTextNome.text.toString(),editTextCnpj.text.toString(),editTextCaixa.text.toString().toFloat(),temAr))


                 }else if (radioGroupClasses.checkedRadioButtonId == R.id.valuePosto){

                     var novoPosto = Posto(editTextNome.text.toString(),editTextCnpj.text.toString(),editTextCaixa.text.toString().toFloat(),editTextCapacidade.text.toString().toFloat())
                     listaPosto.add(novoPosto)

                 }else if (radioGroupClasses.checkedRadioButtonId == R.id.valueCinema){

                     var novoCinema = Cinema(editTextNome.text.toString(),editTextCnpj.text.toString(),editTextCaixa.text.toString().toFloat(),editTextAssento.text.toString().toInt())
                     listaCinema.add(novoCinema)

                 }
            }else{

                Toast.makeText(this, "cnpj ja existente" , Toast.LENGTH_SHORT).show()
            }

        }

        buttonListarPosto.setOnClickListener {


            val bundle = Bundle()
            bundle.putParcelableArrayList("postos", listaPosto)

            /*val intent = Intent(this, SupermercadoActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)*/
            Intent(this, PostoActivity::class.java).let {
                it.putExtras(bundle)
                register.launch(it)
            }

        }

        buttonListarSuper.setOnClickListener {


            val bundle = Bundle()
            bundle.putParcelableArrayList("supermercados", listaSupermercado)

            /*val intent = Intent(this, SupermercadoActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)*/
            Intent(this, SupermercadoActivity::class.java).let {
                it.putExtras(bundle)
                register.launch(it)
            }
        }


        buttonListarCinema.setOnClickListener {


            val bundle = Bundle()
            bundle.putParcelableArrayList("Cinemas", listaCinema)

            /*val intent = Intent(this, SupermercadoActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)*/
            Intent(this, CinemaActivity::class.java).let {
                it.putExtras(bundle)
                register.launch(it)
            }

        }

        buttonSaveFiles.setOnClickListener(){
            var ListasSet : Listas = Listas(listaCinema, listaPosto, listaSupermercado)
            val fileNameCinema : String = "cinemas.txt"
            val fileNamePosto: String = "posto-gasolina.txt"
            val fileNameSupermercado : String = "supermercados.txt"
            writeFiles(fileNameCinema, ListasSet)
            writeFiles(fileNamePosto, ListasSet)
            writeFiles(fileNameSupermercado, ListasSet)


        }

        buttonShowFiles.setOnClickListener(){
            try {

                var texto : String = ""
                File("cinemas.txt").forEachLine { linha ->

                }
                File("posto-gasolina.txt").forEachLine { linha ->
                    texto+= linha
                }
                File("supermercados.txt").forEachLine { linha ->
                    texto+= linha
                }
                textViewFiles.setText(texto)
               /* val bundle = Bundle()
                bundle.putString("texto", texto)
                /*val intent = Intent(this, SupermercadoActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)*/
                Intent(this, String::class.java).let {
                    it.putExtras(bundle)
                    register.launch(it)
                }*/
            } catch (e: FileNotFoundException) {
                println("Falha ao ler o arquivo");
                e.printStackTrace();
            }






        }


    }
    fun writeFiles(tipo : String, listas : Listas){

        when(tipo){
            "cinemas.txt" ->
                try {
                    var arquivo: OutputStream = openFileOutput(tipo, 0)

                    var osw: OutputStreamWriter = OutputStreamWriter(arquivo)
                    var bw: BufferedWriter = BufferedWriter(osw)
                    var texto: String = ""
                    for (empresa in listas.cinemas) {
                        texto += empresa.toString()
                        texto += "\n"
                    }
                    bw.write(texto)
                    bw.close()
                    Toast.makeText(this,"gravou o cinema", Toast.LENGTH_LONG).show()

                }catch(e : IOException){
                    Toast.makeText(this,"erro:" + e.toString(), Toast.LENGTH_LONG).show()
                }catch(e : FileNotFoundException){
                    Toast.makeText(this,"erro:" + e.toString(), Toast.LENGTH_LONG).show()
                }

            "posto-gasolina.txt" ->
                try {
                    var arquivo: OutputStream = openFileOutput(tipo, 0)

                    var osw: OutputStreamWriter = OutputStreamWriter(arquivo)
                    var bw: BufferedWriter = BufferedWriter(osw)
                    var texto: String = ""
                    for (empresa in listas.postos) {
                        texto += empresa.toString()
                        texto += "\n"
                    }
                    bw.write(texto)
                    bw.close()
                    Toast.makeText(this,"gravou o posto", Toast.LENGTH_LONG).show()

                }catch(e : IOException){
                    Toast.makeText(this,"erro:" + e.toString(), Toast.LENGTH_LONG).show()
                }catch(e : FileNotFoundException){
                    Toast.makeText(this,"erro:" + e.toString(), Toast.LENGTH_LONG).show()
                }

            "supermercados.txt" ->
                try {
                    var arquivo: OutputStream = openFileOutput(tipo, 0)

                    var osw: OutputStreamWriter = OutputStreamWriter(arquivo)
                    var bw: BufferedWriter = BufferedWriter(osw)
                    var texto: String = ""
                    for (empresa in listas.supermercados) {
                        texto += empresa.toString()
                        texto += "\n"
                    }
                    bw.write(texto)
                    bw.close()
                    Toast.makeText(this,"gravou o mercadin", Toast.LENGTH_LONG).show()

                }catch(e : IOException){
                    Toast.makeText(this,"erro:" + e.toString(), Toast.LENGTH_LONG).show()
                }catch(e : FileNotFoundException){
                    Toast.makeText(this,"erro:" + e.toString(), Toast.LENGTH_LONG).show()
                }

        }

    }



    val register = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let {
                if (it.hasExtra("modifiedDataSuper")) {
                    //val supermercadosList: ArrayList<Supermercado>? = intent.getParcelableArrayListExtra("modifiedData")

                    //println(it.getParcelableArrayExtra("modifiedData"))

                    val supermercado: ArrayList<Supermercado>? = it.getParcelableArrayListExtra("modifiedDataSuper")
                    Log.i("TESTE","Veio de T2: "+supermercado)

                    println("entrei")
                    if (supermercado != null) {
                        listaSupermercado= supermercado
                    }

                    println(listaSupermercado)
                }else if (it.hasExtra("modifiedDataPosto")){
                    val postos: ArrayList<Posto>? = it.getParcelableArrayListExtra("modifiedDataPosto")
                    //Log.i("TESTE","Veio de T2: "+estudante1)

                    println("entrei")
                    if (postos != null) {
                        listaPosto= postos
                    }
                }else if (it.hasExtra("modifiedDataCinema")){
                    val cinemas: ArrayList<Cinema>? = it.getParcelableArrayListExtra("modifiedDataCinema")
                    //Log.i("TESTE","Veio de T2: "+estudante1)

                    println("entrei")
                    if (cinemas != null) {
                        listaCinema= cinemas
                    }
                }


            }
        }
    }


}