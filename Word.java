package com.example.puspakbiswas.miwok;

/**
 * Created by Puspak Biswas on 05-11-2017.
 */

public class Word {

    private String mMiwokTranslation;
    private String mDefaultTranslation;
    private int mImage;
    private int mSound;

    public Word(String m, String d, int s){
        mMiwokTranslation = m;
        mDefaultTranslation = d;
        mImage = -1;
        mSound=s;
    }

    public Word(String m, String d, int i, int s){
        mMiwokTranslation = m;
        mDefaultTranslation = d;
        mImage = i;
        mSound=s;
    }

    public String getMiwokTranslation(){
        return mMiwokTranslation;
    }

    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }

    public int getImage(){
        return mImage;
    }

    public int getSound(){return mSound;}
}


