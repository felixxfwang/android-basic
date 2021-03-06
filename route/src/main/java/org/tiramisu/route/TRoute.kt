package org.tiramisu.route

import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter

object TRoute {

    inline fun <reified T : TRouteApi> api(): T {
        return api(T::class.java)
    }

    fun <T : TRouteApi> api(apiClazz: Class<T>): T {
        val targetClassName: String = TRouteUtil.convertApiToImplClass(apiClazz)
       return try {
            SingletonPool.get(apiClazz, targetClassName)
        } catch (e: ClassNotFoundException) {
            val emptyClassName = TRouteUtil.convertApiToEmptyImplClass(apiClazz)
            SingletonPool.get(apiClazz, emptyClassName)
        }
    }

    fun build(route: String): Postcard {
        return ARouter.getInstance().build(route)
    }
}