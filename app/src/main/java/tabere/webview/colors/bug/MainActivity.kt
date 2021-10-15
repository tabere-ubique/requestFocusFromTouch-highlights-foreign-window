package tabere.webview.colors.bug

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

            openWebView.setOnClickListener {
                openWebViewDialog()
            }

            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                doCallRequestFromTouch = checkedId == R.id.doRequestFromTrouch
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun openWebViewDialog() {
        val dialog = Dialog(this, R.style.Theme_WebViewColorsBug).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
        }
        WebView(this).apply {
            layoutParams = ViewGroup.LayoutParams(-1, -1)
        }.also {

            it.settings.apply {
                javaScriptEnabled = true
                setSupportZoom(true)
                builtInZoomControls = true
                displayZoomControls = false
            }

            it.webViewClient = object : WebViewClient() {

                override fun onPageFinished(view: WebView, url: String) {
                    /* The problem is with this code here. Calling requestFocusFromTouch()
                    * seems to be propagating the focusing to the MainActivity.
                    * We should not use it! */
                    if (doCallRequestFromTouch) {
                        it.requestFocusFromTouch()
                    }
                }
            }
            it.webChromeClient = WebChromeClient()

            dialog.setContentView(it)
            dialog.show()

            it.loadUrl("https://www.blank.org")

            Handler(Looper.getMainLooper()).postDelayed({
                dialog.dismiss()
            }, 1000)
        }
    }
}