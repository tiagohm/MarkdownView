package br.tiagohm.markdownview.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import br.tiagohm.markdownview.MarkdownView;

public class MainActivity extends AppCompatActivity
{
    private MarkdownView mMarkdownView;
    private int stylePos = 0;
    private String[] STYLES = new String[]{
            MarkdownView.Styles.GITHUB,
            MarkdownView.Styles.ELECTRON
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMarkdownView = (MarkdownView)findViewById(R.id.mark_view);
        mMarkdownView.loadMarkdownFromAsset("markdown1.md", STYLES[0]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.change_theme_action:
                mMarkdownView.loadMarkdownFromAsset("markdown1.md", STYLES[++stylePos % STYLES.length]);
                break;
        }

        return true;
    }
}
