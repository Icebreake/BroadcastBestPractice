package com.example.broadcastbestpractice

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //记住账户和密码
        //获取了SharedPreferences对象
        val prefs = getPreferences(Context.MODE_PRIVATE)
        //然后调用getBoolean()方法去获取remember_password这个键对应的值
        //一开始当然不存在对应的值了，所以会使用默认值false，这样就什么都不会发生
        val isRemember = prefs.getBoolean("remember_password", false)
        if (isRemember) {
            //将账户和密码都设置到文本框中
            val account = prefs.getString("account", "")
            val password = prefs.getString("password", "")
            accountEdit.setText(account)
            passwordEdit.setText(password)
            rememberPass.isChecked = true
        }

        //用户登录
        login.setOnClickListener {
            val account = accountEdit.text.toString()
            val password = passwordEdit.text.toString()
            //如果账户是admin且密码是123456，就认为登录成功
            if (account == "admin" && password == "123456") {
                val editor = prefs.edit()
                //检查复选框是否被选中，如果被选中，表示用户想要记住密码
                if (rememberPass.isChecked) {
                    //这时要把remember_password设置为true
                    editor.putBoolean("remember_password", true)
                    //然后将account和password对应的值都存入SharedPreferences文件中并提交
                    editor.putString("account", account)
                    editor.putString("password" ,password)
                } else {
                    //如果没有被选择，就简单地调用一下clear()方法，将SharedPreferences文件中的数据全部清除掉
                    editor.clear()
                }
                editor.apply()
                //成功就跳转到MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                //否则提示用户账户或密码错误
                Toast.makeText(this, "account or password is invalid", Toast.LENGTH_SHORT).show()
            }
        }
    }

}