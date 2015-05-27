package com.eltonkola.demoapp;

import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by elton on 5/6/15.
 */
public class MainPresenter {

    private MainActivity mView;
    private MainModel mModel;

    public MainPresenter() {
        mModel = MainModel.getInstance();
        loadData();
    }

    public void onClicked(JokeElement elem){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Joke: " + elem.getTitle() + " - " +elem.getDescription());
        sendIntent.setType("text/plain");
        DemoApp.getContext().startActivity(sendIntent);
    }

    public void onLongClicked(final JokeElement elem){

            //alert sure want to delete?
            mView.showDialog("Delete file", "Are you sure you want to delete the file?", "Yes", "No", new DialogEvents() {
                @Override
                public void yesPlease() {
                    mModel.delete(elem);
                    mView.showToast("File deleted!");
                    mView.showLoading();
                    reload();
                }
                @Override
                public void noPlease() {
                    //do nothing
                    mView.showToast("File not deleted!");
                }
            });

    }

    public interface DialogEvents{
        public void yesPlease();
        public void noPlease();
    }

    public void onTakeView(MainActivity view) {
        this.mView = view;
        if (mModel.getData().size()>0)publish();

    }

    public void loadData(){

        if(mModel.getData().size()==0) mModel.loadFromDatabase(new MainModel.OnDataLoaded() {
            @Override
            public void onLoaded() {
                publish();
            }
        });
    }

    public void reload(){

        mModel.loadFromDatabase(new MainModel.OnDataLoaded() {
            @Override
            public void onLoaded() {
                publish();
            }
        });
    }


    private void publish() {
        if (mView != null) {
//            if (mModel.getData().size()>0)
                mView.loadData(mModel.getData());
//            else
//                mView.onItemsError("No data :)");
        }
    }

    public void addJoke(String title, String joke){
        mModel.addJoke(title, joke);
        reload();
    }

}