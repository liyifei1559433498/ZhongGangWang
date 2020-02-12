package com.zgw.home.tablayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.zgw.base.util.LogUtil;
import com.zgw.home.R;
import com.zgw.home.R2;
import com.zgw.home.adapter.UnSelectedTabAdapter;
import com.zgw.home.model.NewsCategoryBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TabPickView extends FrameLayout {

    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R2.id.top_layout)
    LinearLayout mTopLayout;

    @BindView(R2.id.unSelectedRecycleView)
    RecyclerView unSelectedRecycleView;

    private AnimAction<ViewPropertyAnimator> mOnShowAnimator;

    private AnimAction<ViewPropertyAnimator> mOnHideAnimator;

    private TabAdapter mActiveAdapter;

    private OnTabPickingListener mTabPickingListener;

    private UnSelectedTabAdapter unSelectedTabAdapter;

    private ItemTouchHelper mItemTouchHelper;

    private TabPickerDataManager mTabManager;

    private int mSelectedIndex = 0;

    private boolean isShow = false;

    private int removeIndex;

    private Context context;

    public TabPickView(@NonNull Context context) {
        this(context, null);
    }

    public TabPickView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabPickView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    public interface AnimAction<T> {
        void call(T t);
    }

    public interface OnTabPickingListener {
        /**
         * 单击选择某个tab
         *
         * @param position
         */
        void onSelected(int position);

        /**
         * 删除某个tab
         *
         * @param position
         * @param tab
         */
        void onRemove(int position, NewsCategoryBean tab);

        /**
         * 添加某个tab
         *
         * @param tab
         */
        void onInsert(NewsCategoryBean tab);

        /**
         * 交换tab
         *
         * @param op
         * @param np
         */
        void onMove(int op, int np);

        /**
         * 重新持久化活动的tabs
         *
         * @param activeTabs
         */
        void onRestore(List<NewsCategoryBean> activeTabs);
    }


    private void initView() {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.news_tab_picker, this, false);
        ButterKnife.bind(this, view);
        addView(view);
    }

    public void setOnShowAnimation(AnimAction<ViewPropertyAnimator> l) {
        this.mOnShowAnimator = l;
    }

    public void setOnHideAnimator(AnimAction<ViewPropertyAnimator> l) {
        this.mOnHideAnimator = l;
    }

    public void show(int selectedIndex) {
        if (getVisibility() == VISIBLE) {
            return;
        }
        isShow = true;
        final int tempIndex = mSelectedIndex;
        mSelectedIndex = selectedIndex;
        mActiveAdapter.notifyItemChanged(tempIndex);
        mActiveAdapter.notifyItemChanged(mSelectedIndex);
        mActiveAdapter.notifyDataSetChanged();
        unSelectedTabAdapter.notifyDataSetChanged();
        if (mOnShowAnimator != null) mOnShowAnimator.call(null);
        setVisibility(VISIBLE);
        mTopLayout.setAlpha(0);
        mTopLayout.animate().alpha(1).setDuration(380).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mTopLayout.setAlpha(1);
            }
        });

