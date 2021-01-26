package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.LayoutInflater.*
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_text.view.*
import kotlinx.android.synthetic.main.item_tree.view.*
import org.json.JSONObject
import android.animation.ObjectAnimator


class MainActivity : AppCompatActivity() {

    lateinit var objectAnimator: ObjectAnimator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataBean = Gson().fromJson(OpenParam.json, DataBean().javaClass)
        createView(dataBean.data, treeLayout)
    }

    private fun createView(dataList: MutableList<DataBean.Data>, linearLayout: LinearLayout) {
        for (i in 0 until dataList.size) {
            val title = dataList[i].title
            val next = dataList[i].next
            if (null != next) {
                val childLayout = from(this).inflate(R.layout.item_tree, null, false)
                childLayout.title.text = title

                childLayout.title.setOnClickListener {
                    if (childLayout.nextLayout.isGone) {
                        childLayout.nextLayout.visibility = View.VISIBLE
                        objectAnimator = ObjectAnimator.ofFloat(childLayout.flag, "rotation", 0f)
                        objectAnimator.duration = 400
                        objectAnimator.start()
                    } else {
                        childLayout.nextLayout.visibility = View.GONE
                        objectAnimator = ObjectAnimator.ofFloat(childLayout.flag, "rotation", -90f)
                        objectAnimator.duration = 400
                        objectAnimator.start()
                    }
                }
                createView(next, childLayout.nextLayout)
                linearLayout.addView(childLayout)
            } else {
                val textLayout = from(this).inflate(R.layout.item_text, null, false)
                textLayout.info.text = title
                linearLayout.addView(textLayout)
            }
        }
    }
}