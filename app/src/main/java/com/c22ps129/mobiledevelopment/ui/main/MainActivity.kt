package com.c22ps129.mobiledevelopment.ui.main

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.c22ps129.mobiledevelopment.R
import com.c22ps129.mobiledevelopment.data.Ocr
import com.c22ps129.mobiledevelopment.utils.createCustomTempFile
import com.c22ps129.mobiledevelopment.data.UserPreference
import com.c22ps129.mobiledevelopment.databinding.ActivityMainBinding
import com.c22ps129.mobiledevelopment.network.ApiConfig
import com.c22ps129.mobiledevelopment.response.ListPredict
import com.c22ps129.mobiledevelopment.response.OcrResponse
import com.c22ps129.mobiledevelopment.ui.profile.ProfileActivity
import com.c22ps129.mobiledevelopment.utils.uriToFile
import com.c22ps129.mobiledevelopment.utils.ViewModelFactory
import com.c22ps129.mobiledevelopment.utils.reduceFileImage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    lateinit var tts: TextToSpeech
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupViewModel()

        binding.bottomNavigation.selectedItemId = R.id.home

        tts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR) {
                tts.language = Locale.US
            }
        })
    }

    private fun startUpload(){
        if(getFile != null){
            val file = reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part =MultipartBody.Part.createFormData(
                "img",
                file.name,
                requestImageFile
            )

            val service = ApiConfig.getApiService2()
                .uploadImage(imageMultipart)
            service.enqueue(object : Callback<OcrResponse>{
                override fun onResponse(call: Call<OcrResponse>, response: Response<OcrResponse>) {
                    if (response.isSuccessful){
                        val responseBody = response.body()
                        if (responseBody != null){
                            mainViewModel.saveOcr(Ocr(
                                responseBody.prediction.box,
                                responseBody.prediction.text
                            ))
                        }
                    }
                }
                override fun onFailure(call: Call<OcrResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, getString(R.string.failedRetrofit), Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun setOcrResponse(items: List<ListPredict>){
        val listOcr = ArrayList<ListPredict>()
        for (data in items){
            val result = ListPredict(
                data.box,
                data.text)
            listOcr.add(result)
        }
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
        ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]
        action()

        binding.btnAdd.setOnClickListener { startGallery() }
        binding.fltPlay.setOnClickListener { starSpeech() }
        binding.btnOcr.setOnClickListener { startUpload() }
    }


    private fun action(){
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    binding.bottomNavigation.selectedItemId = R.id.home
                    finish()
                    true
                }
                R.id.scan -> {
                    startTakePhoto()
                    false
                }
                else -> true
            }
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@MainActivity,
                "com.c22ps129.mobiledevelopment",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent()

        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun starSpeech() {
        val toSpeak = binding.etOcr.text.toString()
        if (toSpeak == "") {
            Toast.makeText(this, "No Text Inputted", Toast.LENGTH_SHORT).show()
        } else {
            tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null)
            var pitch = binding.seekbarPitch.progress.toFloat()/50
            if (pitch<0.1) pitch = 0.1f

            var speed = binding.seekbarSpeed.progress.toFloat()/50
            if (speed<0.1) speed = 0.1f

            tts.setPitch(pitch)
            tts.setSpeechRate(speed)
            tts.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null)
        }
    }

    private lateinit var currentPhotoPath: String
    private var getFile: File? = null

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            getFile = File(currentPhotoPath)

            val result =  BitmapFactory.decodeFile(getFile?.path)
//            Silakan gunakan kode ini jika mengalami perubahan rotasi
//            val result = rotateBitmap(
//                BitmapFactory.decodeFile(myFile.path),
//                true
//            )

            binding.etInputtext.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@MainActivity)
            val contentResolver: ContentResolver = contentResolver
            getFile = myFile

            binding.etInputtext.setImageURI(selectedImg)
        }
    }
}