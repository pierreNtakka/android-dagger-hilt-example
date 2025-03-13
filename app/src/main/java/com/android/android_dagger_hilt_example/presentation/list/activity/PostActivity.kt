package com.android.android_dagger_hilt_example.presentation.list.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.android.android_dagger_hilt_example.R
import com.android.android_dagger_hilt_example.di.AppModule.STRING1
import com.android.android_dagger_hilt_example.di.AppModule.STRING2
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
class PostActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private val TAG = PostActivity::class.java.simpleName

    //Field Injection
    @Inject
    @Named(STRING1)
    lateinit var testString: String

    @Inject
    @Named(STRING2)
    lateinit var testString2: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        Log.d(TAG, "CIAO: $testString")
        Log.d(TAG, "CIAO: $testString2")

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_retrofit_error_mgmt) as NavHostFragment
        navController = navHostFragment.navController

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}