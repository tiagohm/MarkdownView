package br.tiagohm.markdownview.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.tiagohm.markdown.MarkdownView;

public class MainActivity extends AppCompatActivity
{
    private MarkdownView mMarkdownView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMarkdownView = (MarkdownView)findViewById(R.id.mark_view);
        mMarkdownView.loadMarkdownFromAsset("markdown1.md", MarkdownView.Styles.GITHUB);
    }
}
