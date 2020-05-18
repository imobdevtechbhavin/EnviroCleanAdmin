package com.envirocleanadmin.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.envirocleanadmin.R
import com.envirocleanadmin.base.BaseActivity
import com.envirocleanadmin.data.ApiService
import com.envirocleanadmin.data.Prefs
import com.envirocleanadmin.data.response.LoginResponse
import com.envirocleanadmin.databinding.ActivityCommunityDetailsBinding
import com.envirocleanadmin.di.component.DaggerNetworkLocalComponent
import com.envirocleanadmin.di.component.NetworkLocalComponent
import com.envirocleanadmin.utils.AppConstants
import com.envirocleanadmin.viewmodels.CommunityDetailsViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import javax.inject.Inject


class CommunityDetailsActivity : BaseActivity<CommunityDetailsViewModel>(), View.OnClickListener,
    OnMapReadyCallback {

    private lateinit var binding: ActivityCommunityDetailsBinding
    private lateinit var mViewModel: CommunityDetailsViewModel

    private lateinit var mMap: GoogleMap
    private var isShowPassword = false
    /*Injecting prefs from DI*/
    @Inject
    lateinit var prefs: Prefs

    /*Injecting apiService*/
    @Inject
    lateinit var apiService: ApiService


    private val communityName:String by lazy {
        intent.getStringExtra(AppConstants.COMMUNITY_NAME)
    }
    companion object {
        fun newInstance(context: Context,communityName:String): Intent {
            val intent = Intent(context, CommunityDetailsActivity::class.java)
            intent.putExtra(AppConstants.COMMUNITY_NAME,communityName)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        val requestsComponent: NetworkLocalComponent = DaggerNetworkLocalComponent
            .builder()
            .networkComponent(getNetworkComponent())
            .localDataComponent(getLocalDataComponent())
            .build()
        requestsComponent.injectCommunityDetailsActivity(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_details)

        init()
    }

    private fun init() {
        mViewModel.setInjectable(apiService, prefs)
        setToolBar(communityName,R.color.pantone_072)
        mViewModel.getLoginResponse().observe(this, loginResponseObserver)

    }

    private fun setToolBar(title: String, bgColor: Int) {
        // setting toolbar title
        setToolbarTitle(title)
        // toolbar color

        setToolbarColor(bgColor)

        // toolbar left icon and its click listener
        setToolbarLeftIcon(R.drawable.ic_back_white, object : ToolbarLeftImageClickListener {
            override fun onLeftImageClicked() {
                onBackPressed()
            }
        })
        setToolbarRightIcon(
            R.drawable.ic_plus_white,
            object : ToolbarRightImageClickListener {
                override fun onRightImageClicked() {

                }
            })

    }
    private val loginResponseObserver = Observer<LoginResponse> {

    }
    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!

    }
    override fun getViewModel(): CommunityDetailsViewModel {
        mViewModel = ViewModelProvider(this).get(CommunityDetailsViewModel::class.java)
        return mViewModel
    }

    override fun internetErrorRetryClicked() {
    }

    override fun onClick(v: View?) {
    }

}
