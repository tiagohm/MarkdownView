package br.tiagohm.markdown;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;

import java.util.Arrays;

public class MarkdownView extends FrameLayout
{
    private static final Parser PARSER = Parser.builder()
            .extensions(Arrays.asList(TablesExtension.create(),
                    StrikethroughExtension.create(),
                    TaskListExtension.create(),
                    AutolinkExtension.create()))
            .build();
    private static final HtmlRenderer RENDERER = HtmlRenderer.builder()
            .escapeHtml(true)
            .extensions(Arrays.asList(TablesExtension.create(),
                    StrikethroughExtension.create(),
                    TaskListExtension.create(),
                    AutolinkExtension.create()))
            .build();
    private WebView mWebView;

    public MarkdownView(Context context)
    {
        this(context, null);
    }

    public MarkdownView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public MarkdownView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        mWebView = new WebView(context, null, 0);
        mWebView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        try
        {
            mWebView.getSettings().setLoadsImagesAutomatically(true);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        addView(mWebView);
    }

    public void loadMarkdown(String text, String cssPath)
    {
        Node node = PARSER.parse(text);
        String html = RENDERER.render(node);

        StringBuilder sb = new StringBuilder();
        html = sb.append("<html>")
                .append("<head>")
                .append("<link rel=\"stylesheet\" href=\"")
                .append(cssPath == null ? "" : cssPath)
                .append("\" />")
                .append("</head>")
                .append("<body class=\"markdown-body\">")
                .append(html)
                .append("</body>")
                .append("</html>").toString();

        Log.d("TAG", html);

        mWebView.loadDataWithBaseURL("",
                html,
                "text/html",
                "UTF-8",
                "");
    }

    public void loadMarkdownFromAsset(String path, String cssPath)
    {
        loadMarkdown(Utils.getStringFromAssetFile(getContext().getAssets(), path), cssPath);
    }

    public interface Styles
    {
        String GITHUB = "file:///android_asset/css/github.css";
    }
}
