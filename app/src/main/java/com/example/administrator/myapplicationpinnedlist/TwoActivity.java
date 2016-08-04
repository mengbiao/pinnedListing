package com.example.administrator.myapplicationpinnedlist;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TwoActivity extends AppCompatActivity {


    private PinnedSectionListView listView;
    private ArrayList<Item> items = null;
    //当前的点击位置
    public int mCurrentPositon=-1;
    //上一次的点击位置
    public int mLastPositon=-2;
    MyPinnedSectionListAdapter adpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (PinnedSectionListView) findViewById(android.R.id.list);
        items = new ArrayList<Item>();

        //标题数组
        String[] groups = new String[] { "A", "B", "C", "D", "E", "F","G", "H" };
        for (int i = 0; i < groups.length; i++) {

            String s = groups[i];

            Item group = new Item();
            group.type = Item.GROUP;
            group.text = s;
            items.add(group);

            for (int j = 0; j < 5; j++) {

                Item child = new Item();
                child.type = Item.CHILD;
                child.text = s + " 组数据:" + j;
                items.add(child);
            }
        }
        //去掉阴影
        listView.setShadowVisible(false);
        adpter=new MyPinnedSectionListAdapter(TwoActivity.this, -1);
        listView.setAdapter(adpter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //标注出点击的position
                mCurrentPositon=position;
                //刷新列表
                adpter.notifyDataSetChanged();
            }
        });
    }



    // 用于承载数据块的类。
    // 字段分为类型（type）和值（text）。
    private class Item {
        public static final int GROUP = 0;
        public static final int CHILD = 1;

        public int type;
        public String text;
    }








    // Our adapter class implements 'PinnedSectionListAdapter' interface
    class MyPinnedSectionListAdapter extends ArrayAdapter<Item> implements PinnedSectionListView.PinnedSectionListAdapter {

        private LayoutInflater inflater;

        public MyPinnedSectionListAdapter(Context context, int resource) {
            super(context, resource);

            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        // We implement this method to return 'true' for all view types we want
        // to pin
        @Override
        public boolean isItemViewTypePinned(int viewType) {

            boolean type = false;
            switch (viewType) {
                case Item.GROUP:
                    type = true;
                    break;
                case Item.CHILD:
                    type = false;
                    break;
                default:
                    type = false;
                    break;
            }

            return type;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Item getItem(int position) {
            Item item = items.get(position);
            return item;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            Log.e("------>>","getView");
              Log.e("--positon-1->>",""+position);

            ViewHolder holder;
            //获取类型
            int type = this.getItemViewType(position);

//            if (type == Item.GROUP) {
//                convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
//                TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
//
//                tv.setText(getItem(position).text);
//                tv.setBackgroundColor(Color.RED);
//
//                // 将背景设置为半透明，更好的观察效果。
//                tv.getBackground().setAlpha(128);
//            }
//
//            if (type == Item.CHILD) {
//                convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
//                TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
//                tv.setText(getItem(position).text);
//            }

            if (convertView==null)
            {
                if (type == Item.GROUP)
                {
                    holder=new ViewHolder();
                    convertView = inflater.inflate(R.layout.group_layout_item, null);
                    holder.date = (TextView) convertView.findViewById(R.id.date);
                    holder.date.setBackgroundResource(R.color.write_light);
                    //date_tv.setText("2015-10-66");
                }else  //if (type == Item.CHILD)
                {

                    holder=new ViewHolder();
                    convertView = inflater.inflate(R.layout.child_layout_item, null);
                    holder.result_Tv  = (TextView) convertView.findViewById(R.id.result_tv);
                    //result_tv.setText("交易成功");

                }
                convertView.setTag(holder);
            }else
            {
                holder= (ViewHolder) convertView.getTag();
            }
            if (type == Item.GROUP)
            {
                //在此处判断是否，点击展开
                holder.date.setText("2015-10-"+position);
            }else
            {
                //对点击事件作出回应
                if (mCurrentPositon==position  )
                {

                    mLastPositon=mCurrentPositon;
                    //展开
                        holder.result_Tv.setText("交易失败");

                   //     Log.e("--mCurrentPositon-2->>",""+mCurrentPositon);
                  //  Log.e("--positon-2->>",""+position);



                }
                else if (mCurrentPositon==position && mLastPositon==mCurrentPositon)
                {
                  //  Log.e("--mCurrentPositon-3->>",""+mCurrentPositon);
                 //   Log.e("--positon-3->>",""+position);

                    mLastPositon=-2;
                    //关闭
                    holder.result_Tv.setText("交易成功");
                }
                else if (!(mCurrentPositon==position))
                {
                  //  Log.e("--mCurrentPositon-4->>",""+mCurrentPositon);
                  //  Log.e("--positon-4->>",""+position);

                    //关闭
                    holder.result_Tv.setText("交易成功");
                }
            }
            return convertView;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            return getItem(position).type;
        }
    }
    public final class ViewHolder
    {
      public TextView date;
        //child
        public ImageView resultIcon;
        public ImageView logolIcon;
        public TextView result_Tv;
        public TextView result_Class;
        public TextView pay_Money;
        //zhan kai xiang


    }
}
