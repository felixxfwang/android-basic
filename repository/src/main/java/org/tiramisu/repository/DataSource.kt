package org.tiramisu.repository

/**
 *
 * @author felixxfwang
 * @date   2019-09-09
 */
interface DataSource<P, D> {
    /**
     * 加载数据
     */
    fun loadData(param: P, callback: LoadDataCallback<P, D>?)
    fun isLoading(): Boolean
    fun cancel()
}

/**
 * 数据请求回调，可选方法实现
 */
interface LoadCallback<P, T>

/**
 * 数据刷新回调
 */
interface LoadDataCallback<P, T> : LoadCallback<P, T> {

    /**
     * 开始首次刷新
     */
    fun onLoadDataStart(param: P) {}

    /**
     * 首次刷新成功
     */
    fun onLoadDataSuccess(param: P, data: T)

    /**
     * 首次刷新失败
     */
    fun onLoadDataFailed(param: P, errorCode: Int, errorMsg: String?) {}

    /**
     * 首次刷新完成（成功和失败都会回调）
     */
    fun onLoadDataComplete(param: P) {}
}