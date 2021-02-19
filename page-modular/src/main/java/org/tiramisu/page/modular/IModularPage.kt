package org.tiramisu.page.modular

interface IModularPage<T: IPageModuleManager> {

    val modular: T

    fun onCreateModuleManager(): T? = null

    fun addModule(module: IPageModule) {
        modular.addModule(module)
    }
}