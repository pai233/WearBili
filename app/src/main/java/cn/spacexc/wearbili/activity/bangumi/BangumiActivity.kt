package cn.spacexc.wearbili.activity.bangumi

import OnClickListerExtended
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import cn.spacexc.wearbili.adapter.BangumiViewPagerAdapter
import cn.spacexc.wearbili.databinding.ActivityVideoBinding
import cn.spacexc.wearbili.fragment.CommentFragment
import cn.spacexc.wearbili.manager.ID_TYPE_EPID
import cn.spacexc.wearbili.utils.TimeUtils
import cn.spacexc.wearbili.viewmodel.BangumiViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class BangumiActivity : AppCompatActivity() {
    val viewModel by viewModels<BangumiViewModel>()
    private lateinit var binding: ActivityVideoBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getBangumi(
            idType = intent.getStringExtra("idType") ?: ID_TYPE_EPID,
            id = intent.getStringExtra("id") ?: ""
        )
        binding.viewPager2.adapter = BangumiViewPagerAdapter(this)
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.pageName.text = (when (position) {
                    0 -> "番剧详情"
                    1 -> "单话评论"
                    else -> ""
                })
            }
        })
        binding.titleBar.setOnTouchListener(OnClickListerExtended(object :
            OnClickListerExtended.OnClickCallback {
            override fun onSingleClick() {
            }

            override fun onDoubleClick() {
                val fragment =
                    supportFragmentManager.findFragmentByTag("f${binding.viewPager2.currentItem}")
                when (binding.viewPager2.currentItem) {
                    1 -> {
                        (fragment as CommentFragment).apply {
                            refresh()
                        }
                    }
                }
            }

        }))
        lifecycleScope.launch {
            while (true) {
                binding.timeText.text = TimeUtils.getCurrentTime()
                delay(500)
            }
        }
        binding.pageName.setOnClickListener { finish() }
    }
}