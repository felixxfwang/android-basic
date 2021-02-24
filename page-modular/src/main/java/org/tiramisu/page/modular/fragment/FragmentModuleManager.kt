package org.tiramisu.page.modular.fragment

import android.view.View
import androidx.fragment.app.Fragment
import org.tiramisu.page.modular.AbstractModuleManager
import org.tiramisu.page.modular.IPageModule
import org.tiramisu.page.modular.visibility.FragmentVisibilityModule
import org.tiramisu.page.modular.visibility.VisibilityChangedListener

open class FragmentModuleManager : AbstractModuleManager(),
    IFragmentModule, VisibilityChangedListener {

    val modules by lazy {
        ArrayList<IFragmentModule>().also { it.add(visibilityModule) }
    }

    private var visibilityChangedListener: VisibilityChangedListener? = null

    private val visibilityModule by lazy {
        FragmentVisibilityModule().also { it.setVisibilityChangedListener(this) }
    }

    fun setVisibilityChangedListener(listener: VisibilityChangedListener) {
        this.visibilityChangedListener = listener
    }

    fun isFragmentVisible(): Boolean = visibilityModule.isFragmentVisible()

    override fun onVisibilityChanged(isVisible: Boolean) {
        visibilityChangedListener?.onVisibilityChanged(isVisible)
        if (isVisible) {
            modules.forEach { it.onFragmentShow() }
        } else {
            modules.forEach { it.onFragmentHide() }
        }
    }

    override fun addModule(module: IPageModule) {
        if (module is IFragmentModule) {
            modules.add(module)
        }
    }

    override fun clear() {
        modules.clear()
    }

    inline fun <reified T: IFragmentModule> getModule(): T? {
        return modules.firstOrNull { it is T } as? T
    }

    override fun onFragmentCreated() {
        modules.forEach { it.onFragmentCreated() }
    }

    override fun onFragmentViewPreCreate(fragment: Fragment): View? {
        var intercepted: View? = null
        modules.forEach {
            val view = it.onFragmentViewPreCreate(fragment)
            if (view != null) intercepted = view
        }
        return intercepted
    }

    override fun onFragmentViewCreated(fragment: Fragment, view: View) {
        modules.forEach { it.onFragmentViewCreated(fragment, view) }
    }

    override fun onFragmentViewRefresh() {
        modules.forEach { it.onFragmentViewRefresh() }
    }

    override fun onFragmentViewReused() {
        modules.forEach { it.onFragmentViewReused() }
    }

    override fun onFragmentShow() {
        modules.forEach { it.onFragmentShow() }
    }

    override fun onFragmentHide() {
        modules.forEach { it.onFragmentHide() }
    }

    override fun onFragmentResume() {
        modules.forEach { it.onFragmentResume() }
    }

    override fun onFragmentPause() {
        modules.forEach { it.onFragmentPause() }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        modules.forEach { it.onHiddenChanged(hidden) }
    }

    override fun onSetUserVisibleHint(visible: Boolean) {
        modules.forEach { it.onSetUserVisibleHint(visible) }
    }

    override fun onFragmentViewDestroyed() {
        modules.forEach { it.onFragmentViewDestroyed() }
    }

    override fun onFragmentDestroyed() {
        modules.forEach { it.onFragmentDestroyed() }
    }
}