package com.example.p1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
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
import android.os.Environment  
import java.io.*

class MainActivity : AppCompatActivity() {

    lateinit var buttonInserir: Button
    lateinit var buttonListarSuper : Button
    lateinit var buttonListarPosto : Button
    lateinit var buttonListarCinema : Button
    lateinit var buttonSalvarFiles : Button
    lateinit var buttonMostrarFiles : Button

    var listaSupermercado = ArrayList<Supermercado>()
    var listaPosto = ArrayList<Posto>()
    var listaCinema = ArrayList<Cinema>()

    lateinit var editTextNome: EditText
    lateinit var editTextCnpj: EditText
    lateinit var editTextCaixa: EditText
    lateinit var editTextAssento: EditText
    lateinit var editTextCapacidade: EditText

    lateinit var textViewAr : TextView
    lateinit var textViewMostrar : TextView

    lateinit var radioGroupClasses: RadioGroup
    lateinit var radioGroupClassMercado: RadioGroup

    lateinit var pathFiles : String   // caminho dos arquivos
    lateinit var fileCinema : File
    lateinit var fileSupermercado : File
    lateinit var filePosto : File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonInserir = findViewById(R.id.buttonInserir)
        buttonListarSuper = findViewById(R.id.buttonListaSuper)
        buttonListarPosto = findViewById(R.id.buttonListaPosto)
        buttonListarCinema = findViewById(R.id.buttonListaCinema)
	buttonSalvarFiles = findViewById(R.id.buttonSalvarDados)
	buttonMostrarFiles = findViewById(R.id.buttonMostrarFiles)
        editTextNome = findViewById(R.id.editNome)
        editTextCnpj = findViewById(R.id.editCnpj)
        editTextCaixa = findViewById(R.id.editCaixa)
        radioGroupClasses = findViewById(R.id.radioGroupClass)
        radioGroupClassMercado = findViewById(R.id.especificoSuper)
        editTextAssento = findViewById(R.id.editTextAssento)
        editTextCapacidade = findViewById(R.id.editTextCapacidade)

        textViewAr = findViewById(R.id.textViewAr)
	textViewMostrar = findViewById(R.id.textViewArquivos)
	pathFiles = "arquivos"
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
	buttonSalvarFile.setOnClickListener{
		fileCinema= File(getExternalFilesDir(pathFiles), "cinemas.txt")
		fileSupermercado= File(getExternalFilesDir(pathFiles), "supermercados.txt")
		filePosto= File(getExternalFilesDir(pathFiles), "postos-gasolina.txt")
		
		for(cinema in listaCinema){
		try{
		val fileOutPutStream = FileOutputStream(fileCinema)  
                fileOutPutStream.write(cinema.toString().toByteArray())  
                fileOutPutStream.close()

		}catch(e: IOException){
		    e.printStackTrace()  
		}
		Toast.makeText(applicationContext,"Cinema Salvo!",Toast.LENGTH_SHORT).show() 
    		}
	
		for(posto in listaPosto){
		   try{
		   val fileOutPutStream = FileOutputStream(filePosto)  
                   fileOutPutStream.write(posto.toString().toByteArray())  
                   fileOutPutStream.close()

		}catch(e: IOException){
		    e.printStackTrace()  
		}
	       Toast.makeText(applicationContext,"Posto Salvo!",Toast.LENGTH_SHORT).show() 
    	       }

		for(mercado in listaSupermercado){
		  try{
		   val fileOutPutStream = FileOutputStream(fileSupermercado)  
                   fileOutPutStream.write(mercado.toString().toByteArray())  
                   fileOutPutStream.close()

		}catch(e: IOException){
		    e.printStackTrace()  
		}
		Toast.makeText(applicationContext,"Supermercado Salvo!",Toast.LENGTH_SHORT).show() 
    		}
}
	
	buttonMostrarFiles.setOnClickListener(){
	   	val filenamePosto = "postos-gasolina.txt"
           	val fileNameCinema = "cinemas.txt"
	   	val fileNameMercado = "supermercado.txt"
		 myExternalFileCinemas = File(getExternalFilesDir(pathFile), filenameCinema.text.toString())  
  		 myExternalFileCinemas = File(getExternalFilesDir(pathFile), filenamePosto.text.toString()) 
		 myExternalFilePostos = File(getExternalFilesDir(pathFile), filenameMercado.text.toString()) 
          
          	textViewMostrar.setVisibility(View.VISIBLE) 
           	if(filenameCinema.toString()!=null && filenameCinema.toString().trim()!=""){  
              	    var fileInputStream = FileInputStream(myExternalFileCinemas)  
                    var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)  
                    val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)  
                    val stringBuilder: StringBuilder = StringBuilder()  
                    var text: String? = null

                    while ({ text = bufferedReader.readLine(); text }() != null) {  
                        stringBuilder.append(text)  
                    }  
               	    fileInputStream.close()  
             	// Mostrar arquivos no TextView
                   texViewMostrar.show(stringBuilder.toString)  
           	}
	       if(filenameMercado.toString()!=null && filenameMercado.toString().trim()!=""){  
               	  var fileInputStream = FileInputStream(myExternalFileMercados)  
                  var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)  
                  val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)  
                  val stringBuilder: StringBuilder = StringBuilder()  
                  var text: String? = null  
                  while ({ text = bufferedReader.readLine(); text }() != null) {  
                     stringBuilder.append(text)  
                  }  
                  fileInputStream.close()  
               // Mostrar arquivos no TextView
                  texViewMostrar.show(stringBuilder.toString)  
           } 
	     if(filenamePosto.toString()!=null && filenamePosto.toString().trim()!=""){  
                var fileInputStream = FileInputStream(myExternalFilePostos)  
                var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)  
                val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)  
                val stringBuilder: StringBuilder = StringBuilder()  
                var text: String? = null  
                while ({ text = bufferedReader.readLine(); text }() != null) {  
                   stringBuilder.append(text)  
               }  
               fileInputStream.close()  
               // Mostrar arquivos no TextView
               texViewMostrar.show(stringBuilder.toString)  
           }    	
}



    val register = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let {
                if (it.hasExtra("modifiedDataSuper")) {
                    //val supermercadosList: ArrayList<Supermercado>? = intent.getParcelableArrayListExtra("modifiedData")

                    //println(it.getParcelableArrayExtra("modifiedData"))

                    val estudante1: ArrayList<Supermercado>? = it.getParcelableArrayListExtra("modifiedDataSuper")
                    Log.i("TESTE","Veio de T2: "+estudante1)

                    println("entrei")
                    if (estudante1 != null) {
                        listaSupermercado= estudante1
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