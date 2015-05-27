package com.eltonkola.demoapp;

import android.os.AsyncTask;
import com.eltonkola.prefdatalibrary.PrefData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by elton on 5/7/15.
 */
public class MainModel {

    private static MainModel mInstance = null;

    private List<JokeElement> jokes = new ArrayList<>();

    public List<JokeElement> getData(){
        return jokes;
    }

    public PrefData<JokeElement> dataPref;

    private MainModel(){
        dataPref = new PrefData<JokeElement>(DemoApp.getContext(), JokeElement.class);
    }

    public void edit(JokeElement elem) {
        dataPref.update(elem);
    }

    public void delete(JokeElement elem) {
        dataPref.delete(elem);
    }


    public interface OnDataLoaded{
        public void onLoaded();
    }

    public void loadFromDatabase(final OnDataLoaded event){

        new AsyncTask<Void, Void, String>() {
            protected String doInBackground(Void... params) {

                //get all
                jokes = dataPref.getAll();

                //order objects
                if(jokes.size()>0) Collections.sort(jokes, new Comparator<JokeElement>() {
                    @Override
                    public int compare(JokeElement lhs, JokeElement rhs) {
                        return lhs.getCreation_date().compareTo(rhs.getCreation_date());
                    }
                });
                return null;
            }
            protected void onPostExecute(String msg) {
                event.onLoaded();
            }
        }.execute();


    }

    public static MainModel getInstance(){
        if(mInstance == null)
        {
            mInstance = new MainModel();
        }
        return mInstance;
    }

    public void addJoke(String title, String joke){
        JokeElement el = new JokeElement();
        el.setDescription(joke);
        el.setTitle(title);
        dataPref.create(el);
    }

}