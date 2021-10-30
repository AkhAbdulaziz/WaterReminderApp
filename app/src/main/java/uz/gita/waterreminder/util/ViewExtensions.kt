package uz.gita.waterreminder.util

import android.app.Activity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import timber.log.Timber

fun <T : ViewBinding> T.scope(f: T.() -> Unit) {
    f(this)
}

fun timber(message: String, tag: String = "TTT") {
    Timber.tag(tag).d(message)
}

fun Activity.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this.requireContext(), message, duration).show()
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun TextView.animateBottomToTop(block: TextView.() -> Unit) {
    this.animate().setDuration(1500L)
        .translationY(-400f)
        /*  .scaleY(0f)
          .scaleX(0f)*/
        .alpha(0f)
        .withEndAction {
            this.gone()
            block(this)
        }.start()
}
