# webview-changing-colors-bug
Demonstration of calling requestFocusFromTouch() in WebView is changing colors in Android app


![Screenshot_20211015-120734_WebView Colors Bug](https://user-images.githubusercontent.com/43139081/137471467-d270487d-7745-4794-9bb4-6aa34e24ab28.jpg)

This is the problematic code:
```
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
```
