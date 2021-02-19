package org.tiramisu.page.modular.activity

import org.tiramisu.page.modular.IPageModuleManager

abstract class BaseActivityModule<T: IPageModuleManager> : IActivityModule {

    private var eventBus: T? = null

    @Suppress("UNCHECKED_CAST")
    override fun setModuleManager(manager: IPageModuleManager) {
        this.eventBus = manager as? T
    }

    protected fun bus() = eventBus
}