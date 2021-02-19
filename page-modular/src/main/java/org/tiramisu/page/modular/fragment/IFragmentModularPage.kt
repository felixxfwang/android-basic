package org.tiramisu.page.modular.fragment

import org.tiramisu.page.modular.IModularPage
import org.tiramisu.page.modular.visibility.VisibilityChangedListener
import java.util.*

interface IFragmentModularPage : IModularPage<FragmentModuleManager>, VisibilityChangedListener {

    companion object {
        internal val modulesMap = IdentityHashMap<IFragmentModularPage, FragmentModuleManager>()
    }

    override val modular: FragmentModuleManager
        get() = modulesMap[this] ?: run {
            (onCreateModuleManager() ?: FragmentModuleManager()).also {
                modulesMap[this] = it
                it.setVisibilityChangedListener(this)
            }
        }

    fun isFragmentVisible(): Boolean = modular.isFragmentVisible()
}