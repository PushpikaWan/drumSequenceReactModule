package com.drumsequence.mydrumsequence;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import java.util.Map;

import android.media.MediaPlayer;
import android.media.AudioManager;
import android.media.SoundPool;


public class MyDrumSequenceModule extends ReactContextBaseJavaModule {

    // attributes
    private int rows = 4; // no. of samples
    private int beats = 8; // no. of time divisions
    private int[] samples = new int[4] ; // array of samples
    private int bpm = 200;
    private SoundPool sound;
    private Runnable playback;
    private boolean playing = false;
    private int[][] matrix = null;
    private int i,j,samplesCount=4;
    private ReactApplicationContext context;

    public MyDrumSequenceModule(ReactApplicationContext reactContext) {
        super(reactContext);
        context = reactContext;
    }

    @Override
    public String getName() {
        return "MyDrumSequence";
    }


    //notes is a string which conatins  1-d array (samplesCount*beats)
    //------------------
    //0,1,2,3,4,5,6,7,
    //8,9,10,11,12,13,14,
    //
    //
    //
    //------------------- 
    // 1 - enableNotes and 0 - disable notes
    @ReactMethod
    public void initalizeSoundMatirx(int beats,int bpm_val,String notes){ //samples constant
      matrix = new int[samplesCount][beats];
      bpm = bpm_val;
      for(i=0;i<samplesCount;i++){
        for(j=0;j<beats;j++){
            if (notes.charAt(i*beats+j) == '1'){
              matrix[i][j] = 1;
            }
            else{
              matrix[i][j] = 0;
            }
        }
      }
    }
    

    @ReactMethod
    public void play() {
        sound = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);

        playing = true;
        samples[0] = sound.load(context,com.drumsequence.R.raw.bass, 1);
        samples[1] = sound.load(context,com.drumsequence.R.raw.hhc, 1);
        samples[2] = sound.load(context,com.drumsequence.R.raw.hho, 1);
        samples[3] = sound.load(context,com.drumsequence.R.raw.snare, 1);

        try{
            // play sound periodically
            playback = new Runnable() {
                int count = 0;

                public void run() {

                    while (playing) {
                        long millis = System.currentTimeMillis();
                        for (int i = 0; i < rows; i++) {
                            System.out.println("Row-COl " + i + "-" + count);
                            System.out.println("ROW data"+String.valueOf(samples[i]));
                            if (matrix[i][count] != 0)
                                sound.play(samples[i], 100, 100, 1, 0, 1);
                        }

                        count = (count + 1) % beats;
                        long next = (60 * 1000) / bpm;
                        try {
                            Thread.sleep(next - (System.currentTimeMillis() - millis));
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            };

            playing = true;
            Thread thandler = new Thread(playback);
            thandler.start();

        }catch(Exception e){}
    }

    @ReactMethod
    public void pause(){
        try{
            playing = false;
        }catch(Exception e){}
    }

}
