package com.hiking.zero.flutter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.hiking.zero.R
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor


class TestFlutter1Activity : AppCompatActivity() {


    companion object {

        fun start(context: Context) {
            val intent = Intent(context, TestFlutter1Activity::class.java)
            if (context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }

    }

    private val flutterEngineId = "flutterEngineId"
    private var engine: FlutterEngine? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("flutter测试页面")
        setContentView(R.layout.ddyy_test_flutter_1)

        engine = FlutterEngineCache.getInstance().get(flutterEngineId)
        if (engine == null) {
            engine = FlutterEngine(this)
            engine?.dartExecutor?.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
            FlutterEngineCache.getInstance().put(flutterEngineId, engine)
        }

        //默认加载flutter的main.dart文件   setInitialRoute 修改初始界面
        engine?.navigationChannel?.setInitialRoute("hhff");

        val view = createFlutterView()
        view.attachToFlutterEngine(engine!!)
    }

    private fun createFlutterView(): FlutterView {
        val flutterView = FlutterView(this)
        val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        addContentView(flutterView, params)
        return flutterView
    }

    override fun onResume() {
        super.onResume()
        engine?.lifecycleChannel?.appIsResumed()
    }

    override fun onPause() {
        super.onPause()
        engine?.lifecycleChannel?.appIsInactive()
    }

    override fun onStop() {
        super.onStop()
        engine?.lifecycleChannel?.appIsPaused()
    }
}