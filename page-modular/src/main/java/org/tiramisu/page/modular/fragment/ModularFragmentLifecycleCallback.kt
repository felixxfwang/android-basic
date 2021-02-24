package org.tiramisu.page.modular.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

typealias FragmentModularPage = IFragmentModularPage<*, *>

object ModularFragmentLifecycleCallback : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {

    }

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
    }

    override fun onFragmentPreCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        (f as? FragmentModularPage)?.modular?.onFragmentCreated()
    }

    override fun onFragmentActivityCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
    }

    override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
        (f as? FragmentModularPage)?.modular?.onFragmentViewCreated(f, v)
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        (f as? FragmentModularPage)?.modular?.onFragmentResume()
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        (f as? FragmentModularPage)?.modular?.onFragmentPause()
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        (f as? FragmentModularPage)?.modular?.onFragmentViewDestroyed()
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        (f as? FragmentModularPage)?.let {
            it.modular.onFragmentDestroyed()
            IFragmentModularPage.modulesMap.remove(it)?.clear()
        }
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
    }
}