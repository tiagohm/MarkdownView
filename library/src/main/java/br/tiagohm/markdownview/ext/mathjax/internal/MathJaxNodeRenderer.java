package br.tiagohm.markdownview.ext.mathjax.internal;

import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.HashSet;
import java.util.Set;

import br.tiagohm.markdownview.ext.mathjax.MathJax;

public class MathJaxNodeRenderer implements NodeRenderer
{

    public MathJaxNodeRenderer(DataHolder options)
    {
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers()
    {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(MathJax.class, new CustomNodeRenderer<MathJax>()
        {
            @Override
            public void render(MathJax node, NodeRendererContext context, HtmlWriter html)
            {
                MathJaxNodeRenderer.this.render(node, context, html);
            }
        }));

        return set;
    }

    private void render(MathJax node, NodeRendererContext context, HtmlWriter html)
    {
        html.withAttr().tag("math");
        html.append("$");
        context.renderChildren(node);
        html.append("$");
        html.tag("/math");
    }

    public static class Factory implements NodeRendererFactory
    {
        @Override
        public NodeRenderer create(final DataHolder options)
        {
            return new MathJaxNodeRenderer(options);
        }
    }
}
