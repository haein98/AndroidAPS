package app.aaps.plugins.automation.elements

import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.annotation.StringRes
import app.aaps.core.interfaces.resources.ResourceHelper

class ComparatorExists(private val rh: ResourceHelper, var value: Compare = Compare.EXISTS) : Element {

    enum class Compare {
        EXISTS, NOT_EXISTS;

        @get:StringRes val stringRes: Int
            get() = when (this) {
                EXISTS -> app.aaps.core.ui.R.string.exists
                NOT_EXISTS -> app.aaps.core.ui.R.string.notexists
            }

        companion object {

            fun labels(rh: ResourceHelper): List<String> {
                val list: MutableList<String> = ArrayList()
                for (c in Compare.entries) list.add(rh.gs(c.stringRes))
                return list
            }
        }
    }

    override fun addToLayout(root: LinearLayout) {
        root.addView(
            Spinner(root.context).apply {
                adapter = ArrayAdapter(root.context, app.aaps.core.ui.R.layout.spinner_centered, Compare.labels(rh)).apply {
                    setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                    setMargins(0, rh.dpToPx(4), 0, rh.dpToPx(4))
                }

                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        value = Compare.entries.toTypedArray()[position]
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
                setSelection(value.ordinal)
                gravity = Gravity.CENTER_HORIZONTAL
            })
    }
}