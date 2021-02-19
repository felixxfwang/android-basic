package org.tiramisu.page.modular.fragment

import android.view.View
import androidx.fragment.app.Fragment
import org.tiramisu.page.modular.IPageModule

/**
 *
 * @author felixxfwang
 * @date   2019-12-12
 */
interface IFragmentModule : IPageModule {

    fun onFragmentCreated() {}

    fun onFragmentViewPreCreate(fragment: Fragment): View? = null

    fun onFragmentViewCreated(fragment: Fragment, view: View) {}

    fun onFragmentViewReused() {}

    fun onFragmentViewRefresh() {}

    fun onFragmentShow() {}

    fun onFragmentHide() {}

    fun onFragmentResume() {}

    fun onFragmentPause() {}

    fun onHiddenChanged(hidden: Boolean) {}

    fun onSetUserVisibleHint(visible: Boolean) {}

    fun onFragmentViewDestroyed() {}

    fun onFragmentDestroyed() {}
}
