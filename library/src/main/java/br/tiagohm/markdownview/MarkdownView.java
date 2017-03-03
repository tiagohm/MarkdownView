package br.tiagohm.markdownview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughSubscriptExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.superscript.SuperscriptExtension;

import java.util.Arrays;

import br.tiagohm.markdownview.ext.kbd.KeystrokeExtension;
import br.tiagohm.markdownview.ext.mark.MarkExtension;
import br.tiagohm.markdownview.ext.mathjax.MathJaxExtension;

public class MarkdownView extends FrameLayout
{
    private static final Parser PARSER = Parser.builder()
            .extensions(Arrays.asList(TablesExtension.create(),
                    TaskListExtension.create(),
                    AbbreviationExtension.create(),
                    AutolinkExtension.create(),
                    MarkExtension.create(),
                    StrikethroughSubscriptExtension.create(),
                    SuperscriptExtension.create(),
                    KeystrokeExtension.create(),
                    MathJaxExtension.create()))
            .build();
    private static final HtmlRenderer.Builder RENDERER_BUILDER = HtmlRenderer.builder()
            .escapeHtml(true)
            .extensions(Arrays.asList(TablesExtension.create(),
                    TaskListExtension.create(),
                    AbbreviationExtension.create(),
                    AutolinkExtension.create(),
                    MarkExtension.create(),
                    StrikethroughSubscriptExtension.create(),
                    SuperscriptExtension.create(),
                    KeystrokeExtension.create(),
                    MathJaxExtension.create()));
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
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.getSettings().setLoadsImagesAutomatically(true);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            TypedArray attr = getContext().obtainStyledAttributes(attrs, R.styleable.MarkdownView);
            setEscapeHtml(attr.getBoolean(R.styleable.MarkdownView_escapeHtml, true));
            attr.recycle();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        addView(mWebView);
    }

    public MarkdownView setEscapeHtml(boolean flag)
    {
        RENDERER_BUILDER.escapeHtml(flag);
        return this;
    }

    public void loadMarkdown(String text, String cssPath)
    {
        Node node = PARSER.parse(text);
        HtmlRenderer renderer = RENDERER_BUILDER.build();
        String html = renderer.render(node);

        StringBuilder sb = new StringBuilder();
        html = sb.append("<html>")
                .append("<head>")
                .append("<link rel=\"stylesheet\" href=\"")
                .append(cssPath == null ? "" : cssPath)
                .append("\" />")
                .append("</head>")
                .append("<body class=\"markdown-body\">")
                .append(html)
                .append("<span id='tooltip'></span>")
                .append("<script type='text/javascript' src='file:///android_asset/js/jquery-3.1.1.min.js'></script>")
                .append("<script type='text/javascript' src='file:///android_asset/js/markdownview.js'></script>")
                .append("<script type=\"text/x-mathjax-config\"> MathJax.Hub.Config({showProcessingMessages: false, showMathMenu: false, tex2jax: {inlineMath: [['$','$']]}});</script>")
                .append("<script type=\"text/javascript\" src=\"https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS_CHTML\"></script>")
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
        String GITHUB_DARK = "file:///android_asset/css/github-dark.css";
    }
}
