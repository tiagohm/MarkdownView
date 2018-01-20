package br.tiagohm.markdownview.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import br.tiagohm.markdownview.MarkdownView;
import br.tiagohm.markdownview.css.InternalStyleSheet;
import br.tiagohm.markdownview.css.styles.Github;

public class MainActivity extends AppCompatActivity {

    private MarkdownView mMarkdownView;
    private InternalStyleSheet mStyle = new Github();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_theme_action:
                break;
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Cria o bean.
        MyBean myBean = new MyBean();
        myBean.setHello("Olá");
        myBean.setDiasDaSemana(MyBean.DiasDaSemana.DOMINGO);

        mMarkdownView = findViewById(R.id.mark_view);
        mMarkdownView.addStyleSheet(mStyle);
        //http://stackoverflow.com/questions/6370690/media-queries-how-to-target-desktop-tablet-and-mobile
        mStyle.addMedia("screen and (min-width: 320px)");
        mStyle.addRule("h1", "color: green");
        mStyle.endMedia();
        mStyle.addMedia("screen and (min-width: 481px)");
        mStyle.addRule("h1", "color: red");
        mStyle.endMedia();
        mStyle.addMedia("screen and (min-width: 641px)");
        mStyle.addRule("h1", "color: blue");
        mStyle.endMedia();
        mStyle.addMedia("screen and (min-width: 961px)");
        mStyle.addRule("h1", "color: yellow");
        mStyle.endMedia();
        mStyle.addMedia("screen and (min-width: 1025px)");
        mStyle.addRule("h1", "color: gray");
        mStyle.endMedia();
        mStyle.addMedia("screen and (min-width: 1281px)");
        mStyle.addRule("h1", "color: orange");
        mStyle.endMedia();
        mMarkdownView.setBean(myBean);
        mMarkdownView.loadMarkdownFromAsset("markdown1.md");
    }

    public static class MyBean {

        public enum DiasDaSemana {
            DOMINGO,
            SEGUNDA,
            TERCA,
            QUARTA,
            QUINTA,
            SEXTA,
            SABADO
        }

        private String hello;
        private DiasDaSemana diasDaSemana;

        public String getHello() {
            return hello;
        }

        public void setHello(String hello) {
            this.hello = hello;
        }

        public DiasDaSemana getDiasDaSemana() {
            return diasDaSemana;
        }

        public void setDiasDaSemana(DiasDaSemana diasDaSemana) {
            this.diasDaSemana = diasDaSemana;
        }
    }
}
