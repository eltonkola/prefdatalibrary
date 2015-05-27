package com.eltonkola.prefdatalibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by elton on 5/12/15.
 */
public class PrefData<T> {


    //eveything else

    private Context mContext;
    private SharedPreferences sharedPreferences;
     private Gson gson = new Gson();

    private final String NEXT_ID = "nextId";
    private String ELEM_;
    private final String ELEM_ID = "id";

    private Class<T> mType;


    public PrefData(Context context, Class<T> type){
        this.mContext= context;
        this.mType=type;
        ELEM_ = "elem_" + mType.getName();
        Utils.log("--------------------name used:" + ELEM_);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public T create(T elem){
        SharedPreferences.Editor ed = sharedPreferences.edit();

        int id = getNextId();

        JsonParser parser = new JsonParser();
        JsonObject o = (JsonObject)parser.parse(gson.toJson(elem));
        o.addProperty(ELEM_ID, id);

        ed.putString(ELEM_ + id, o.toString());
        //set created id
        ((BaseType)elem).setId(id);
        ed.commit();
        incrementId();

        return elem;
    }

    public void update(T elem){
        SharedPreferences.Editor ed = sharedPreferences.edit();
        JsonParser parser = new JsonParser();
        JsonObject o = (JsonObject)parser.parse(gson.toJson(elem));
        o.addProperty(ELEM_ID, ((BaseType) elem).getId());
        ed.putString(ELEM_ + ((BaseType) elem).getId(), o.toString());
        ed.commit();
    }

    public void delete(T elem){
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString(ELEM_ + ((BaseType)elem).getId() , null);
        ed.commit();
    }

    public List<T> getAll(){
        List<T> res = new ArrayList<>();
        //scann for all and check if i have data
        for(int i = 0 ; i< getNextId();i++){
            String inputElem = sharedPreferences.getString(ELEM_ + i, null);
            if(inputElem!=null){
                T newElem = gson.fromJson(inputElem, mType);
                Utils.log("loaded :" + newElem + "-from:" + inputElem);
                res.add(newElem);
            }else{
                Utils.log("nothing for key:" + i);
            }
        }
        return res;
    }

    public T getById(int i){
        List<T> res = getAll();
        for(T a: res){
            if(((BaseType)a).getId() == i) return a;
        }
        return null;
    }

    private synchronized int getNextId(){
        return sharedPreferences.getInt(NEXT_ID, -1) + 1;
    }

    private synchronized void incrementId(){
        int val =  getNextId();
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putInt(NEXT_ID, val);
        ed.commit();
    }


}
