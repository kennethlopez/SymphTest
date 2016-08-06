package com.example.symph.symphtest;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.symph.symphtest.adapter.UsersActivityAdapter;
import com.example.symph.symphtest.database.UserTable;
import com.example.symph.symphtest.helper.DividerItemDecoration;
import com.example.symph.symphtest.helper.RecyclerTouchListener;
import com.example.symph.symphtest.object.User;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity{

    RecyclerView listView;

    ArrayList<User>list;
    UsersActivityAdapter adapter;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        listView= (RecyclerView) findViewById(R.id.activity_user_list_users);
        relativeLayout= (RelativeLayout) findViewById(R.id.activity_user_list_layout);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);

        list=new ArrayList<>();
        adapter=new UsersActivityAdapter(getApplicationContext(),list);
        listView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        listView.setAdapter(adapter);
        listViewTouchListener();

        try{
            UserTable userTable=new UserTable(this);
            ArrayList<User>users=userTable.getAllUsers();
            for(int c=0;c<users.size();c++)
                list.add(users.get(c));
            adapter.notifyDataSetChanged();
        }
        catch (SQLiteException e){
            e.printStackTrace();
        }
    }

    public void listViewTouchListener(){
        listView.addOnItemTouchListener(new RecyclerTouchListener(this,listView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(UsersActivity.this,DrawerActivity.class);
                intent.putExtras(list.get(position).toBundle());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}
