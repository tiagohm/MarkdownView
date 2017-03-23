package br.tiagohm.markdownview.css;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

public class InternalStyleSheet implements StyleSheet
{
    private static final String NO_MEDIA_QUERY = "NO_MEDIA_QUERY";
    private Map<String, Map<String, Map<String, String>>> mRules = new LinkedHashMap<>();
    private String currentMediaQuery;

    public InternalStyleSheet()
    {
        currentMediaQuery = NO_MEDIA_QUERY;
        mRules.put(currentMediaQuery, new LinkedHashMap<String, Map<String, String>>());
    }

    private Map<String, Map<String, String>> getCurrentMediaQuery()
    {
        return mRules.get(currentMediaQuery);
    }

    public void addMedia(String mediaQuery)
    {
        if(mediaQuery != null && mediaQuery.trim().length() > 0)
        {
            mediaQuery = mediaQuery.trim();

            if(!mRules.containsKey(mediaQuery))
            {
                mRules.put(mediaQuery, new LinkedHashMap<String, Map<String, String>>());
                currentMediaQuery = mediaQuery;
            }
        }
    }

    public void endMedia()
    {
        currentMediaQuery = NO_MEDIA_QUERY;
    }

    public void addRule(String selector, String... declarations)
    {
        if(selector != null && selector.trim().length() > 0 && declarations.length > 0)
        {
            selector = selector.trim();

            if(!getCurrentMediaQuery().containsKey(selector))
            {
                getCurrentMediaQuery().put(selector, new LinkedHashMap<String, String>());
            }

            for(String declaration : declarations)
            {
                //String vazia.
                if(declaration == null || declaration.trim().length() <= 0) continue;

                String[] nameAndValue = declaration.trim().split(":");

                if(nameAndValue.length == 2)
                {
                    String name = nameAndValue[0].trim();
                    String value = nameAndValue[1].trim();
                    getCurrentMediaQuery().get(selector).put(name, value);
                }
                else
                {
                    Logger.e("invalid css: '" + declaration + "' in selector: " + selector);
                }
            }
        }
    }

    public void removeRule(String selector)
    {
        getCurrentMediaQuery().remove(selector);
    }

    public void removeDeclaration(String selector, String declarationName)
    {
        if(!TextUtils.isEmpty(selector) && getCurrentMediaQuery().containsKey(selector))
        {
            getCurrentMediaQuery().get(selector).remove(declarationName);
        }
    }

    public void replaceDeclaration(String selector, String declarationName, String newDeclarationValue)
    {
        if(!TextUtils.isEmpty(selector) && !TextUtils.isEmpty(declarationName))
        {
            if(getCurrentMediaQuery().containsKey(selector) && getCurrentMediaQuery().get(selector).containsKey(declarationName))
            {
                getCurrentMediaQuery().get(selector).put(declarationName, newDeclarationValue);
            }
        }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, Map<String, Map<String, String>>> q : mRules.entrySet())
        {
            if(!q.getKey().equals(NO_MEDIA_QUERY))
            {
                sb.append("@media ");
                sb.append(q.getKey());
                sb.append(" {\n");
            }

            for(Map.Entry<String, Map<String, String>> e : q.getValue().entrySet())
            {
                sb.append(e.getKey());
                sb.append(" {");
                for(Map.Entry<String, String> declaration : e.getValue().entrySet())
                {
                    sb.append(declaration.getKey());
                    sb.append(":");
                    sb.append(declaration.getValue());
                    sb.append(";");
                }
                sb.append("}\n");
            }

            if(!q.getKey().equals(NO_MEDIA_QUERY))
            {
                sb.append("}\n");
            }
        }

        return sb.toString();
    }

    @Override
    public String toHTML()
    {
        return "<style>\n" + toString() + "\n</style>\n";
    }
}
