package org.tiramisu.route;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Impl类单例管理池,不允许外部使用
 */
class SingletonPool {

    private static final String TAG = "SingletonPool";
    private static final ConcurrentHashMap<Class<?>, Object> SINGLETON_POOL = new ConcurrentHashMap<>(512);

    private SingletonPool() {
    }

    /**
     * 传入api类和转换后的impl类名，获取单例
     *
     * @param apiClazz api类
     * @param implName impl类名
     * @param <T> impl实例
     */
    @NonNull
    static <T> T get(Class<T> apiClazz, String implName) throws ClassNotFoundException {
        if (apiClazz == null || implName == null || implName.length() == 0) {
            throw new IllegalStateException("args null! ");
        }
        try {
            Object instance = getInstance(apiClazz, implName);
            if (null == instance) {
                throw new IllegalStateException("getInstance null! @" + implName);
            }
            return (T) instance;
        } catch (ClassNotFoundException e) {
            throw e;
        } catch (Exception e) {
            String errorInfo = "build API fatal:" + apiClazz.getSimpleName() + " " + e.toString();
            throw new IllegalStateException(errorInfo, e);
        }
    }

    @Nullable
    private static Object getInstance(Class<?> apiClazz, String implName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Object t = SINGLETON_POOL.get(apiClazz);
        if (t == null) {
            synchronized (SINGLETON_POOL) {
                t = SINGLETON_POOL.get(apiClazz);
                if (t == null) {
                    Class<?> implClazz = Class.forName(implName);
                    t = implClazz.newInstance();
                    SINGLETON_POOL.put(apiClazz, t);
                }
            }
        }
        return t;
    }
}
