package org.tiramisu.route;

import java.util.concurrent.ConcurrentHashMap;

public class TRouteUtil {

    private static final ConcurrentHashMap<Class<?>, String> IMPL_CLASS_NAME_HASH_MAP = new ConcurrentHashMap<>(2048); // HashMap扩容耗时,目前手Q有1000+的API,先定义2048,后续需要保证API数量是容量的0.75以下.
    private static final Object[] IMPL_CLASS_LOCK_ARRAY = new Object[10];

    private static final ConcurrentHashMap<Class<?>, String> EMPTY_IMPL_CLASS_NAME_HASH_MAP = new ConcurrentHashMap<>(2048); // HashMap扩容耗时,目前手Q有1000+的API,先定义2048,后续需要保证API数量是容量的0.75以下.
    private static final Object[] EMPTY_IMPL_CLASS_LOCK_ARRAY = new Object[10];

    static {
        for (int i = 0; i < IMPL_CLASS_LOCK_ARRAY.length; i++) {
            IMPL_CLASS_LOCK_ARRAY[i] = new Object();
        }
        for (int i = 0; i < EMPTY_IMPL_CLASS_LOCK_ARRAY.length; i++) {
            EMPTY_IMPL_CLASS_LOCK_ARRAY[i] = new Object();
        }
    }

    private TRouteUtil() {
    }

    /**
     * 获取QRoute命名规范的实现类名，构造实现用
     */
    public static String convertApiToImplClass(Class<?> clazz) {
        String cacheImplClassName = IMPL_CLASS_NAME_HASH_MAP.get(clazz);
        if (cacheImplClassName == null) {
            synchronized (IMPL_CLASS_LOCK_ARRAY[clazz.hashCode() % 10]) {
                cacheImplClassName = IMPL_CLASS_NAME_HASH_MAP.get(clazz);
                if (cacheImplClassName == null) {
                    cacheImplClassName = getImplNameInner(clazz);
                    IMPL_CLASS_NAME_HASH_MAP.put(clazz, cacheImplClassName);
                }
            }
        }
        return cacheImplClassName;
    }

    public static String convertApiToEmptyImplClass(Class<?> clazz) {
        String cacheImplClassName = EMPTY_IMPL_CLASS_NAME_HASH_MAP.get(clazz);
        if (cacheImplClassName == null) {
            synchronized (EMPTY_IMPL_CLASS_LOCK_ARRAY[clazz.hashCode() % 10]) {
                cacheImplClassName = EMPTY_IMPL_CLASS_NAME_HASH_MAP.get(clazz);
                if (cacheImplClassName == null) {
                    cacheImplClassName = getEmptyImplNameInner(clazz);
                    EMPTY_IMPL_CLASS_NAME_HASH_MAP.put(clazz, cacheImplClassName);
                }
            }
        }
        return cacheImplClassName;
    }

    private static String getImplNameInner(Class<?> clazz) {
        StringBuilder stringBuffer = new StringBuilder(clazz.getName()).append("Impl");
        final int dot = stringBuffer.lastIndexOf(".");
        if (dot > 0) {
            stringBuffer.deleteCharAt(dot + 1);
            stringBuffer.insert(dot + 1, "impl.");
            return stringBuffer.toString();
        } else {
            return "";
        }
    }

    private static String getEmptyImplNameInner(Class<?> clazz) {
        StringBuilder stringBuffer = new StringBuilder(clazz.getName()).append("EmptyImpl");
        final int dot = stringBuffer.lastIndexOf(".");
        if (dot > 0) {
            stringBuffer.deleteCharAt(dot + 1);
            stringBuffer.insert(dot + 1, "empty.");
            return stringBuffer.toString();
        } else {
            return "";
        }
    }
}