//        mRecyclerView.setTranslationY(-mRecyclerView.getHeight());
//        mRecyclerView.animate().translationY(0).setDuration(380).setListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                mRecyclerView.setTranslationY(0);
//            }
//        });
    }

    public void hide() {
        isShow = false;
        if (mTabPickingListener != null) {
            mTabPickingListener.onRestore(mTabManager.mActiveDataSet);
            mTabPickingListener.onSelected(mSelectedIndex);
        }

        if (mOnHideAnimator != null) {
            mOnHideAnimator.call(null);
        }
        //取消动画
//        mTopLayout.animate().alpha(0).setDuration(380).setListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                setVisibility(GONE);
//            }
//        });
//        mRecyclerView.animate().translationY(-mRecyclerView.getHeight()).setDuration(380);

        setVisibility(GONE);
    }

    public boolean isShow() {
        return isShow;
    }

    private class TabAdapter extends RecyclerView.Adapter {

        private OnClickListener mClickTabItemListener;
        private OnTouchListener mTouchTabItemListener;

        private AnimAction<Integer> mSelectItemAction;
        AnimAction<TabViewHolder> mBindViewObserver;

        private boolean isEditMode = false;
        List<NewsCategoryBean> items;

        TabAdapter(List<NewsCategoryBean> items) {
            this.items = items;
        }

        NewsCategoryBean removeItem(int position) {
            NewsCategoryBean b = items.remove(position);
            notifyItemRemoved(position);
            return b;
        }

        void addItem(NewsCategoryBean bean) {
            items.add(bean);
            notifyItemInserted(items.size() - 1);
        }

        void addItem(NewsCategoryBean bean, int index) {
            items.add(index, bean);
            notifyItemInserted(index);
        }

        NewsCategoryBean getItem(int position) {
            if (position < 0 || position >= items.size()) return null;
            return items.get(position);
        }

        boolean isEditMode() {
            return isEditMode;
        }

        void registerBindViewObserver(AnimAction<TabViewHolder> l) {
            this.mBindViewObserver = l;
        }

        void unRegisterBindViewObserver() {
            this.mBindViewObserver = null;
        }

        OnClickListener getClickTabItemListener() {

            if (mClickTabItemListener == null) {
                mClickTabItemListener = new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TabViewHolder holder = (TabViewHolder) v.getTag();
                        if (holder == null) return;
                        if (mSelectItemAction != null) {
                            holder.mViewTab.setSelected(true);
                            mSelectItemAction.call(holder.getAdapterPosition());
                        }
                    }
                };
            }
            return mClickTabItemListener;
        }

        OnTouchListener getTouchTabItemListener() {
            if (mTouchTabItemListener == null) {
                mTouchTabItemListener = new OnTabTouchListener();
            }
            return mTouchTabItemListener;
        }

        void setOnClickItemListener(AnimAction<Integer> l) {
            this.mSelectItemAction = l;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TabViewHolder(LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.item_news_tab, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            NewsCategoryBean item = items.get(position);
            TabViewHolder holder = (TabViewHolder) viewHolder;
            holder.mViewTab.setText(item.getTitle());

            if (item.getMove() == 1) {
                holder.mViewTab.setActivated(false);
                holder.mViewTab.setBackgroundResource(R.drawable.dynamic_tab_fixed_selector);
            } else {
                holder.mViewTab.setActivated(true);
                holder.mViewTab.setBackgroundResource(R.drawable.dynamic_tab_selector);
            }

            if (mSelectedIndex == position) {
                holder.mViewTab.setSelected(true);
            } else {
                holder.mViewTab.setSelected(false);
            }

            if (mBindViewObserver != null) {
                mBindViewObserver.call(holder);
            }


            holder.mViewTab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除
                    if(position == 0)return;

                    for (int i = 0; i < mTabManager.unSelectedList.size(); i++) {
                        if (mTabManager.unSelectedList.get(i).getCategoryId().equals(mTabManager.mActiveDataSet.get(position).getCategoryId()))return;
                    }
                    removeIndex = position;
                    mSelectedIndex = position - 1;
                    if (mTabPickingListener != null) {
                        mTabPickingListener.onRemove(removeIndex, mTabManager.mActiveDataSet.get(removeIndex));
                        mTabManager.unSelectedList.add( mTabManager.mActiveDataSet.get(removeIndex));
                        unSelectedTabAdapter.notifyDataSetChanged();
                    }
                    mActiveAdapter.removeItem(position);

                    if (mTabPickingListener != null) {
                        mTabPickingListener.onRestore(mTabManager.mActiveDataSet);
                        mTabPickingListener.onSelected(mSelectedIndex);
                        notifyDataSetChanged();
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        TabItemTouchHelperCallback newItemTouchHelperCallback() {
            return new TabItemTouchHelperCallback();
        }

        class TabViewHolder extends RecyclerView.ViewHolder {


            public TextView mViewTab;

            public ImageView deleteBtn;

            TabViewHolder(View view) {
                super(view);
                mViewTab = (TextView) view.findViewById(R.id.tv_content);
                deleteBtn = (ImageView) view.findViewById(R.id.deleteBtn);

                mViewTab.setTextColor(new ColorStateList(new int[][]{
                                new int[]{android.R.attr.state_selected},
                                new int[]{-android.R.attr.state_selected}
                        }, new int[]{0XFFFF7600, 0XFF454545})
                );
                mViewTab.setActivated(true);

                mViewTab.setTag(this);

//                mViewTab.setOnClickListener(getClickTabItemListener());
                mViewTab.setOnTouchListener(getTouchTabItemListener());
//                mViewTab.setOnLongClickListener(new OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View v) {
//                        notifyDataSetChanged();
//                        return true;
//                    }
//                });

            }
        }

        private class OnTabTouchListener implements OnTouchListener {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                TabViewHolder holder = (TabViewHolder) v.getTag();
                if (holder == null) return false;
                if (isEditMode() && MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    holder.mViewTab.setSelected(false);
                    mItemTouchHelper.startDrag(holder);
                    return true;
                }

                return false;
            }
        }

        class TabItemTouchHelperCallback extends ItemTouchHelper.Callback {

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlag = 0;
                int position = viewHolder.getAdapterPosition();
                if (position > 0 && position < items.size()) {
                    if (items.get(position).getMove() == 2) {
                        dragFlag = ItemTouchHelper.UP
                                | ItemTouchHelper.DOWN
                                | ItemTouchHelper.LEFT
                                | ItemTouchHelper.RIGHT;
                    }
                }
                return makeMovementFlags(dragFlag, 0);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                int fromTargetIndex = viewHolder.getAdapterPosition();
                int toTargetIndex = target.getAdapterPosition();

                if (fromTargetIndex == toTargetIndex) {
                    return true;
                }
                if (items.get(toTargetIndex).getMove() == 1) {
                    return true;
                }

                NewsCategoryBean tab = items.remove(fromTargetIndex);
                items.add(toTargetIndex, tab);

                if (mSelectedIndex == fromTargetIndex) {
                    mSelectedIndex = toTargetIndex;
                } else if (mSelectedIndex == toTargetIndex) {
                    mSelectedIndex = fromTargetIndex > toTargetIndex ? mSelectedIndex + 1 : mSelectedIndex - 1;
                } else if (toTargetIndex <= mSelectedIndex && mSelectedIndex < fromTargetIndex) {
                    ++mSelectedIndex;
                } else if (fromTargetIndex < mSelectedIndex && mSelectedIndex < toTargetIndex) {
                    --mSelectedIndex;
                }

                notifyItemMoved(fromTargetIndex, toTargetIndex);
                if (mTabPickingListener != null) {
                    mTabPickingListener.onMove(fromTargetIndex, toTargetIndex);
                }
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // pass
            }

            @Override
            public void onSelectedChanged(final RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (viewHolder == null) {
                    return;
                }
                Animation zoomOut = AnimationUtils.loadAnimation(getContext(), R.anim.news_tab_zoom_out);
                ((TabViewHolder) viewHolder).mViewTab.startAnimation(zoomOut);

                if (isEditMode()) return;
                final int position = viewHolder.getAdapterPosition();

                // onBindViewHolder之后，ViewHolder.itemView.getParent() != RecycleView
                // 估计是在onBindViewHolder之后绑定了ViewParent的，延迟200，暂时没什么好办法
                registerBindViewObserver(new AnimAction<TabViewHolder>() {
                    @Override
                    public void call(final TabViewHolder viewHolder) {
                        int index = viewHolder.getAdapterPosition();
                        if (index != position) return;
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((TabViewHolder) viewHolder).mViewTab.setSelected(false);
//                                mItemTouchHelper.startDrag(viewHolder);
                            }
                        }, 200);
                        unRegisterBindViewObserver();
                    }
                });
            }

            @Override
            public void clearView(RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                ((TabViewHolder) viewHolder).mViewTab.clearAnimation();
                Animation zoomIn = AnimationUtils.loadAnimation(getContext(), R.anim.news_tab_zoom_in);
                ((TabViewHolder) viewHolder).mViewTab.startAnimation(zoomIn);

                if (mSelectedIndex == viewHolder.getAdapterPosition()) {
                    return;
                }
                ((TabViewHolder) viewHolder).mViewTab.setSelected(false);
            }
        }

    }

    private void initRecyclerView() {

        unSelectedTabAdapter = new UnSelectedTabAdapter(context, mTabManager.unSelectedList);
        unSelectedRecycleView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        unSelectedRecycleView.setAdapter(unSelectedTabAdapter);

        unSelectedTabAdapter.setAddTabListener(new UnSelectedTabAdapter.AddTabListener() {
            @Override
            public void addTabListener(int position, NewsCategoryBean newsCategoryBean) {
                if (mTabPickingListener != null) {
//                    mTabManager.unSelectedList.remove(position);
//                    mTabManager.mActiveDataSet.add(newsCategoryBean);
                    for (int i = 0; i < mTabManager.mActiveDataSet.size(); i++) {
                        if (mTabManager.mActiveDataSet.get(i).getCategoryId().equals(newsCategoryBean.getCategoryId())) {
                            return;
                        }
                    }
                    mTabPickingListener.onInsert(newsCategoryBean);
                    unSelectedTabAdapter.notifyDataSetChanged();
                    mActiveAdapter.notifyDataSetChanged();
                    mTabPickingListener.onRestore(mTabManager.mActiveDataSet);
                }
            }
        });
        mActiveAdapter = new TabAdapter(mTabManager.mActiveDataSet);
        mActiveAdapter.setOnClickItemListener(new AnimAction<Integer>() {
            @Override
            public void call(Integer position) {
//                //删除
//                if(position == 0)return;
//                removeIndex = position;
//                mSelectedIndex = position - 1;
//                if (mTabPickingListener != null) {
//                    LogUtil.outLog("TAG", "removeIndex == " + removeIndex);
//                    LogUtil.outLog("TAG", "mTabManager.mActiveDataSet == " +mTabManager.mActiveDataSet.size());
//                    mTabPickingListener.onRemove(removeIndex, mTabManager.mActiveDataSet.get(removeIndex));
//                    mTabManager.unSelectedList.add( mTabManager.mActiveDataSet.get(removeIndex));
//                    unSelectedTabAdapter.notifyDataSetChanged();
//                }
//                mActiveAdapter.removeItem(position);
//
//                if (mTabPickingListener != null) {
//                    mTabPickingListener.onRestore(mTabManager.mActiveDataSet);
//                    mTabPickingListener.onSelected(mSelectedIndex);
//                }

            }
        });


        mRecyclerView.setAdapter(mActiveAdapter);

        mItemTouchHelper = new ItemTouchHelper(mActiveAdapter.newItemTouchHelperCallback());
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));

        /* - set up Inactive Recycler - */
