package br.tiagohm.markdownview.ext.localization;

import android.content.Context;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

import br.tiagohm.markdownview.ext.localization.internal.LocalizationDelimiterProcessor;
import br.tiagohm.markdownview.ext.localization.internal.LocalizationNodeRenderer;

public class LocalizationExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    public static final DataKey<Context> LOCALIZATION_CONTEXT = new DataKey<>("LOCALIZATION_CONTEXT", (Context) null);

    private LocalizationExtension() {
    }

    public static Extension create() {
        return new LocalizationExtension();
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customDelimiterProcessor(new LocalizationDelimiterProcessor());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        switch (rendererType) {
            case "HTML":
                rendererBuilder.nodeRendererFactory(new LocalizationNodeRenderer.Factory());
                break;
        }
    }
}
