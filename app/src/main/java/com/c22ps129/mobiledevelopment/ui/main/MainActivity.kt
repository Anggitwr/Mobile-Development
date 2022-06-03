package com.c22ps129.mobiledevelopment.ui.main

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.c22ps129.mobiledevelopment.R
import com.c22ps129.mobiledevelopment.createCustomTempFile
import com.c22ps129.mobiledevelopment.data.UserPreference
import com.c22ps129.mobiledevelopment.databinding.ActivityMainBinding
import com.c22ps129.mobiledevelopment.ui.OnBoardingActivity
import com.c22ps129.mobiledevelopment.ui.profile.ProfileActivity
import com.c22ps129.mobiledevelopment.uriToFile
import com.c22ps129.mobiledevelopment.utils.ViewModelFactory
import java.io.File

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupViewModel()
        action()

    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
        ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]
        authMain()
    }

    private fun authMain(){
        mainViewModel.auth().observe(this){
            if (!it){
                startActivity(Intent(this,OnBoardingActivity::class.java))
                finish()
            }
        }
//        mainViewModel.getUser().observe(this) { user ->
//            if (user.isLogin) {
////                startActivity(Intent(this, MainActivity::class.java))
//            } else {
//                startActivity(Intent(this, OnBoardingActivity::class.java))
////                finish()
//            }
//        }
    }

    private fun action(){
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.scan -> {
                    startTakePhoto()
                    false
                }
                R.id.btn_add -> {
                    startGallery()
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