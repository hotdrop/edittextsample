package jp.hotdrop.edittextsample.component

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.widget.EditText
import com.jakewharton.rxbinding2.widget.RxTextView

class ExEditText constructor(
        context: Context,
        attributeSet: AttributeSet
): EditText(context, attributeSet) {

    override fun setError(error: CharSequence?, icon: Drawable?) {

        if (error == null) {
            super.setError(error, icon)
            setCompoundDrawables(null, null, null, null)
        }

        if (error != ONLY_ERROR_ICON) {
            super.setError(error, icon)
            return
        }

        ContextCompat.getDrawable(context, Resources.getSystem().getIdentifier("indicator_input_error", "drawable", "android"))?.let {
            it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
            setCompoundDrawables(null, null, it, null)
        } ?: setError(null, null)
    }

    companion object {
        const val ONLY_ERROR_ICON = "ONLY_ERROR_ICON"
    }
}

fun ExEditText.toObservable() =
    RxTextView.textChangeEvents(this)