//        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
    }

    public void setOnTabPickingListener(OnTabPickingListener l) {
        mTabPickingListener = l;
    }

    public void setTabPickerManager(TabPickerDataManager manager) {
        if (manager == null) return;
        mTabManager = manager;
        initRecyclerView();
    }

    public boolean onTurnBack() {
        if (getVisibility() == VISIBLE) {
            hide();
            return true;
        }
        return false;
    }

    public abstract static class TabPickerDataManager {

        public List<NewsCategoryBean> mActiveDataSet;

        public List<NewsCategoryBean> unSelectedList;

        public TabPickerDataManager() {
            mActiveDataSet = setupActiveDataSet();
            unSelectedList = setSelectedList();

            if (mActiveDataSet == null) {
                mActiveDataSet = new ArrayList<>();
                restoreActiveDataSet(mActiveDataSet);
            }

            if (unSelectedList == null) {
                unSelectedList = new ArrayList<>();
                unRestoreActiveDataSet(unSelectedList);
            }

        }

        public List<NewsCategoryBean> getActiveDataSet() {
            return mActiveDataSet;
        }

        public List<NewsCategoryBean> unSelectedList() {
            return unSelectedList;
        }

        public abstract List<NewsCategoryBean> setupActiveDataSet();

        public abstract List<NewsCategoryBean> setSelectedList();

        public abstract List<NewsCategoryBean> setupOriginalDataSet();

        public abstract void restoreActiveDataSet(List<NewsCategoryBean> mActiveDataSet);

        public abstract void unRestoreActiveDataSet(List<NewsCategoryBean> unSelectedList);
    }
}
