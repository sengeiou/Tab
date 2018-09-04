package cn.zero.android.common.view.navigation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import com.toocms.frame.ui.R;

import java.util.ArrayList;
import java.util.List;

import cn.zero.android.common.util.ListUtils;
import cn.zero.android.common.util.ScreenUtils;
import cn.zero.android.common.view.banner.ConvenientBanner;
import cn.zero.android.common.view.banner.holder.CBViewHolderCreator;
import cn.zero.android.common.view.banner.holder.Holder;

/**
 * 翻页导航栏
 *
 * @author Zero
 * @date 2016/9/9 14:57
 */
public class FlipNavigationView<T> extends ConvenientBanner {

    public int page_size = 8; // 每页显示的数量

    private List<NavigationAdapter> adapters;

    private int pageCount; // 总共的页数
    private boolean isCoerciveCircle; // 是否强制显示圆形图片

    public FlipNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置每页最多显示的数量
     *
     * @param page_size 数量，不能为奇数
     * @return
     */
    public FlipNavigationView<T> setPage_size(int page_size) {
        if (page_size % 2 != 0) {
            throw new RuntimeException("请输入一个偶数作为参数");
        }
        this.page_size = page_size;
        return this;
    }

    /**
     * 设置强制显示圆形图片
     */
    public FlipNavigationView<T> setCoerciveCircle() {
        isCoerciveCircle = true;
        return this;
    }

    /**
     * 设置导航栏的数据以及所对应的key值
     *
     * @param list 数据源
     * @param keys 对应的key值，图标/文字
     * @return
     */
    public FlipNavigationView<T> setData(List<T> list, String[] keys, OnNavigationClickListener listener) {
        // 总页数=数据源条数/每页数量，取整
        pageCount = (int) Math.ceil(ListUtils.getSize(list) * 1.0 / page_size);
        adapters = new ArrayList<>();
        for (int i = 0; i < pageCount; i++) {
            NavigationAdapter adapter = new NavigationAdapter(getContext(), list, keys, i, page_size, isCoerciveCircle);
            adapter.setOnNavigationClickListener(listener);
            adapters.add(adapter);
        }
        setPages(new CBViewHolderCreator() {
            @Override
            public Holder createHolder(View itemView) {
                return new FlipHolder(itemView);
            }

            @Override
            public int getLayoutId() {
                return R.layout.fnv_gridview;
            }
        }, adapters)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focused})
                .setPageIndicatorAlign(PageIndicatorAlign.CENTER_HORIZONTAL);
        setCanLoop(false);
        // 根据数据源设置高度以及是否显示圆点
        // item的高度
        int itemHeight = ScreenUtils.dpToPxInt(63.3f);
        if (pageCount > 1) { // 当页数大于1页时，两行的高度+底部圆点的高度
            setPointViewVisible(true);
            getLayoutParams().height = itemHeight * 2 + ScreenUtils.dpToPxInt(13.3f);
        } else {
            setPointViewVisible(false);
            if (ListUtils.getSize(list) > 4) {
                getLayoutParams().height = itemHeight * 2;
            } else {
                getLayoutParams().height = itemHeight;
            }
        }
        return this;
    }

    private class FlipHolder extends Holder<NavigationAdapter> {

        private GridView gridView;

        public FlipHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            gridView = itemView.findViewById(R.id.fnv_gridview);
            gridView.setNumColumns(page_size / 2);
        }

        @Override
        public void updateUI(NavigationAdapter adapter) {
            gridView.setAdapter(adapter);
        }
    }
}
