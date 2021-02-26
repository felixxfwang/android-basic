package org.tiramisu.page.modular.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import org.tiramisu.log.TLog

typealias FragmentModularPage = IFragmentModularPage<*, *>

object ModularFragmentLifecycleCallback : FragmentManager.FragmentLifecycleCallbacks() {
    private const val TAG = "ModularFragmentLifecycleCallback"

    override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
        TLog.i(TAG, "onFragmentPreAttached: $f")
    }

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        TLog.i(TAG, "onFragmentAttached: $f")
    }

    override fun onFragmentPreCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        TLog.i(TAG, "onFragmentPreCreated: $f")
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        TLog.i(TAG, "onFragmentCreated: $f")
        (f as? FragmentModularPage)?.modular?.onFragmentCreated()
    }

    override fun onFragmentActivityCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        TLog.i(TAG, "onFragmentActivityCreated: $f")
    }

    override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
        TLog.i(TAG, "onFragmentViewCreated: $f")
        (f as? FragmentModularPage)?.modular?.onFragmentViewCreated(f, v)
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        TLog.i(TAG, "onFragmentStarted: $f")
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        TLog.i(TAG, "onFragmentResumed: $f")
        (f as? FragmentModularPage)?.modular?.onFragmentResume()
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        TLog.i(TAG, "onFragmentPaused: $f")
        (f as? FragmentModularPage)?.modular?.onFragmentPause()
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        TLog.i(TAG, "onFragmentStopped: $f")
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
        TLog.i(TAG, "onFragmentSaveInstanceState: $f")
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        TLog.i(TAG, "onFragmentViewDestroyed: $f")
        (f as? FragmentModularPage)?.modular?.onFragmentViewDestroyed()
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        TLog.i(TAG, "onFragmentDestroyed: $f")
        (f as? FragmentModularPage)?.let {
            it.modular.onFragmentDestroyed()
            IFragmentModularPage.modulesMap.remove(it)?.clear()
        }
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        TLog.i(TAG, "onFragmentDetached: $f")
    }
}