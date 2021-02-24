package org.tiramisu.page.modular.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import org.tiramisu.page.modular.IModularPage
import org.tiramisu.page.modular.IPageModule
import org.tiramisu.page.modular.visibility.VisibilityChangedListener
import java.util.*

interface IFragmentModularPage<BINDING: ViewBinding, VM: ViewModel>
    : IModularPage<FragmentModuleManager>, VisibilityChangedListener {

    companion object {
        internal val modulesMap = IdentityHashMap<IFragmentModularPage<*, *>, FragmentModuleManager>()
    }

    override val modular: FragmentModuleManager
        get() = modulesMap[this] ?: run {
            (onCreateModuleManager() ?: FragmentModuleManager()).also {
                modulesMap[this] = it
                it.setVisibilityChangedListener(this)
            }
        }

    fun binding(): BINDING

    fun viewModel(): VM

    fun fragment(): Fragment

    override fun addModule(module: IPageModule) {
        super.addModule(module)
        (module as? BaseFragmentModule<*, BINDING, VM>)?.initialize(this)
    }

    fun isFragmentVisible(): Boolean = modular.isFragmentVisible()
}