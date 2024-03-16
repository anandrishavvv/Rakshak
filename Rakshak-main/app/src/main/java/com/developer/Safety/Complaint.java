package com.developer.Safety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ProgressBar;


public class Complaint extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        getWindow().setStatusBarColor(ContextCompat.getColor(Complaint.this, R.color.HelplineStatusBar));


        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);


        // Enable JavaScript (if required)
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Set a WebViewClient to monitor page loading
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // Show the progress bar when a page starts loading
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // Hide the progress bar when the page finishes loading
                progressBar.setVisibility(View.GONE);
            }
        });

        // Set a WebChromeClient to handle the progress bar updates
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // Update the progress bar as the page loads
                progressBar.setProgress(newProgress);
            }
        });
        if (NetworkUtils.isNetworkAvailable(this)) {
            // Load the web page in the WebView
            webView.loadUrl("https://ncwapps.nic.in/onlinecomplaintsv2/frmPubRegistration.aspx");

        } else {
            // Show the "No Internet" dialog
            DialogUtils.showNoInternetDialog(this);
        }

    }
    public static class NetworkUtils {

        public static boolean isNetworkAvailable(Context context) {
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            }

            return false;
        }
    }

    public static class DialogUtils {

        public static void showNoInternetDialog(Context context) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("No Internet Connection");
            builder.setMessage("Please check your internet connection and try again.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
    }

}







