package com.eltonkola.demoapp;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    private static MainPresenter presenter;
    private RecyclerView list;

    private SwipeRefreshLayout swipe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showLoading();

        list = (RecyclerView)findViewById(R.id.list);
        swipe = (SwipeRefreshLayout)findViewById(R.id.swipe);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        list.setLayoutManager(layoutManager);

        swipe.setRefreshing(true);

        swipe.setColorScheme(android.R.color.holo_blue_dark, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_green_light);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);

                presenter.reload();

            }
        });


        if (presenter == null)
            presenter = new MainPresenter();
        presenter.onTakeView(this);

    }

    public void showToast(String msg){
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideLoading();
        presenter.onTakeView(null);
        if (isFinishing())
            presenter = null;
    }

    public void loadData(final List<JokeElement> items) {

        JokeAdapter listAdapter = new JokeAdapter(MainActivity.this, items);
        listAdapter.setOnItemClickListener(new JokeAdapter.OnItemClickListenerVk() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(View view, int position) {
                //on lick do something
                presenter.onClicked(items.get(position));
            }

            @Override
            public void onItemLongClick(View view, int position) {
                presenter.onLongClicked(items.get(position));
            }


        });

        if(list!=null)list.setAdapter(listAdapter);

        hideLoading();

        swipe.setRefreshing(false);
    }

    public void showDialog(String title, String desc, String yes, String no, final MainPresenter.DialogEvents events){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(desc);

        builder.setPositiveButton(yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                events.yesPlease();
                dialog.dismiss();
            }

        });

        builder.setNegativeButton(no, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                events.noPlease();
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }


    public void onItemsError(String msg) {
        hideLoading();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            //dialog add
            showAdDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showAdDialog() {

        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.add_dialog, null);

        final EditText title = (EditText)layout.findViewById(R.id.title);
        final EditText joke = (EditText)layout.findViewById(R.id.joke);



        new AlertDialog.Builder(this)
                .setView(layout)
                .setTitle("New Joke")
                .setPositiveButton("Create", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int whichButton){
                        if(joke.getText().toString().length()>0 && joke.getText().toString().length()>0){
                            presenter.addJoke(title.getText().toString(), joke.getText().toString());
                        }else{
                            Toast.makeText(MainActivity.this, "Write something..", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).show();

    }


    private ProgressDialog progress;

    public void showLoading(){
        if(progress!=null){
            progress.show();
        }else{
            try {
                progress = ProgressDialog.show(MainActivity.this,"Please wait...", "Retrieving data ...", true);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void hideLoading(){
        if(progress!=null){
            progress.dismiss();
//            progress.hide();
        }
    }
}
