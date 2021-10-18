package tabere.webview.colors.bug

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import tabere.webview.colors.bug.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var doCallRequestFromTouch = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)

            openDialog.setOnClickListener {
                openDialog()
            }

            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                doCallRequestFromTouch = checkedId == R.id.doRequestFromTrouch
            }
        }
    }

    private fun openDialog() {
        val dialog = Dialog(this, R.style.Theme_WebViewColorsBug)
        View(this).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }.also {
            dialog.setContentView(it)
            dialog.show()

            Handler(Looper.getMainLooper()).apply {
                postDelayed({
                    if (doCallRequestFromTouch) {
                        it.requestFocusFromTouch()
                    }
                }, 500)
                postDelayed({
                    dialog.dismiss()
                }, 1000)
            }
        }
    }
}