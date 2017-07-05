ht-recyclerviewadapter使用文档
===============

根据 [REMEADME文档](https://github.com/NEYouFan/ht-recyclerviewadapter/blob/master/README.md)，初步知道如何将 `TRecyclerViewAdapter ` 集成至工程中，也简单知道 6 步使用 `TRecyclerViewAdapter ` 的简单使用

## 基本的使用和API

#### 普通 ViewHolder 的使用

1. 定义 `ViewHolder` 类型枚举值:
	
		public interface ViewItemType {
		    int ITEM_VIEW_HOLDER_0 = 0;
		    int ITEM_VIEW_HOLDER_1 = 1;
		    int ITEM_VIEW_HOLDER_2 = 2;
		}
		
	**说明**：定义的枚举常量值之间要互不相同

2. 定义具体 `ViewHolder` class

		import com.netease.hearttouch.htrecyclerviewadapter.TAdapterItem;
		import com.netease.hearttouch.htrecyclerviewadapter.TRecyclerViewHolder;
		import com.netease.hearttouch.htrecyclerviewadapter.TRecyclerViewHolderAnnotation;

		@TRecyclerViewHolderAnnotation(resId = R.layout.item_viewholder_0)
		public class ViewHolder0 extends TRecyclerViewHolder<DataModel0> {
		    
		    public ViewHolder0(View itemView, Context context, RecyclerView recyclerView) {
		        super(itemView, context, recyclerView);
		    }
			
		    @Override
		    public void inflate() {
		        // do something initialize
		    }
			
		    @Override
		    public void refresh(TAdapterItem<DataModel0> item) {
		        DataModel0 dataModel = item.getDataModel();
		        // 使用 dataModel 去刷新视图
		    }
		}
		
	**说明**

	- 代码 `@TRecyclerViewHolderAnnotation(resId = R.layout.item_viewholder_0)` 指定了当前 `ViewHolder` 的布局文档。

	- 定义的基类 `TRecyclerViewHolder<DataModel0>` 中的模板指定了当前 `ViewHolder` 中使用的数据类型。

	- 构造函数 `public ViewHolder0(View itemView, Context context, RecyclerView recyclerView) { ** }` 并不需要程序员主动调用。
	
	- `inflate` 接口在 `ViewHolder` 对应的 item 第一次显示的时候调用，可以想象是在 `ListView` 的 `getView` 接口中当 `convertView` 为 `null` 的情况。

	- `refresh` 接口在 `ViewHolder` 对应的 item 发生复用的时候调用，根据通过 `item.getDataModel();` 获取当前 `ViewHolder` 中对应的数据，并根据使用数据更新 UI。

3. 定义 `AdapterItem` 类型

		import com.netease.hearttouch.htrecyclerviewadapter.TAdapterItem;
		public class AdapterItem0 implements TAdapterItem<DataModel0> {
		
			private DataModel0 mDataModel;
		
		    public AdapterItem0(DataModel0 dataModel) {
		    	mDataModel = dataModel;
		    }
		
		    @Override
		    public int getViewType() {
		        return ViewItemType.ITEM_VIEW_HOLDER_0;
		    }
		
		    @Override
		    public int getId() {
		        return mDataModel.hashCode();
		    }
		
		    @Override
		    public DataModel0 getDataModel() {
		        return mDataModel;
		    }
		}
		
	**说明**

	- 定义的基类 `TAdapterItem<DataModel0>` 中的模板指定了当前 item 中使用的数据类型。

	- 构造函数 `public ViewHolder0(View itemView, Context context, RecyclerView recyclerView) { ** }` 并不需要程序员主动调用。
	
	- `getViewType ` 接口定义了 item 的类型，使用第 `1` 步定义的常量值。

	- `getId` 接口的返回值定义了每个 item 的 id 值。因为需要每个 id 不同，推荐使用 `mDataModel.hashCode()` 的返回值。

	- `getDataModel` 接口返回了 item 中对应的数据，在 'ViewHolder' 的 'refresh' 接口中使用。

4. 定义 SparseArray 变量关联 `ViewItemType` 常量与 `ViewHolder` 类型信息

		private final static SparseArray<Class<? extends TRecyclerViewHolder>> sViewHolders = new SparseArray<>();
		static {
		        viewHolders.put(ViewItemType.ITEM_VIEW_HOLDER_0, ViewHolder0.class);
		        viewHolders.put(ViewItemType.ITEM_VIEW_HOLDER_1, ViewHolder1.class);
		        viewHolders.put(ViewItemType.ITEM_VIEW_HOLDER_2, ViewHolder2.class);
		}
		
	**说明**

	- 定义的 ViewItemType 中的常量值和 ViewHolder 的具体类型

5. 初始化 `List<TAdapterItem>` 变量

		private List<TAdapterItem> mTAdapterItems = new ArrayList<>();
		
		...
		
		mTAdapterItems.add(new AdapterItem0(new DataModel0()));
		...
		mTAdapterItems.add(new AdapterItem1(new DataModel1()));
		...
		mTAdapterItems.add(new AdapterItem2(new DataModel2()));

	**说明**

	- 定义 List<TAdapterItem> 类型的变量，将一个列表页中全部 item 添加进去，即一个 `RecyclerView` 有多少项内容，就定义多少个 `TAdapterItem` 对象

6. 初始化 `TRecyclerViewAdapter` 对象，并设置给 `RecyclerView` 

		mRecyclerViewAdapter = new TRecyclerViewAdapter(context, sViewHolders, mTAdapterItems);
		recycleView.setAdapter(mRecyclerViewAdapter);
		
#### 左右滑功能的 ViewHolder 的使用

1. 定义 `ViewHolder` 类型枚举值 (同上)

2. 定义具体 `ViewHolder` class

```
	import com.netease.hearttouch.htrecyclerviewadapter.TAdapterItem;
	import com.netease.hearttouch.htrecyclerviewadapter.bga.BGARecyclerViewHolder;
	import com.netease.hearttouch.htrecyclerviewadapter.bga.BGARecyclerViewHolderAnnotation;
	import com.netease.hearttouch.htrecyclerviewadapter.bga.BGASwipeItemLayout;

	@BGARecyclerViewHolderAnnotation(
    leftLayoutId = R.layout.item_left_view,
    rightLayoutId = R.layout.item_right_view)
	
	public class BGAViewHolder0
    	extends BGARecyclerViewHolder<DataModel0> {
	    
	    public BGAViewHolder0(BGASwipeItemLayout itemView,
                              View leftContent,
                              View rightContent,
                              Context context,
                              RecyclerView recyclerView) {
    		super(itemView, leftContent, rightContent, context, recyclerView);
	    }
	
	    @Override
	    public boolean getSwipeable() {
	        return false;
	    }
	    
	    @Override
	    public SwipeDirection getSwipeDirection() {
	        return SwipeDirection.Left;
	    }
	
		@Override
	    public BottomModel getBottomModel() {
	        return BottomModel.PullOut;
	    }
	
		@Override	
	    public int getSpringDistance() {
	        return 0;
	    }
	
	    @Override
	    public void inflate() {
	        // 页面 layout 创建的时候
	    }
	
	    @Override
	    public void refresh(TAdapterItem<DataModel0> item) {
	        DataModel0 dataModel = item.getDataModel();
	    }
	}
```

**说明**

- 代码 `@BGARecyclerViewHolderAnnotation(leftLayoutId = R.layout.item_left_view, rightLayoutId = R.layout.item_right_view)` 指定了当前 `ViewHolder` 的布局文档，`leftLayoutId` 指定左边的视图布局，`rightLayoutId` 指定右边的视图布局。

- 使用基类 `BGARecyclerViewHolder<DataModel0>`，其中模板指定了当前 `ViewHolder` 中使用的数据类型。

- 构造函数 `public BGAViewHolder0(BGASwipeItemLayout itemView, View leftContent, View rightContent, Context context, RecyclerView recyclerView) { ** }` 并不需要程序员主动调用。
	
- `getSwipeable` 接口返回值决定，当前的 ViewHolder 能否能滑动。

- `getSwipeDirection` 接口返回值决定，当前的 ViewHolder 是左滑还是右滑，当 `getSwipeable` 返回为 true 时生效。

- `getBottomModel` 接口返回值决定，当前的 ViewHolder 滑动后底层控件的出现效果

- `getSpringDistance` 接口返回值决定，当前的 ViewHolder 滑动时的弹簧距离

	- 其他内容同普通的 `ViewHolder` 的使用。

	
3. 定义 `AdapterItem` 类型 (同上)

4. 定义 SparseArray 变量关联 `ViewItemType` 常量与 `ViewHolder` 类型信息 (同上)

5. 初始化 `List<TAdapterItem>` 变量 (同上)

6. 初始化 `BGARecyclerViewAdapter ` 对象，并设置给 `RecyclerView`

	recycleAdapter = new BGARecyclerViewAdapter(target.getContext(),
				viewHolders, adapterItems);
	recycleView.setAdapter(recycleAdapter);
	

#### ViewHolder 的普通事件传递

每个 `ViewHolder` 类型都有一个变量 `listener`，通过 listener 将底部事件传递出来。

	if (listener != null) {
		listener.onEventNotify(ItemEventListener.longClickEventName, v, getAdapterPosition());
    }
	
外部接受监听的对象
	
	import com.netease.hearttouch.htrecyclerviewadapter.event.ItemEventListener;
	import com.netease.hearttouch.htrecyclerviewadapter.event.ItemEventHelper;
	public class MyActivity
	        extends Activity
	        implements ItemEventListener {
		
		public void onCreate() {
			mRecycleAdapter = new TRecyclerViewAdapter(target.getContext(), viewHolders, adapterItems);
			mRecycleAdapter.setItemEventListener(this);
		}

	    @Override
	    public boolean onEventNotify(String eventName, View view, int position, Object... values) {
	        if (ItemEventHelper.isClick(eventName)) {
	            onItemChildClick(view, position);
	        } else if (ItemEventHelper.isLongClick(eventName)) {
	            return onItemChildLongClick(view, position);
	        } else if (ItemEventHelper.isCheckedChanged(eventName)) {
	            onItemCheckedChanged((CompoundButton) view, (boolean) values[0]);
	        }
	        return true;
	    }
	}
	
**说明**

1. 通过 `listener` 属性的 `onEventNotify` 函数将事件传递出来

2. `ItemEventHelper` 类中预定义了 `click`, `longClick`, `checkedChanged` 3 个事件类型，若需要其他的类型，可自行定义使用

#### BGARecyclerViewHolder 的滑动相关的事件传递

	import com.netease.hearttouch.htrecyclerviewadapter.bga.BGASwipeItemLayout;
	public class MyActivity
	        extends Activity
	        implements BGASwipeItemLayout.BGASwipeItemLayoutDelegate {
		
		public void onCreate() {
			mRecycleAdapter = new BGARecyclerViewAdapter(target.getContext(), viewHolders, adapterItems);
	        mRecycleAdapter.setItemDelegate(this);
		}

	    @Override
	    public boolean onEventNotify(String eventName, View view, int position, Object... values) {
	        if (ItemEventHelper.isClick(eventName)) {
	            onItemChildClick(view, position);
	        } else if (ItemEventHelper.isLongClick(eventName)) {
	            return onItemChildLongClick(view, position);
	        } else if (ItemEventHelper.isCheckedChanged(eventName)) {
	            onItemCheckedChanged((CompoundButton) view, (boolean) values[0]);
	        }
	        return true;
	    }
	    
	    @Override
	    public void onBGASwipeItemLayoutOpened(BGASwipeItemLayout swipeItemLayout, int position) {
	    }
	
	    @Override
	    public void onBGASwipeItemLayoutClosed(BGASwipeItemLayout swipeItemLayout, int position) {
	    }
	
	    @Override
	    public void onBGASwipeItemLayoutStartOpen(BGASwipeItemLayout swipeItemLayout, int position) {
	    }
	}
	
**说明**

1. 通过 `setItemDelegate` 接口将可滑动 `ViewHolder` 将滑动的事件 (滑动打开完成，滑动关闭完成，滑动开始打开) 传递出来