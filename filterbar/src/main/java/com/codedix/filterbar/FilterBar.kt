package com.codedix.filterbar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.ContextThemeWrapper
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.codedix.filterbar.ViewUtils.Companion.toDp

@SuppressLint("ViewConstructor")
class FilterBar(private val mContext: Context, private val isExclusive: Boolean, choicesList: List<String>, private var activeList: ArrayList<String>, titleText: String) :
    LinearLayout(mContext, null, R.style.FilterBar) {

    var filterUpdated: ((ArrayList<String>) -> Unit)? = null
    var filterItems: ArrayList<FilterItem> = ArrayList()

    init {
        val container = LinearLayout(ContextThemeWrapper(mContext, R.style.FilterBar_Container))

        val title = TextView(ContextThemeWrapper(mContext, R.style.FilterBar_Title))
        title.text = titleText
        container.addView(title)

        val content = LinearLayout(ContextThemeWrapper(mContext, R.style.FilterBar_Content))
        for(c in choicesList) {
            val f = FilterItem(c)
            content.addView(f)
            val l = LayoutParams(LayoutParams.WRAP_CONTENT, 35.toDp())
            l.marginEnd = 10.toDp()
            f.layoutParams = l

            filterItems.add(f)
        }
        container.addView(content)
        content.layoutParams.width = MATCH_PARENT

        addView(container)
    }

    private fun notifyDataChanges() {
        for(f in filterItems) f.updateActive()
    }

    inner class FilterItem(c: String) : RelativeLayout(mContext, null, R.style.FilterBar_ItemContainer) {

        private var textView: TextView = TextView(ContextThemeWrapper(mContext, R.style.FilterBar_ItemText))

        init {
            textView.text = c

            updateActive()

            addView(textView)
            val l = LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)
            l.addRule(CENTER_IN_PARENT)
            textView.layoutParams = l

            setOnClickListener {
                if(!activeList.contains(c) && isExclusive) {
                    activeList.clear()
                    activeList.add(c)
                } else if(!isExclusive){
                    if(activeList.contains(c)) activeList.remove(c)
                    else activeList.add(c)
                }

                filterUpdated?.invoke(activeList)
                notifyDataChanges()
            }
        }

        fun updateActive() {
            if(activeList.contains(textView.text)) {
                setBackgroundResource(R.drawable.button_filter_selected)
                textView.setTextColor(Color.WHITE)
                //textView.setTypeface(null, Typeface.BOLD)
            } else {
                setBackgroundResource(R.drawable.button_filter_unselected)
                textView.setTextColor(resources.getColor(android.R.color.tab_indicator_text))
                //textView.setTypeface(null, Typeface.NORMAL)
            }
        }
    }
}

