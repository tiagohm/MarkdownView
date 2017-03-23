package br.tiagohm.markdownview.css;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

public class InternalStyleSheet implements StyleSheet
{
    private Map<String, Map<String, String>> mRules = new LinkedHashMap<>();

    public InternalStyleSheet()
    {
    }

    public void addRule(String selector, String... declarations)
    {
        if(selector != null && selector.trim().length() > 0 && declarations.length > 0)
        {
            selector = selector.trim();

            if(!mRules.containsKey(selector))
            {
                mRules.put(selector, new LinkedHashMap<String, String>());
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
                    mRules.get(selector).put(name, value);
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
        mRules.remove(selector);
    }

    public void removeDeclaration(String selector, String declarationName)
    {
        if(!TextUtils.isEmpty(selector) && mRules.containsKey(selector))
        {
            mRules.get(selector).remove(declarationName);
        }
    }

    public void replaceDeclaration(String selector, String declarationName, String newDeclarationValue)
    {
        if(!TextUtils.isEmpty(selector) && !TextUtils.isEmpty(declarationName))
        {
            if(mRules.containsKey(selector) && mRules.get(selector).containsKey(declarationName))
            {
                mRules.get(selector).put(declarationName, newDeclarationValue);
            }
        }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for(Map.Entry<String, Map<String, String>> e : mRules.entrySet())
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

        return sb.toString();
    }

    @Override
    public String toHTML()
    {
        return "<style>\n" + toString() + "\n</style>\n";
    }
}
