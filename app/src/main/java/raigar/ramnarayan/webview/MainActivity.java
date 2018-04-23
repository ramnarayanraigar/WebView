package raigar.ramnarayan.webview;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String WEB_URL = "https://play.google.com/store/apps/details?id=raigar.ramnarayan.getname";
    // declaring views
    private WebView mWebView;
    // context declaration
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_main);

        // context initialization
        mContext = this;
        initViews();
        // check internet connection
        if (isNetworkConnected(mContext)) {
            webViewFun();
            // renderWebPage(WEB_URL);
        } else {
            Toast.makeText(this, "Internet not available", Toast.LENGTH_LONG).show();
        }

    }

    // view initializations
    private void initViews() {
        mWebView = findViewById(R.id.webView);
    }

    // Hide status bar and call it before setContentView method
    private void hideStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.mWebView.canGoBack()) {
            this.mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, keyEvent);
    }

    // check internet connection is available or not
    private boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return networkInfo != null && networkInfo.isConnected();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void webViewFun() {
        //mProgressBar.setVisibility(View.VISIBLE);
        final ProgressDialog progressBar = ProgressDialog.show(mContext, "Loading web page", "Loading...");
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        mWebView.getSettings().setBuiltInZoomControls(true);
        //mWebView.getSettings().setDisplayZoomControls(false);
       /* mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(true);*/


        // mWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);

       /* mWebView.setInitialScale(1);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);*/

        // mWebView.getSettings().setBuiltInZoomControls(true);
        // mWebView.getSettings().getBuiltInZoomControls();
        // mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.v(TAG, "Processing web view url click...");
                view.loadUrl(url);
                mWebView.getSettings().setBuiltInZoomControls(true);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.v(TAG, "Finished loading URL: " + url);
                if (progressBar != null && progressBar.isShowing()) {
                    progressBar.dismiss();
                }
                mWebView.getSettings().setBuiltInZoomControls(true);
            }

            @Override
            public void onLoadResource(WebView  view, String  url) {
                // Log.v(TAG, " New url clicked " + url);
                mWebView.getSettings().setBuiltInZoomControls(true);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "Error: " + description);
                // Toast.makeText(mContext, "Oh no! " + description, Toast.LENGTH_SHORT).show();
                alertDialog.setTitle("Error");
                alertDialog.setMessage(description);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (progressBar != null && progressBar.isShowing()) {
                            progressBar.dismiss();
                        }
                        mWebView.getSettings().setBuiltInZoomControls(true);
                    }
                });
                alertDialog.show();
            }
        });


        mWebView.loadUrl(WEB_URL);
    }

    // Custom method to render a web page
    protected void renderWebPage(String urlToRender) {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // Do something on page loading started
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // Do something when page loading finished
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {
            }
        });

        /*
            WebSettings
                Manages settings state for a WebView. When a WebView is first created, it obtains a
                set of default settings. These default settings will be returned from any getter
                call. A WebSettings object obtained from WebView.getSettings() is tied to the life
                of the WebView. If a WebView has been destroyed, any method call on WebSettings
                will throw an IllegalStateException.
        */
        // Enable the javascript dsfgfdg sdf ds
        mWebView.getSettings().setJavaScriptEnabled(true);

        /*
            public abstract void setSupportZoom (boolean support)
                Sets whether the WebView should support zooming using its on-screen zoom controls
                and gestures. The particular zoom mechanisms that should be used can be set with
                setBuiltInZoomControls(boolean). This setting does not affect zooming performed
                using the zoomIn() and zoomOut() methods. The default is true.

            Parameters
                support : whether the WebView should support zoom

        */
        mWebView.getSettings().setSupportZoom(true);

        /*
            public abstract void setBuiltInZoomControls (boolean enabled)
                Sets whether the WebView should use its built-in zoom mechanisms. The built-in zoom
                mechanisms comprise on-screen zoom controls, which are displayed over the WebView's
                content, and the use of a pinch gesture to control zooming. Whether or not these
                on-screen controls are displayed can be set with setDisplayZoomControls(boolean).
                The default is false.

                The built-in mechanisms are the only currently supported zoom mechanisms, so it is
                recommended that this setting is always enabled.

            Parameters
                enabled : whether the WebView should use its built-in zoom mechanisms
        */
        mWebView.getSettings().setBuiltInZoomControls(true);

        /*
            public abstract void setDisplayZoomControls (boolean enabled)
                Sets whether the WebView should display on-screen zoom controls when using the
                built-in zoom mechanisms. The default is true.

            Parameters
                enabled : whether the WebView should display on-screen zoom controls
        */
        mWebView.getSettings().setDisplayZoomControls(true);

        // Render the web page
        mWebView.loadUrl(urlToRender);
    }
}
