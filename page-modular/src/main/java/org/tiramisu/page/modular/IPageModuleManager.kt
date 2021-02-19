package org.tiramisu.page.modular

/**
 *
 * @author felixxfwang
 * @date   2019-12-10
 */
interface IPageModuleManager {

    /**
     * 添加新的Module
     */
    fun addModule(module: IPageModule)

    /**
     * 清除所有的Module
     */
    fun clear()
}
