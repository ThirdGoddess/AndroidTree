package com.example.myapplication

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater.from
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_text.view.*
import kotlinx.android.synthetic.main.item_tree.view.*


class MainActivity : AppCompatActivity() {

    lateinit var objectAnimator: ObjectAnimator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //解析Json
        val dataBean = Gson().fromJson(OpenParam.json, DataBean().javaClass)

        //创建View
        createView(dataBean.data, treeLayout)
    }

    /**
     * 递归创建布局
     */
    private fun createView(dataList: MutableList<DataBean.Data>, linearLayout: LinearLayout) {
        for (i in 0 until dataList.size) {
            val title = dataList[i].title
            val next = dataList[i].next
            if (null != next) {
                val childLayout = from(this).inflate(R.layout.item_tree, null, false)
                childLayout.title.text = title

                //展开和关闭的点击事件
                childLayout.title.setOnClickListener {
                    if (childLayout.nextLayout.isGone) {

                        //展开
                        childLayout.nextLayout.visibility = View.VISIBLE

                        //添点展开动画
                        objectAnimator = ObjectAnimator.ofFloat(childLayout.flag, "rotation", 0f)
                        objectAnimator.duration = 400
                        objectAnimator.start()
                    } else {

                        //隐藏
                        childLayout.nextLayout.visibility = View.GONE

                        //添点关闭动画
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