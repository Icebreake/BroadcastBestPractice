package com.example.broadcastbestpractice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    lateinit var receiver: ForceOfflineReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCollector.addActivity(this)
    }

    //注册了ForceOfflineReceiver
    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.example.broadcastbestpractice.FORCE_OFFLINE")
        receiver = ForceOfflineReceiver()
        registerReceiver(receiver, intentFilter)
    }

    //取消了ForceOfflineReceiver
    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

    inner class ForceOfflineReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            //使用AlertDialog.Builder构建一个对话框
            AlertDialog.Builder(context).apply {
                setTitle("Warning")
                setMessage("You are forced to be offline. Please try to login again.")
                //一定要调用setCancelable()方法将对话框设为不可取消，否则用户按一下Back键就可以关闭对话框继续使用程序了
                setCancelable(false)
                //给对话框注册确定按钮
                setPositiveButton("OK"){_ , _ ->
                    //销毁所有的Activity
                    ActivityCollector.finishAll()
                    val i = Intent(context, LoginActivity::class.java)
                    //重新启动LoginActivity
                    context.startActivity(i)
                }
                show()
            }
        }


    }

}