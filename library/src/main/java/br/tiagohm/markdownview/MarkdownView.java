package br.tiagohm.markdownview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.orhanobut.logger.Logger;
import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ast.Image;
import com.vladsch.flexmark.ast.util.TextCollectingVisitor;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughSubscriptExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.LinkType;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.superscript.SuperscriptExtension;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import br.tiagohm.markdownview.css.StyleSheet;
import br.tiagohm.markdownview.ext.emoji.EmojiExtension;
import br.tiagohm.markdownview.ext.kbd.KeystrokeExtension;
import br.tiagohm.markdownview.ext.mark.MarkExtension;
import br.tiagohm.markdownview.ext.mathjax.MathJaxExtension;

public class MarkdownView extends FrameLayout
{
    private final static List<Extension> EXTENSIONS = Arrays.asList(TablesExtension.create(),
            TaskListExtension.create(),
            AbbreviationExtension.create(),
            AutolinkExtension.create(),
            MarkExtension.create(),
            StrikethroughSubscriptExtension.create(),
            SuperscriptExtension.create(),
            KeystrokeExtension.create(),
            MathJaxExtension.create(),
            FootnoteExtension.create(),
            EmojiExtension.create());

    private final DataHolder OPTIONS = new MutableDataSet()
            .set(FootnoteExtension.FOOTNOTE_REF_PREFIX, "[")
            .set(FootnoteExtension.FOOTNOTE_REF_SUFFIX, "]")
            .set(HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_PREFIX, "")
            .set(HtmlRenderer.FENCED_CODE_NO_LANGUAGE_CLASS, "nohighlight")
            //.set(FootnoteExtension.FOOTNOTE_BACK_REF_STRING, "&#8593")
            ;

