package br.tiagohm.markdownview.ext.localization.internal;

import android.content.Context;

import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.HashSet;
import java.util.Set;

import br.tiagohm.markdownview.ext.localization.Localization;
import br.tiagohm.markdownview.ext.localization.LocalizationExtension;

public class LocalizationNodeRenderer implements NodeRenderer
{
  private final Context mContext;

  public LocalizationNodeRenderer(DataHolder options)
  {
    mContext = options.get(LocalizationExtension.LOCALIZATION_CONTEXT);
  }

  @Override
  public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers()
  {
    HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
    set.add(new NodeRenderingHandler<>(Localization.class, new CustomNodeRenderer<Localization>()
    {
      @Override
      public void render(Localization node, NodeRendererContext context, HtmlWriter html)
      {
        LocalizationNodeRenderer.this.render(node, context, html);
      }
    }));

    return set;
  }

  private void render(Localization node, NodeRendererContext context, HtmlWriter html)
  {
    if(mContext != null)
    {
      String packageName = mContext.getPackageName();
      String name = node.getText().toString().trim();
      int identifier = mContext.getResources().getIdentifier(name, "string", packageName);
      if(identifier > 0)
      {
        html.attr("class", "localization");
        html.withAttr().tag("span");
        html.append(mContext.getString(identifier));
        html.tag("/span");
      }
    }
  }

  public static class Factory implements NodeRendererFactory
  {
    @Override
    public NodeRenderer create(final DataHolder options)
    {
      return new LocalizationNodeRenderer(options);
    }
  }
}
