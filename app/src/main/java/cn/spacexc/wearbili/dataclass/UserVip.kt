package cn.spacexc.wearbili.dataclass

data class UserVip(
    val avatar_subscript: Int,
    val avatar_subscript_url: String,
    val due_date: Long,
    val label: UserLabel,
    val nickname_color: String,
    val role: Int,
    val status: Int,
    val theme_type: Int,
    val tv_vip_pay_type: Int,
    val tv_vip_status: Int,
    val type: Int,
    val vip_pay_type: Int
)