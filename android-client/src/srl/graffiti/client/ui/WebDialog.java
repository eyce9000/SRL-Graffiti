package srl.graffiti.client.ui;

import srl.graffiti.client.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class WebDialog extends Dialog {
	WebView webView;
	public WebDialog(Context context) {
		super(context);
		this.setContentView(R.layout.webview_dialog);
		this.setCanceledOnTouchOutside(true);
		this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND); 
		this.getWindow().getAttributes().windowAnimations =R.style.WebviewDialog;
		final Dialog dialog = this;
		webView = (WebView)findViewById(R.id.web_view);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient() {
		   public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		     Toast.makeText(getOwnerActivity(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
		   }
		 });
		Button button = (Button)this.findViewById(R.id.web_view_close_button);
		button.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
	}
	public WebView getWebView(){
		return webView;
	}
}
