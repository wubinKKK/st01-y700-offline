package com.wubinkkk.st01offline;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public final class MainActivity extends Activity {
    private static final String HOST = "offline.manual.invalid";
    private static final String HOME = "https://" + HOST + "/";
    private WebView page;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        page = new WebView(this);
        setContentView(page);
        page.getSettings().setJavaScriptEnabled(true);
        page.getSettings().setDomStorageEnabled(true);
        page.getSettings().setAllowFileAccess(false);
        page.getSettings().setAllowContentAccess(false);
        page.getSettings().setBlockNetworkLoads(true);
        page.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return !isLocal(request.getUrl());
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                if (!isLocal(uri)) return missing();
                String path = uri.getPath();
                if (path == null || path.contains("..") || path.indexOf('\\') >= 0) return missing();
                if (path.isEmpty() || "/".equals(path)) path = "/index.html";
                String asset = path.substring(1);
                try {
                    InputStream stream = getAssets().open(asset);
                    String mime = mimeType(asset);
                    return new WebResourceResponse(mime, mime.startsWith("text/") ? "UTF-8" : null, stream);
                } catch (IOException notFound) {
                    return missing();
                }
            }
        });
        if (state == null || page.restoreState(state) == null) page.loadUrl(HOME);
    }

    private static boolean isLocal(Uri uri) {
        return "https".equals(uri.getScheme()) && HOST.equals(uri.getHost()) && uri.getUserInfo() == null;
    }

    private static String mimeType(String path) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path).toLowerCase(Locale.ROOT);
        if ("js".equals(extension)) return "text/javascript";
        if ("svg".equals(extension)) return "image/svg+xml";
        String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        return mime == null ? "application/octet-stream" : mime;
    }

    private static WebResourceResponse missing() {
        return new WebResourceResponse("text/plain", "UTF-8", 404, "Not Found", null,
                new ByteArrayInputStream(new byte[0]));
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        if (page != null) page.saveState(state);
        super.onSaveInstanceState(state);
    }

    @Override
    public void onBackPressed() {
        if (page != null && page.canGoBack()) page.goBack();
        else super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (page != null) {
            page.stopLoading();
            page.destroy();
            page = null;
        }
        super.onDestroy();
    }
}
