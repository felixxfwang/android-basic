package org.tiramisu.page.modular.activity

import android.content.Intent
import android.os.Bundle
import org.tiramisu.page.modular.IPageModule

interface IActivityModule : IPageModule {

    fun onCreate(savedInstanceState: Bundle?) {}

    fun onStart() {}

    fun onResume() {}

    fun onPause() {}

    fun onStop() {}

    fun onDestroy() {}

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}
}