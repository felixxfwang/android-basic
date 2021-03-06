package org.tiramisu.log.xlog

import android.app.Application
import com.google.auto.service.AutoService
import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.Xlog
import org.tiramisu.log.BuildConfig
import org.tiramisu.log.ILogger
import java.io.File

/**
 * @author felixxfwang
 */
@AutoService(ILogger::class)
class TiramisuXLogger: ILogger {

    override fun initialize(application: Application) {
        System.loadLibrary("c++_shared")
        System.loadLibrary("marsxlog")

        val logPath = File(application.getExternalFilesDir("tiramisu"), "xlog").absolutePath

        // this is necessary, or may crash for SIGBUS
        val cachePath = File(application.filesDir, "xlog").absolutePath

        //init xlog
        val logConfig = Xlog.XLogConfig()
        logConfig.mode = Xlog.AppednerModeAsync
        logConfig.logdir = logPath
        logConfig.nameprefix = "tiramisu"
        logConfig.compressmode = Xlog.ZLIB_MODE
        logConfig.compresslevel = 0
        logConfig.cachedir = cachePath
        logConfig.cachedays = 7
        if (BuildConfig.DEBUG) {
            logConfig.level = Xlog.LEVEL_VERBOSE
            Log.setConsoleLogOpen(true)
        } else {
            logConfig.level = Xlog.LEVEL_INFO;
            Log.setConsoleLogOpen(false)
        }

        Log.setLogImp(Xlog())
    }

    override fun v(tag: String, message: String) {
        Log.v(tag, message)
    }

    override fun i(tag: String, message: String) {
        Log.i(tag, message)
    }

    override fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun w(tag: String, message: String) {
        Log.w(tag, message)
    }

    override fun e(tag: String, message: String, tr: Throwable?) {
        Log.e(tag, message, tr)
    }
}