    private final List<StyleSheet> mStyleSheets = new LinkedList<>();
    private WebView mWebView;
    private boolean mEscapeHtml = true;

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
            mEscapeHtml = attr.getBoolean(R.styleable.MarkdownView_escapeHtml, true);
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
        mEscapeHtml = flag;
        return this;
    }

    public MarkdownView setEmojiRootPath(String path)
    {
        ((MutableDataHolder)OPTIONS).set(EmojiExtension.ROOT_IMAGE_PATH, path);
        return this;
    }

    public MarkdownView setEmojiImageExtension(String ext)
    {
        ((MutableDataHolder)OPTIONS).set(EmojiExtension.IMAGE_EXT, ext);
        return this;
    }

    public MarkdownView addStyleSheet(StyleSheet s)
    {
        if(s != null)
        {
            mStyleSheets.add(s);
        }

        return this;
    }

    public MarkdownView replaceStyleSheet(StyleSheet oldStyle, StyleSheet newStyle)
    {
        if(oldStyle == newStyle)
        {
        }
        else if(newStyle == null)
        {
            mStyleSheets.remove(oldStyle);
        }
        else
        {
            final int index = mStyleSheets.indexOf(oldStyle);

            if(index >= 0)
            {
                mStyleSheets.set(index, newStyle);
            }
            else
            {
                mStyleSheets.add(newStyle);
            }
        }

        return this;
    }

    public MarkdownView removeStyleSheet(StyleSheet s)
    {
        mStyleSheets.remove(s);
        return this;
    }

    private String parseBuildAndRender(String text)
    {
        Parser parser = Parser.builder(OPTIONS)
                .extensions(EXTENSIONS)
                .build();

        HtmlRenderer renderer = HtmlRenderer.builder(OPTIONS)
                .escapeHtml(mEscapeHtml)
                .nodeRendererFactory(new NodeRendererFactoryImpl())
                .extensions(EXTENSIONS)
                .build();

        return renderer.render(parser.parse(text));
    }

    public void loadMarkdown(String text)
    {
        String html = parseBuildAndRender(text);

        StringBuilder sb = new StringBuilder();
        sb.append("<html>\n");
        sb.append("<head>\n");
        for(StyleSheet s : mStyleSheets)
        {
            sb.append(s.toHTML());
        }
        sb.append("</head>\n");
        sb.append("<body>\n")
                .append("<div class=\"container\">\n")
                .append(html)
                .append("</div>\n")
                .append("<span id='tooltip'></span>\n")
                .append("<script type='text/javascript' src='file:///android_asset/js/jquery-3.1.1.min.js'></script>\n")
                .append("<script type='text/javascript' src='file:///android_asset/js/markdownview.js'></script>\n")
                .append("<script type='text/javascript' src='file:///android_asset/js/highlight.js'></script>\n")
                .append("<script>hljs.initHighlightingOnLoad();</script>")
                .append("<script type=\"text/x-mathjax-config\"> MathJax.Hub.Config({showProcessingMessages: false, messageStyle: 'none', showMathMenu: false, tex2jax: {inlineMath: [['$','$']]}});</script>\n")
                .append("<script type=\"text/javascript\" src=\"https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS_CHTML\"></script>\n")
                .append("</body>\n")
                .append("</html>");

        html = sb.toString();

        Logger.d(html);

        mWebView.loadDataWithBaseURL("",
                html,
                "text/html",
                "UTF-8",
                "");
    }

    public void loadMarkdownFromAsset(String path)
    {
        loadMarkdown(Utils.getStringFromAssetFile(getContext().getAssets(), path));
    }

    public void loadMarkdownFromFile(File file)
    {
        loadMarkdown(Utils.getStringFromFile(file));
    }

    public void loadMarkdownFromUrl(String url)
    {
        new LoadMarkdownUrlTask().execute(url);
    }

    public static class NodeRendererFactoryImpl implements NodeRendererFactory
    {
        @Override
        public NodeRenderer create(DataHolder options)
        {
            return new NodeRenderer()
            {
                @Override
                public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers()
                {
                    HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
                    set.add(new NodeRenderingHandler<>(Image.class, new CustomNodeRenderer<Image>()
                    {
                        @Override
                        public void render(Image node, NodeRendererContext context, HtmlWriter html)
                        {
                            if(!context.isDoNotRenderLinks())
                            {
                                String altText = new TextCollectingVisitor().collectAndGetText(node);

                                ResolvedLink resolvedLink = context.resolveLink(LinkType.IMAGE, node.getUrl().unescape(), null);
                                String url = resolvedLink.getUrl();

                                if(!node.getUrlContent().isEmpty())
                                {
                                    // reverse URL encoding of =, &
                                    String content = Escaping.percentEncodeUrl(node.getUrlContent()).replace("+", "%2B").replace("%3D", "=").replace("%26", "&amp;");
                                    url += content;
                                }

                                final int index = url.indexOf('@');

                                if(index >= 0)
                                {
                                    String[] dimensions = url.substring(index + 1, url.length()).split("\\|");
                                    url = url.substring(0, index);

                                    if(dimensions.length == 2)
                                    {
                                        String width = TextUtils.isEmpty(dimensions[0]) ? "auto" : dimensions[0];
                                        String height = TextUtils.isEmpty(dimensions[1]) ? "auto" : dimensions[1];
                                        html.attr("style", "width: " + width + "; height: " + height);
                                    }
                                }

                                html.attr("src", url);
                                html.attr("alt", altText);

                                if(node.getTitle().isNotNull())
                                {
                                    html.attr("title", node.getTitle().unescape());
                                }

                                html.srcPos(node.getChars()).withAttr(resolvedLink).tagVoid("img");
                            }
                        }
                    }));
                    return set;
                }
            };
        }


    }

    private class LoadMarkdownUrlTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            String url = params[0];
            InputStream is = null;
            try
            {
                URLConnection connection = new URL(url).openConnection();
                connection.setReadTimeout(5000);
                connection.setConnectTimeout(5000);
                connection.setRequestProperty("Accept-Charset", "UTF-8");
                return Utils.getStringFromInputStream(is = connection.getInputStream());
            }
            catch(Exception e)
            {
                e.printStackTrace();
                return "";
            }
            finally
            {
                if(is != null)
                {
                    try
                    {
                        is.close();
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String s)
        {
            loadMarkdown(s);
        }
    }
}
