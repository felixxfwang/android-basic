package org.tiramisu.page.modular.activity

import org.tiramisu.page.modular.IModularPage
import java.util.*

interface IActivityModularPage : IModularPage<ActivityModuleManager> {
    companion object {
        internal val modulesMap = IdentityHashMap<IActivityModularPage, ActivityModuleManager>()
    }

    override val modular: ActivityModuleManager
        get() = modulesMap[this] ?: run {
            (onCreateModuleManager() ?: ActivityModuleManager()).also { modulesMap[this] = it }
        }
}