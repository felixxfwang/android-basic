package org.tiramisu.page.modular.fragment

import org.tiramisu.page.modular.IPageModuleManager

abstract class BaseFragmentModule<T: IPageModuleManager> : IFragmentModule {
    private var eventBus: T? = null

    @Suppress("UNCHECKED_CAST")
    override fun setModuleManager(manager: IPageModuleManager) {
        this.eventBus = manager as? T
    }

    protected fun bus() = eventBus

    @Suppress("UNCHECKED_CAST")
    protected fun <B> getBus(): B? = eventBus as? B
}