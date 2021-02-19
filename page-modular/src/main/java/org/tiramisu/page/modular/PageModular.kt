package org.tiramisu.page.modular

import android.app.Application
import org.tiramisu.page.modular.activity.ModularActivityLifecycleCallbacks

object PageModular {

    fun initialize(application: Application) {
        application.registerActivityLifecycleCallbacks(ModularActivityLifecycleCallbacks)
    }
}