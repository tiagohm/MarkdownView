package br.tiagohm.markdownview.css;

import java.io.File;
import java.net.URL;

public class ExternalStyleSheet implements StyleSheet
{
    private String mUrl;

    public ExternalStyleSheet(String url)
    {
        mUrl = url;
    }

    public static ExternalStyleSheet fromUrl(URL url)
    {
        return new ExternalStyleSheet(url.toString());
    }

    public static ExternalStyleSheet fromFile(File file)
    {
        return new ExternalStyleSheet(file.getAbsolutePath());
    }

    public static StyleSheet fromAsset(String path)
    {
        return new ExternalStyleSheet("file:///android_asset/" + path);
    }

    public String getUrl()
    {
        return mUrl;
    }

    @Override
    public String toString()
    {
        return getUrl();
    }

    @Override
    public String toHTML()
    {
        return "<link rel=\"stylesheet\" type=\"text/css\" href=\"" + getUrl() + "\" />";
    }
}
