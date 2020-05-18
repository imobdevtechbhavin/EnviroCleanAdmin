package com.envirocleanadmin.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.envirocleanadmin.R
import com.envirocleanadmin.base.BaseActivity
import com.envirocleanadmin.data.ApiService
import com.envirocleanadmin.data.Prefs
import com.envirocleanadmin.databinding.ActivitySplashBinding
import com.envirocleanadmin.di.component.DaggerNetworkLocalComponent
import com.envirocleanadmin.di.component.NetworkLocalComponent
import com.envirocleanadmin.utils.AppConstants
import com.envirocleanadmin.utils.AppConstants.SPLASH_TIME
import com.envirocleanadmin.utils.AppUtils
import com.envirocleanadmin.viewmodels.SplashViewModel
import javax.inject.Inject


class SplashActivity : BaseActivity<SplashViewModel>() {

    companion object {
        fun newInstance(context: Context): Intent {
            val intent = Intent(context, SplashActivity::class.java)
            return intent
        }
    }

    private lateinit var mViewModel: SplashViewModel
    private lateinit var binding: ActivitySplashBinding
    private var handler: Handler? = null

    /*Injecting prefs from DI*/
    @Inject
    lateinit var prefs: Prefs

    /*Injecting apiService*/
    @Inject
    lateinit var apiService: ApiService


    private var isResponseReceived = false
    private var isTimeOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        /*window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)*/

        /*NetworkLocalComponent of DI will provide us all the necessary variables that we need to use in this activity
        * So, initializing DI here*/
        val requestsComponent: NetworkLocalComponent = DaggerNetworkLocalComponent
            .builder()
            .networkComponent(getNetworkComponent())
            .localDataComponent(getLocalDataComponent())
            .build()
        requestsComponent.injectSplashActivity(this)

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.tvDisplayVersion.text = AppConstants.DISPLAY_VERSION
        //window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        init()


    }

    override fun getViewModel(): SplashViewModel {
        mViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        return mViewModel
    }

    override fun internetErrorRetryClicked() {

    }

    private fun init() {

        handler = Handler()

    }

    override fun onResume() {
        super.onResume()
        handler!!.postDelayed(mRunnable, SPLASH_TIME.toLong())
    }

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            startApp()
        }

    }


    override fun onPause() {
        super.onPause()
        handler!!.removeCallbacks(mRunnable)
    }

    private fun startApp() {
        /*Either user is logged in or has skipped the login
            * then we will redirect him to MainActivity*/
        if (prefs.isLoggedIn || prefs.isSkipped) {
            startActivity(LoginActivity.newInstance(this))
            AppUtils.startFromRightToLeft(this)
        } else {
            // Otherwise we will start from the starch
            startActivity(LoginActivity.newInstance(this))
            AppUtils.startFromRightToLeft(this)
        }

        finishAffinity()
    }

}


