package com.day.shoushi.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.InputType;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.day.shoushi.R;
import com.day.shoushi.adapter.IndexGalleryAdapter;
import com.day.shoushi.entity.IndexGalleryItemData;
import com.day.shoushi.utils.CommonTools;
import com.day.shoushi.myview.HomeSearchBarPopupWindow;
import com.day.shoushi.myview.HomeSearchBarPopupWindow.onSearchBarItemClickListener;
import com.day.shoushi.myview.JazzyViewPager;
import com.day.shoushi.myview.JazzyViewPager.TransitionEffect;
import com.day.shoushi.myview.OutlineContainer;

public class HomeFragment extends Fragment implements OnClickListener,
        onSearchBarItemClickListener {
    public static final String TAG = HomeFragment.class.getSimpleName();

    private ImageView mMiaoShaImage = null;
    private TextView mIndexHour = null;
    private TextView mIndexMin = null;
    private TextView mIndexSeconds = null;
    private TextView mIndexPrice = null;
    private TextView mIndexRawPrice = null;
    // ============== 广告切换 ===================
    private JazzyViewPager mViewPager = null;
    /**
     * 装指引的ImageView数组
     */
    private ImageView[] mIndicators;

    /**
     * 装ViewPager中ImageView的数组
     */
    private ImageView[] mImageViews;
    private List<String> mImageUrls = new ArrayList<String>();
    private LinearLayout mIndicator = null;
    private String mImageUrl = null;
    private static final int MSG_CHANGE_PHOTO = 1;
    /** 图片自动切换时间 */
    private static final int PHOTO_CHANGE_TIME = 3000;
    // ============== 广告切换 ===================

    private Gallery mStormGallery = null;
    private Gallery mPromotionGallery = null;
    private IndexGalleryAdapter mStormAdapter = null;
    private IndexGalleryAdapter mPromotionAdapter = null;
    private List<IndexGalleryItemData> mStormListData = new ArrayList<IndexGalleryItemData>();
    private List<IndexGalleryItemData> mPromotionListData = new ArrayList<IndexGalleryItemData>();
    private IndexGalleryItemData mItemData = null;
    private HomeSearchBarPopupWindow mBarPopupWindow = null;
    private EditText mSearchBox = null;
    private ImageButton mSearchButton = null;
    private LinearLayout mTopLayout = null;
    private View view ;
    private Handler mHandler ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        //super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.home, null);
        mHandler = new Handler(this.getActivity().getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                switch (msg.what) {
                    case MSG_CHANGE_PHOTO:
                        int index = mViewPager.getCurrentItem();
                        if (index == mImageUrls.size() - 1) {
                            index = -1;
                        }
                        mViewPager.setCurrentItem(index + 1);
                        mHandler.sendEmptyMessageDelayed(MSG_CHANGE_PHOTO,
                                PHOTO_CHANGE_TIME);
                }
            }

        };

        initData();

        findViewById();
        initView();
        return view ;
    }

    //@Override
    protected void findViewById() {
        // TODO Auto-generated method stub
        mIndexHour = (TextView) view.findViewById(R.id.index_miaosha_hour);
        mIndexMin = (TextView) view.findViewById(R.id.index_miaosha_min);
        mIndexSeconds = (TextView)view. findViewById(R.id.index_miaosha_seconds);
        mIndexPrice = (TextView) view.findViewById(R.id.index_miaosha_price);
        mIndexRawPrice = (TextView) view.findViewById(R.id.index_miaosha_raw_price);

        mMiaoShaImage = (ImageView) view.findViewById(R.id.index_miaosha_image);
        mViewPager = (JazzyViewPager) view.findViewById(R.id.index_product_images_container);
        mIndicator = (LinearLayout) view.findViewById(R.id.index_product_images_indicator);

        mStormGallery = (Gallery) view.findViewById(R.id.index_jingqiu_gallery);
        mPromotionGallery = (Gallery)view. findViewById(R.id.index_tehui_gallery);

        mSearchBox = (EditText) view.findViewById(R.id.index_search_edit);
        mSearchButton = (ImageButton)view.findViewById(R.id.index_search_button);
        mTopLayout = (LinearLayout) view.findViewById(R.id.index_top_layout);
    }

    protected void initView() {
        // TODO Auto-generated method stub
        ImageLoader.getInstance().displayImage(
                "drawable://" + R.drawable.miaosha, mMiaoShaImage);

        mIndexHour.setText("00");
        mIndexMin.setText("53");
        mIndexSeconds.setText("49");
        mIndexPrice.setText("￥269.99");
        mIndexRawPrice.setText("￥459.99");
        mIndexRawPrice.getPaint().setFlags(
                Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

        // ======= 初始化ViewPager ========
        mIndicators = new ImageView[mImageUrls.size()];
        if (mImageUrls.size() <= 1) {
            mIndicator.setVisibility(View.GONE);
        }

        for (int i = 0; i < mIndicators.length; i++) {
            ImageView imageView = new ImageView(this.getActivity());
            LayoutParams params = new LayoutParams(0,
                    LayoutParams.WRAP_CONTENT, 1.0f);
            if (i != 0) {
                params.leftMargin = 5;
            }
            imageView.setLayoutParams(params);
            mIndicators[i] = imageView;
            if (i == 0) {
                mIndicators[i]
                        .setBackgroundResource(R.mipmap.android_activities_cur);
            } else {
                mIndicators[i]
                        .setBackgroundResource(R.mipmap.android_activities_bg);
            }

            mIndicator.addView(imageView);
        }

        mImageViews = new ImageView[mImageUrls.size()];

        for (int i = 0; i < mImageViews.length; i++) {
            ImageView imageView = new ImageView(this.getActivity());
            imageView.setScaleType(ScaleType.CENTER_CROP);
            mImageViews[i] = imageView;
        }
        mViewPager.setTransitionEffect(TransitionEffect.CubeOut);
        mViewPager.setCurrentItem(0);
        mHandler.sendEmptyMessageDelayed(MSG_CHANGE_PHOTO, PHOTO_CHANGE_TIME);

        mViewPager.setAdapter(new MyAdapter());
        mViewPager.setOnPageChangeListener(new MyPageChangeListener());
        mViewPager.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (mImageUrls.size() == 0 || mImageUrls.size() == 1)
                    return true;
                else
                    return false;
            }
        });
        // ======= 初始化ViewPager ========

        mStormAdapter = new IndexGalleryAdapter(this.getActivity(),
                R.layout.activity_index_gallery_item, mStormListData,
                new int[] { R.id.index_gallery_item_image,
                        R.id.index_gallery_item_text });

        mStormGallery.setAdapter(mStormAdapter);

        mPromotionAdapter = new IndexGalleryAdapter(this.getActivity(),
                R.layout.activity_index_gallery_item, mPromotionListData,
                new int[] { R.id.index_gallery_item_image,
                        R.id.index_gallery_item_text });

        mPromotionGallery.setAdapter(mPromotionAdapter);

        mStormGallery.setSelection(3);
        mPromotionGallery.setSelection(3);

        mBarPopupWindow = new HomeSearchBarPopupWindow(this.getActivity(),
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mBarPopupWindow.setOnSearchBarItemClickListener(this);

        mSearchButton.setOnClickListener(this);
        mSearchBox.setOnClickListener(this);

        mSearchBox.setInputType(InputType.TYPE_NULL);
    }

    private void initData() {
        mImageUrl = "mipmap://" + R.mipmap.image01;
        mImageUrls.add(mImageUrl);

        mImageUrl = "mipmap://" + R.mipmap.image02;
        mImageUrls.add(mImageUrl);

        mImageUrl = "mipmap://" + R.mipmap.image03;
        mImageUrls.add(mImageUrl);

        mImageUrl = "mipmap://" + R.mipmap.image04;
        mImageUrls.add(mImageUrl);

        mImageUrl = "drawable://" + R.mipmap.image05;
        mImageUrls.add(mImageUrl);

        mImageUrl = "mipmap://" + R.mipmap.image06;
        mImageUrls.add(mImageUrl);

        mImageUrl = "mipmap://" + R.mipmap.image07;
        mImageUrls.add(mImageUrl);

        mImageUrl = "mipmap://" + R.mipmap.image08;
        mImageUrls.add(mImageUrl);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(1);
        mItemData.setImageUrl("mipmap://" + R.mipmap.index_gallery_01);
        mItemData.setPrice("￥79.00");
        mStormListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(2);
        mItemData.setImageUrl("mipmap://" + R.mipmap.index_gallery_02);
        mItemData.setPrice("￥89.00");
        mStormListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(3);
        mItemData.setImageUrl("mipmap://" + R.mipmap.index_gallery_03);
        mItemData.setPrice("￥99.00");
        mStormListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(4);
        mItemData.setImageUrl("mipmap://" + R.mipmap.index_gallery_04);
        mItemData.setPrice("￥109.00");
        mStormListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(5);
        mItemData.setImageUrl("mipmap://" + R.mipmap.index_gallery_05);
        mItemData.setPrice("￥119.00");
        mStormListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(6);
        mItemData.setImageUrl("mipmap://" + R.mipmap.index_gallery_06);
        mItemData.setPrice("￥129.00");
        mStormListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(7);
        mItemData.setImageUrl("mipmap://" + R.mipmap.index_gallery_07);
        mItemData.setPrice("￥139.00");
        mStormListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(8);
        mItemData.setImageUrl("mipmap://" + R.mipmap.index_gallery_08);
        mItemData.setPrice("￥69.00");
        mPromotionListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(9);
        mItemData.setImageUrl("mipmap://" + R.mipmap.index_gallery_09);
        mItemData.setPrice("￥99.00");
        mPromotionListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(10);
        mItemData.setImageUrl("mipmap://" + R.mipmap.index_gallery_10);
        mItemData.setPrice("￥109.00");
        mPromotionListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(11);
        mItemData.setImageUrl("mipmap://" + R.mipmap.index_gallery_11);
        mItemData.setPrice("￥119.00");
        mPromotionListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(12);
        mItemData.setImageUrl("mipmap://" + R.mipmap.index_gallery_12);
        mItemData.setPrice("￥129.00");
        mPromotionListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(13);
        mItemData.setImageUrl("mipmap://" + R.mipmap.index_gallery_13);
        mItemData.setPrice("￥139.00");
        mPromotionListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(14);
        mItemData.setImageUrl("mipmap://" + R.mipmap.index_gallery_14);
        mItemData.setPrice("￥149.00");
        mPromotionListData.add(mItemData);
    }

    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageViews.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            if (view instanceof OutlineContainer) {
                return ((OutlineContainer) view).getChildAt(0) == obj;
            } else {
                return view == obj;
            }
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(mViewPager
                    .findViewFromObject(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ImageLoader.getInstance().displayImage(mImageUrls.get(position),
                    mImageViews[position]);
            ((ViewPager) container).addView(mImageViews[position], 0);
            mViewPager.setObjectForPosition(mImageViews[position], position);
            return mImageViews[position];
        }

    }

    /**
     * 当ViewPager中页面的状态发生改变时调用
     *
     * @author Administrator
     *
     */
    private class MyPageChangeListener implements OnPageChangeListener {

        /**
         * This method will be invoked when a new page becomes selected.
         * position: Position index of the new selected page.
         */
        public void onPageSelected(int position) {
            setImageBackground(position);
        }

        public void onPageScrollStateChanged(int arg0) {

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }

    /**
     * 设置选中的tip的背景
     *
     * @param selectItemsIndex
     */
    private void setImageBackground(int selectItemsIndex) {
        for (int i = 0; i < mIndicators.length; i++) {
            if (i == selectItemsIndex) {
                mIndicators[i]
                        .setBackgroundResource(R.mipmap.android_activities_cur);
            } else {
                mIndicators[i]
                        .setBackgroundResource(R.mipmap.android_activities_bg);
            }
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.index_search_button:
                int height = mTopLayout.getHeight()
                        + CommonTools.getStatusBarHeight(this.getActivity());
                mBarPopupWindow.showAtLocation(mTopLayout, Gravity.TOP, 0, height);
                break;

            case R.id.index_search_edit:
                //openActivity(SearchActivity.class);
                break;

            default:
                break;
        }
    }

    @Override
    public void onBarCodeButtonClick() {
        // TODO Auto-generated method stub
        CommonTools.showShortToast(this.getActivity(), "条码购");
    }

    @Override
    public void onCameraButtonClick() {
        // TODO Auto-generated method stub
        CommonTools.showShortToast(this.getActivity(), "拍照购");
    }

    @Override
    public void onColorButtonClick() {
        // TODO Auto-generated method stub
        CommonTools.showShortToast(this.getActivity(), "颜色购");
    }
}