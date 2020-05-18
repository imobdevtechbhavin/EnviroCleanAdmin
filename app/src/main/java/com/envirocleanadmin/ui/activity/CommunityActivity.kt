package com.envirocleanadmin.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.envirocleanadmin.R
import com.envirocleanadmin.adapter.CommunityAdapter
import com.envirocleanadmin.base.BaseActivity
import com.envirocleanadmin.base.BaseBindingAdapter
import com.envirocleanadmin.data.ApiService
import com.envirocleanadmin.data.Prefs
import com.envirocleanadmin.data.response.LoginResponse
import com.envirocleanadmin.databinding.ActivityCommunityBinding
import com.envirocleanadmin.di.component.DaggerNetworkLocalComponent
import com.envirocleanadmin.di.component.NetworkLocalComponent
import com.envirocleanadmin.utils.RecycleViewCustom
import com.envirocleanadmin.viewmodels.CommunityViewModel
import javax.inject.Inject


class CommunityActivity : BaseActivity<CommunityViewModel>(), View.OnClickListener,
    RecycleViewCustom.onLoadMore, RecycleViewCustom.onSwipeToRefresh,
    BaseBindingAdapter.ItemClickListener<String> {



    private lateinit var binding: ActivityCommunityBinding
    private lateinit var mViewModel: CommunityViewModel


    private var isShowPassword = false
    /*Injecting prefs from DI*/
    @Inject
    lateinit var prefs: Prefs

    /*Injecting apiService*/
    @Inject
    lateinit var apiService: ApiService


    var from: String = ""

    companion object {
        fun newInstance(context: Context): Intent {
            val intent = Intent(context, CommunityActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        val requestsComponent: NetworkLocalComponent = DaggerNetworkLocalComponent
            .builder()
            .networkComponent(getNetworkComponent())
            .localDataComponent(getLocalDataComponent())
            .build()
        requestsComponent.injectCommunityActivity(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community)

        init()
    }

    private fun init() {
        mViewModel.setInjectable(apiService, prefs)
        setToolBar(getString(R.string.lbl_community), R.color.pantone_072)
        mViewModel.getLoginResponse().observe(this, loginResponseObserver)
        val adapter = CommunityAdapter()
        adapter.filterable = true
        adapter.itemClickListener=this
        binding.rvView.setAdepter(adapter)
        binding.rvView.onLoadMoreItemClick = this
        binding.rvView.swipeToRefreshItemClick = this
        setData()
    }

    private fun setToolBar(title: String, bgColor: Int) {
        // setting toolbar title
        setToolbarTitle(title)
        // toolbar color

        setToolbarColor(bgColor)

        // toolbar left icon and its click listener
        setToolbarLeftIcon(R.drawable.ic_logout_white, object : ToolbarLeftImageClickListener {
            override fun onLeftImageClicked() {
                onBackPressed()
            }
        })
        setToolbarRightIcon(
            R.drawable.ic_search,
            object : ToolbarRightImageClickListener {
                override fun onRightImageClicked() {
                    showSearchBar(R.color.pantone_072)
                    setSearch()
                }
            })

    }

    private fun setSearch() {
        val editText = getEditTextView()
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                (binding.rvView.rvItems.adapter as CommunityAdapter).filter(s.toString().toLowerCase())
            }
        })



        editText?.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                val DRAWABLE_LEFT = 0
                val DRAWABLE_TOP = 1
                val DRAWABLE_RIGHT = 2
                val DRAWABLE_BOTTOM = 3

                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= editText.right - editText.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                        hideSearchBar()
                    }
                }
                return false
            }
        })

    }
    private fun setData() {
        val list: ArrayList<String?> = ArrayList()
        list.add("Comm1")
        list.add("Comm2")
        list.add("Comm3")
        list.add("Comm4")
        list.add("Comm5")
        list.add("Comm6")
        list.add("Comm7")
        list.add("Comm8")
        list.add("Comm9")
        list.add("Comm10")
        list.add("Comm11")
        list.add("Comm12")
        list.add("Comm13")
        list.add("Comm14")
        list.add("Comm15")
        list.add("Comm16")
        list.add("Comm17")
        list.add("Comm18")
        list.add("Comm19")
        list.add("Comm20")
        list.add("Comm21")
        list.add("Comm22")
        list.add("Comm23")
        list.add("Comm24")
        list.add("Comm25")


        (binding.rvView.rvItems.adapter as CommunityAdapter).setItem(list)
        (binding.rvView.rvItems.adapter as CommunityAdapter).notifyDataSetChanged()
    }

    private val loginResponseObserver = Observer<LoginResponse> {

    }

    override fun onSwipeToRefresh() {

    }

    override fun onLoadMore() {

    }
    override fun onItemClick(view: View, data: String?, position: Int) {
        data?.let {
            startActivity(CommunityDetailsActivity.newInstance(this,data))
        }
    }
    override fun getViewModel(): CommunityViewModel {
        mViewModel = ViewModelProvider(this).get(CommunityViewModel::class.java)
        return mViewModel
    }

    override fun internetErrorRetryClicked() {
    }

    override fun onClick(v: View?) {
    }

}
