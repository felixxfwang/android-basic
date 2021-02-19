package org.tiramisu.page.modular.visibility

import org.tiramisu.page.modular.fragment.IFragmentModule

class FragmentVisibilityModule : IFragmentModule {

    private var visibilityChangedListener: VisibilityChangedListener? = null

    // 标示当前Fragment是否可见
    private var isFragmentVisible = false

    // 标示当前Fragment在ViewPager中是否可见
    private var isVisibleToUser : Boolean? = null

    // 标示当前Fragment调用了onHiddenChanged的结果
    private var isHiddenChanged : Boolean? = null

    private var isCreated = false

    fun isFragmentVisible() = isFragmentVisible

    fun setVisibilityChangedListener(listener: VisibilityChangedListener) {
        this.visibilityChangedListener = listener
    }

    override fun onFragmentCreated() {
        isCreated = true
    }

    override fun onFragmentResume() {
        if (isVisibleToUser != false && isHiddenChanged != true) {
            onFragmentVisibilityChangedRaw(true)
        }
    }

    override fun onFragmentPause() {
        if (isVisibleToUser != false && isHiddenChanged != true) {
            onFragmentVisibilityChangedRaw(false)
        }
    }

    /**
     * Activity中Fragment切换会调用
     */
    override fun onHiddenChanged(hidden: Boolean) {
        isHiddenChanged = hidden
        if (isVisibleToUser != false) {
            onFragmentVisibilityChangedRaw(!hidden)
        }
    }

    override fun onSetUserVisibleHint(visible: Boolean) {
        this.isVisibleToUser = visible
        onFragmentVisibilityChangedRaw(visible)
    }

    private fun onFragmentVisibilityChangedRaw(isVisible : Boolean) {
        if (isCreated && isVisible != isFragmentVisible) {
            isFragmentVisible = isVisible
            onFragmentVisibilityChanged(isVisible)
        }
    }

    /**
     * 此方法是当前Fragment可见性发生变化，包括：
     * 1.ViewPager切换Fragment
     * 2.Activity中Fragment切换
     * 3.Fragment跳转Activity以及从Activity返回
     * 4.灭屏/亮屏，Home键退出
     */
    private fun onFragmentVisibilityChanged(isVisible: Boolean) {
        visibilityChangedListener?.onVisibilityChanged(isVisible)
    }
}