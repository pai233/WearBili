package cn.spacexc.wearbili.manager

import android.content.Intent
import cn.spacexc.wearbili.Application
import cn.spacexc.wearbili.R
import cn.spacexc.wearbili.activity.other.SplashScreenActivity
import cn.spacexc.wearbili.dataclass.settings.ChooseItem
import cn.spacexc.wearbili.dataclass.settings.SettingItem
import cn.spacexc.wearbili.dataclass.settings.SettingType
import cn.spacexc.wearbili.utils.NetworkUtils
import cn.spacexc.wearbili.utils.SharedPreferencesUtils
import cn.spacexc.wearbili.utils.ToastUtils
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * Created by XC-Qan on 2022/8/13.
 * I'm very cute so please be nice to my code!
 * 给！爷！写！注！释！
 * 给！爷！写！注！释！
 * 给！爷！写！注！释！
 */

object SettingsManager {
    val settingsList = listOf(
        SettingItem(
            type = SettingType.TYPE_CHOOSE,
            settingName = "defaultPlayer",
            displayName = "默认播放器",
            description = "点击播放时启动的播放器",
            iconRes = R.drawable.ic_baseline_play_circle_outline_24,
            requireSave = true,
            defString = "builtinPlayer",
            chooseItems = listOf(
                ChooseItem(
                    name = "builtinPlayer",
                    displayName = "内建播放器",
                    icon = R.mipmap.app_icon,
                    description = "WearBili内置播放器"
                ),
                ChooseItem(
                    name = "minifyPlayer",
                    displayName = "精简播放器",
                    icon = R.mipmap.app_icon,
                    description = "无添加的播放器"
                ),
                ChooseItem(
                    name = "microTvPlayer",
                    displayName = "小电视播放器",
                    icon = R.drawable.micro_tv_player_icon,
                    description = "由心想事成开发"
                ),
                ChooseItem(
                    name = "microTaiwan",
                    displayName = "小抬腕API中转",
                    icon = R.drawable.empty_placeholder,
                    description = "可调用抬腕视频"
                ),
                ChooseItem(
                    name = "other",
                    displayName = "其他",
                    icon = R.drawable.empty_placeholder,
                    description = "此设备上其他播放器"
                )
            )
        ),
        SettingItem(
            type = SettingType.TYPE_SWITCH,
            settingName = "hasScrollVfx",
            displayName = "滑动动效",
            description = "曲线列表边缘",
            iconRes = R.drawable.ic_baseline_filter_list_24,
            requireRestart = true,
            defBool = Application.context?.resources?.configuration?.isScreenRound!!,
            requireSave = true,
        ),
        SettingItem(
            type = SettingType.TYPE_SWITCH,
            settingName = "isDebugging",
            displayName = "调试开关",
            description = "显示调试信息",
            iconRes = R.drawable.ic_baseline_code_24,
            defBool = false,
            requireSave = true,
        ),
        SettingItem(
            type = SettingType.TYPE_ACTION,
            settingName = "logOut",
            displayName = "登出",
            iconRes = R.drawable.logout,
            requireSave = false,
            color = "#FE679A",
            description = "登出当前的账号",
            action = {
                UserManager.logout(object : NetworkUtils.ResultCallback<Boolean> {
                    override fun onSuccess(result: Boolean, code: Int) {
                        MainScope().launch {
                            if (result) {
                                CookiesManager.deleteAllCookies()
                                ToastUtils.showText("登出成功")
                                Intent(
                                    Application.context,
                                    SplashScreenActivity::class.java
                                ).apply {
                                    flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    Application.context?.startActivity(this)
                                }
                            } else {
                                ToastUtils.showText("网络异常")
                            }
                        }
                    }

                    override fun onFailed(e: Exception) {
                        MainScope().launch {
                            ToastUtils.showText("网络异常")
                        }
                    }

                })
            }
        )
    )

    fun getSettingByName(name: String): SettingItem? {
        for (item in settingsList) {
            if (item.settingName == name) return item
        }
        return null
    }

    fun isDebug(): Boolean = SharedPreferencesUtils.getBoolean("isDebugging", false)
    fun hasScrollVfx(): Boolean = SharedPreferencesUtils.getBoolean("hasScrollVfx", true)
    fun defPlayer(): String = SharedPreferencesUtils.getString("defaultPlayer", "builtinPlayer")
}