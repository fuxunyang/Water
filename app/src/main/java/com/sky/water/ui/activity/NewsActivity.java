package com.sky.water.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.sky.water.R;
import com.sky.water.model.NewsEntity;
import com.sky.water.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 16/1/27 上午10:46
 */
@ContentView(R.layout.activity_news)
public class NewsActivity extends BaseActivity {
    @ViewInject(R.id.tv_news_title)
    private TextView title;
    @ViewInject(R.id.tv_news_date)
    private TextView date;
    @ViewInject(R.id.tv_news_content)
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
        NewsEntity news = (NewsEntity) getIntent().getSerializableExtra("news");
        title.setText(news.getNewsTitle());
        date.setText("发布时间："+news.getNewsDate());
        content.setText(stripHtml(news.getNewContent()));
    }

    public String stripHtml(String content) {
        // <p>段落替换为换行
//        content = content.replaceAll("<p .*?>", "\r\n");
        content = content.replaceAll("<p .*?>", "");
        // <br><br/>替换为换行
        content = content.replaceAll("<br\\s*/?>", "\r\n");
        // 去掉其它的<>之间的东西
        content = content.replaceAll("\\<.*?>", "");
        content = content.replaceAll("&nbsp;", "");
        // 还原HTML
        // content = HTMLDecoder.decode(content);
        return content;
    }
}
