package com.xy.application

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.xy.mviframework.util.ToastUtil

class MainActivity : Activity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContent {
//            MyApplicationTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                    Greeting("Android")
//                }
//            }
//        }
        setContentView(R.layout.activity_main)
        ToastUtil.showShortToast(this, "Hello")


        findViewById<Button>(R.id.trans).setOnClickListener {
            findViewById<TextView>(R.id.tv_trans).text=
                ((findViewById<EditText>(R.id.et_input).text.trim().toString().toLong() * 0.3464052287581699).toInt()).toString()
        }

        findViewById<Button>(R.id.clean).setOnClickListener {
            findViewById<TextView>(R.id.tv_trans).text=""
            findViewById<EditText>(R.id.et_input).text.clear()

        }

        findViewById<EditText>(R.id.et_input).addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 文本改变之前的操作，这里可以根据需要实现
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 文本正在改变时的操作，可以在这里处理实时变化的逻辑
                println("Text changed: $s")
                if(s.toString().isEmpty()) return
                findViewById<TextView>(R.id.tv_trans).text=
                    ((s.toString().toLong() * 0.3464052287581699).toInt()).toString()
            }

            override fun afterTextChanged(s: Editable?) {
                // 文本改变之后的操作，这里也可以根据需要实现
            }
        })


    }
}


fun trans(){

}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
////    Text(
////        text = "Hello $name!",
////        modifier = modifier
////    )
//    EditText(this, modifier = modifier)
//}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MyApplicationTheme {
//        Greeting("Android")
//    }
//}