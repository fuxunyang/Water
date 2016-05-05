package com.sky.water.ui.adapter;

import android.animation.AnimatorInflater;
import android.graphics.Outline;
import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;

import com.sky.water.R;
import com.sky.water.common.Constants;
import com.sky.water.model.NewsEntity;
import com.sky.water.ui.BaseAdapter;
import com.sky.water.ui.BaseHolder;

import org.xutils.x;

/**
 * @author sky QQ:1136096189
 * @Description:
 * @date 15/12/9 下午8:52
 */
public class MainAdapter extends BaseAdapter<NewsEntity, BaseHolder> {
    public MainAdapter(int layoutId) {
        super(layoutId);
    }

    @Override
    protected BaseHolder onCreateBodyHolder(View view) {
        return new BaseHolder(view);
    }

    @Override
    protected void onAchieveHolder(BaseHolder holder, int position) {
        // guid为ID, newsTitle为新闻标题，
        // newContent为新闻内容，newsDate发布时间，
        // newsImage新闻图片相对路径,total为全部数据总条数。
        holder.setText(R.id.tv_name, datas.get(position).getNewsTitle());
        holder.setText(R.id.tv_describe, stripHtml(datas.get(position).getNewContent()));
        holder.setText(R.id.tv_time, cutdate(datas.get(position).getNewsDate()));
        holder.setImage(R.id.img_describe, R.mipmap.pictures_no);
        if (datas.get(position).getNewsImage() != null)
            x.image().bind((ImageView) holder.getView(R.id.img_describe), Constants.IMG_URL + datas.get(position).getNewsImage());

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            //使用ripple
            holder.getView(R.id.cardView).setBackground(context.getDrawable(R.drawable.ripple));
            //点击效果，阴影效果
            holder.getView(R.id.cardView).setStateListAnimator(
                    AnimatorInflater.loadStateListAnimator(context, R.drawable.state_list_animator));
            //视图裁剪
            holder.getView(R.id.img_describe).setClipToOutline(true);
            holder.getView(R.id.img_describe).setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(view.getLeft(), view.getTop(),
                            view.getRight(), view.getBottom(), 30);
                }
            });
        }
    }

    private String cutdate(String date) {
        int last = date.lastIndexOf("-");
        return date.substring(0,last+3);
    }

    public String stripHtml(String content) {
        // <p>段落替换为换行
        content = content.replaceAll("<p .*?>", "");
        // <br><br/>替换为换行
        content = content.replaceAll("<br\\s*/?>", "");
        // 去掉其它的<>之间的东西
        content = content.replaceAll("\\<.*?>", "");
        content = content.replaceAll("\r\n", "");
        content = content.replaceAll("&nbsp;", "");
        // 还原HTML
        // content = HTMLDecoder.decode(content);
        return content;
    }

}