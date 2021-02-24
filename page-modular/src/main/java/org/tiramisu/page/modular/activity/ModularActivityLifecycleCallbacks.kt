package org.tiramisu.page.modular.activity

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import org.tiramisu.log.TLog
import org.tiramisu.page.modular.fragment.ModularFragmentLifecycleCallback

object ModularActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    private const val TAG = "ModularActivityLifecycleCallbacks"

    override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
        TLog.i(TAG, "onActivityPreCreated")
        (activity as? FragmentActivity)?.supportFragmentManager
            ?.registerFragmentLifecycleCallbacks(ModularFragmentLifecycleCallback, true)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        TLog.i(TAG, "onActivityCreated")
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            onActivityPreCreated(activity, savedInstanceState)
        }
        (activity as? IActivityModularPage)?.modular?.onCreate(savedInstanceState)
    }

    override fun onActivityStarted(activity: Activity) {
        TLog.i(TAG, "onActivityStarted")
        (activity as? IActivityModularPage)?.modular?.onStart()
    }

    override fun onActivityResumed(activity: Activity) {
        (activity as? IActivityModularPage)?.modular?.onResume()
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityPaused(activity: Activity) {
        (activity as? IActivityModularPage)?.modular?.onPause()
    }

    override fun onActivityStopped(activity: Activity) {
        (activity as? IActivityModularPage)?.modular?.onStop()
    }

    override fun onActivityDestroyed(activity: Activity) {
        (activity as? IActivityModularPage)?.modular?.onDestroy()
    }

    override fun onActivityPostDestroyed(activity: Activity) {
        (activity as? FragmentActivity)?.supportFragmentManager
            ?.unregisterFragmentLifecycleCallbacks(ModularFragmentLifecycleCallback)
    }
}