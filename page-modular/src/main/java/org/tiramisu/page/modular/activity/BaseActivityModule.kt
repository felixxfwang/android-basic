package org.tiramisu.page.modular.activity

import org.tiramisu.page.modular.IPageModuleManager

abstract class BaseActivityModule<T: IPageModuleManager> : IActivityModule {

    private var eventBus: T? = null

    protected fun bus() = eventBus
}