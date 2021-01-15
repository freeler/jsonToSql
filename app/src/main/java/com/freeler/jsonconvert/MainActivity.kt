package com.freeler.jsonconvert

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import org.litepal.LitePal
import org.litepal.extension.deleteAll
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private lateinit var mTvJson: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermission()
    }

    private fun requestPermission() {
        requestPermission(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) { permit ->
            if (permit) initView() else Toast.makeText(this, "请授予相关权限", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initView() {
        mTvJson = findViewById(R.id.mTvJson)
        findViewById<Button>(R.id.mBtnPick).setOnClickListener { pickKml() }
        findViewById<Button>(R.id.mBtnOutput).setOnClickListener { convertSql() }
    }


    /**
     * 选取KML文件导入
     */
    private fun pickKml() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startForResult(intent) { resultCode, data ->
            if (resultCode == RESULT_OK) {
                val jsonStr = ConvertHelper.parseXmlWithDom4j(this, data)
                mTvJson.text = jsonStr
            }
        }
    }

    private fun convertSql() {
        val info = mTvJson.text.toString()
        if (info.isEmpty()) return
        val list = Gson().toList<TikuBean>(info)

        val tikuNetList = arrayListOf<TikuNet>()
        val tikuList = arrayListOf<Tiku>()

        list.forEach {
            if (it.answer.contains("A") ||
                it.answer.contains("B") ||
                it.answer.contains("C") ||
                it.answer.contains("D") ||
                it.excludes == "A" ||
                it.excludes == "B" ||
                it.excludes == "C" ||
                it.excludes == "D"
            ) {
                tikuNetList.add(TikuNet().apply {
                    question = it.content
                    answer = it.answer
                })
            } else {
                tikuList.add(Tiku().apply {
                    question = it.content
                    answer = it.answer
                })
            }
        }


        thread {
            LitePal.deleteAll<TikuNet>()
            LitePal.saveAll(tikuNetList)
            runOnUiThread {
                Toast.makeText(this, "TikuNet转换成功，共${tikuNetList.size}条", Toast.LENGTH_SHORT).show()
            }
        }

        thread {
            LitePal.deleteAll<Tiku>()
            LitePal.saveAll(tikuList)
            runOnUiThread {
                Toast.makeText(this, "Tiku转换成功，共${tikuList.size}条", Toast.LENGTH_SHORT).show()
            }
        }

    }


}