package org.tiramisu.page.modular

import androidx.annotation.CallSuper

/**
 *
 * @author felixxfwang
 * @date   2019-12-10
 */
abstract class AbstractModuleManager : IPageModuleManager {

    @CallSuper
    override fun addModule(module: IPageModule) {
        module.setModuleManager(this)
    }
}
