package com.example.puspakbiswas.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {


    private AudioManager mAudioManager;
    private MediaPlayer mPlayer;
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mPlayer.start();
            }
            if(focusChange==AudioManager.AUDIOFOCUS_LOSS){
                releaseMedia();
            }
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT){
                mPlayer.pause();
            }
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mPlayer.pause();
            }
        }
    };
    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMedia();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        mAudioManager= (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("one", "lutti", R.drawable.party, R.raw.loststars));
        words.add(new Word("two", "otiiko", R.drawable.party, R.raw.eminem));

        //ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,words);
        WordAdapter itemsAdapter = new WordAdapter(this, words);
        ListView listView = (ListView) findViewById(R.id.list);
        ImageView imageView = (ImageView) findViewById(R.id.img);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMedia();
                Word w = words.get(position);
                int focus= mAudioManager.requestAudioFocus(mAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
                if(focus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mPlayer = MediaPlayer.create(NumbersActivity.this, w.getSound());
                    mPlayer.start();
                    mPlayer.setOnCompletionListener(completionListener);

                }

            }
        });


    }




    protected void onStop(){
        super.onStop();
        releaseMedia();
    }


    public void releaseMedia(){
        if (mPlayer != null){
            mPlayer.release();
            mPlayer=null;
            mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
        }
    }
}
