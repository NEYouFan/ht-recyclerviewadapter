ht-recyclerviewadapter
===============

## RecyclerView的优点

`RecyclerView` 比 `listview` 更先进更灵活，对于很多的视图它就是一个容器，可以有效的重用和滚动

## RecyclerView的缺点

常规的写法，为了使用 `RecyclerView`，必须指定一个 `adapter` 和 一个 `LayoutManager`。其中自定义的 `adapter` 必须继承自 `RecyclerView.Adapter`，其中必须实现几个接口：

* public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)

	**说明** : 在这个接口当中根据类别创建 `ViewHolder` 和 inflate `layout`，由此常规的写法，就需要写 `if - else` 或者 `switch` 语句。

* public void onBindViewHolder(ItemViewHolder viewHolder, int position)

	**说明** : 在这个接口当中根据位置 `position` 取出对应的数据，并用于刷新 `viewHolder` 里面的 `UI`。

* public int getItemCount()

	**说明** : 在这个接口当中根据位置保存的数据数量指定列表控件 `item` 的数量

综上：

1. 上面的几个接口里面我们需要些的内容是非常机械，只要有一个统一的规范，那这个自定义的 `Adapter` 就可以不用写了，里面的逻辑可以分发到其他规定的地方，能够让代码逻辑更清晰

2. 其次 `onCreateViewHolder` 和 `onBindViewHolder` 2 个接口还不足以清晰直观的表达 `item view` 的创建还是复用

3. `Model` 和 `ViewHolder` 之间的映射关系没有做硬性规定

4. `item view` 和 外界 (如 `Activity`) 的通信机制没有做约定和实现

## TRecyclerViewAdapter

TRecyclerViewAdapter 是基于 [RecyclerView.Adapter](http://developer.android.com/intl/zh-cn/reference/android/support/v7/widget/RecyclerView.html) 的一个封装

1. 避免程序员去关心 `RecyclerView.Adapter` 的自定义派生类

2. 底层实现了 `Model` 和 `ViewHolder` 的映射关系

3. 定义了 `ViewHolder` 的使用模板

4. 定义了 `ViewHolder` 和模块外面的通信机制

5. 另外集成了 [AndroidSwipeLayout](https://github.com/daimajia/AndroidSwipeLayout) 左滑功能

## 6步基本使用 TRecyclerViewAdapter

1. 定义 `ViewHolder` 类型枚举值:
	
		public interface ViewItemType {
		    int ITEM_VIEW_HOLDER_0 = 0;
		    int ITEM_VIEW_HOLDER_1 = 1;
		    int ITEM_VIEW_HOLDER_2 = 2;
		}

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
		        return mDataModel == null ? 0 : mDataModel.hashCode();
		    }
		
		    @Override
		    public DataModel0 getDataModel() {
		        return mDataModel;
		    }
		}

4. 定义 SparseArray 变量关联 `ViewItemType` 常量与 `ViewHolder` 类型信息

		private final static SparseArray<Class<? extends TRecyclerViewHolder>> sViewHolders = new SparseArray<>();
		static {
		        sViewHolders.put(ViewItemType.ITEM_VIEW_HOLDER_0, ViewHolder0.class);
		        sViewHolders.put(ViewItemType.ITEM_VIEW_HOLDER_1, ViewHolder1.class);
		        sViewHolders.put(ViewItemType.ITEM_VIEW_HOLDER_2, ViewHolder2.class);
		}

5. 初始化 `List<TAdapterItem>` 变量

		private List<TAdapterItem> mTAdapterItems = new ArrayList<>();
		
		...
		
		mTAdapterItems.add(new AdapterItem0(new DataModel0()));
		...
		mTAdapterItems.add(new AdapterItem1(new DataModel1()));
		...
		mTAdapterItems.add(new AdapterItem2(new DataModel2()));

6. 初始化 `TRecyclerViewAdapter` 对象，并设置给 `RecyclerView`

		mRecyclerViewAdapter = new TRecyclerViewAdapter(context, sViewHolders, mTAdapterItems);
		recycleView.setAdapter(mRecyclerViewAdapter);


## TRecyclerViewAdapter 的集成
Gradle:

```
dependencies {
	compile 'com.netease.hearttouch:ht-recyclerviewadapter:0.0.1'
}
```

Maven:

```
<dependency>
    <groupId>com.netease.hearttouch</groupId>
    <artifactId>ht-recyclerviewadapter</artifactId>
    <version>0.0.1</version>
</dependency>
```

## ProGuard configuration

提交的aar包是已经经过混淆的，所以在工程的 proguard 文件中，添加如下代码：

```
-keep class com.netease.hearttouch.htrecyclerviewadapter.** { *; }
-dontwarn com.netease.hearttouch.htrecyclerviewadapter.**
```

## 开发文档
TRecyclerViewAdapter 的详细使用文档，请参见 [使用文档](https://github.com/NEYouFan/ht-recyclerviewadapter/blob/master/Guide.md)

## 版本发布历史

[CHANGELOG](https://github.com/NEYouFan/ht-recyclerviewadapter/blob/master/CHANGELOG.md)

## 许可证
`TRecyclerViewAdapter ` 使用 `MIT` 许可证，详情见 [LICENSE](https://github.com/NEYouFan/ht-recyclerviewadapter/blob/master/LICENSE.txt) 文件。