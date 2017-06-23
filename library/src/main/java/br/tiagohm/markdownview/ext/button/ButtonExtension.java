package br.tiagohm.markdownview.ext.button;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataHolder;

import br.tiagohm.markdownview.ext.button.internal.ButtonNodePostProcessor;
import br.tiagohm.markdownview.ext.button.internal.ButtonNodeRenderer;

public class ButtonExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    private ButtonExtension() {
    }

    public static Extension create() {
        return new ButtonExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.postProcessorFactory(new ButtonNodePostProcessor.Factory(parserBuilder));
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererType.equals("HTML")) {
            rendererBuilder.nodeRendererFactory(new ButtonNodeRenderer.Factory());
        }
    }
}
