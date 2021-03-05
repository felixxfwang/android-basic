package org.tiramisu.route

object TRoute {

    inline fun <reified T : TRouteApi> api(): T {
        return api(T::class.java)
    }

    fun <T : TRouteApi> api(apiClazz: Class<T>): T {
        val targetClassName: String = TRouteUtil.convertApiToImplClass(apiClazz)
        return SingletonPool.get(apiClazz, targetClassName)
    }
}