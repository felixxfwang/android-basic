package org.tiramisu.page.modular.fragment

import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import org.tiramisu.page.modular.IPageModuleManager

abstract class BaseFragmentModule<T: IPageModuleManager, B: ViewBinding, VM: ViewModel>: IFragmentModule {

    private lateinit var page: IFragmentModularPage<B, VM>

    fun initialize(page: IFragmentModularPage<B, VM>) {
        this.page = page
    }

    private val eventBus: T by lazy { page.modular as T }

    protected fun bus() = eventBus

    @Suppress("UNCHECKED_CAST")
    protected fun <B> getBus(): B? = eventBus as? B

    protected val binding by lazy { page.binding() }

    protected val vm by lazy { page.viewModel() }

    protected val fragment by lazy { page.fragment() }
}