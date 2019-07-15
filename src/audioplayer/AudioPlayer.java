// we are going to use Jlayer Library to play mp3;
package audioplayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class AudioPlayer {

    FileInputStream fis;
    public Player player;
    public long songLeft;
    public long SongLength;
    public String FilePathNew;
    public int loop;
    public String file;
    public int random;

//******************************************************************************        
    public void Play(String file) {

        try {
            fis = new FileInputStream(file);
            player = new Player(fis);
            SongLength = fis.available(); //this will give us the original length of the song;
            FilePathNew = file + "";

        } catch (FileNotFoundException ex) {
            System.out.println("FILE NOT FOUND");
        } catch (JavaLayerException ex) {
            System.out.println("JAVA LAYER EXCEPTION");
        } catch (IOException ex) {
            System.out.println("IO EXCEPTION");
        }

        // we need to have another thread here so that program lets you select other options as well
        new Thread() {

            @Override
            public void run() {
                try {
                    player.play(); //song will play
                    if(player.isComplete()  && loop==1){  //for looping a song
                        Play(FilePathNew);
                                
                    }
                    
                    
                } catch (JavaLayerException ex) {
                    System.out.println("JAVA LAYER EXCEPTION");
                }
            }
        }.start();

    }
//******************************************************************************    

    public void Stop() {
        if (player != null) {
            player.close();

            SongLength = 0;
            songLeft = 0;
        }
    }

//******************************************************************************        
    public void Pause() {
        if (player != null) {
            try {
                songLeft = fis.available(); //to check how much song is available 
            } catch (IOException ex) {
                System.out.println("IO EXCEPTION");
            }
            player.close();
        }
    }

//******************************************************************************          
    public void Resume() {

        try {
            fis = new FileInputStream(FilePathNew);
            player = new Player(fis);
            fis.skip(SongLength - songLeft);  //this will skip the song we have already listened;

        } catch (FileNotFoundException ex) {
            System.out.println("FILE NOT FOUND");
        } catch (JavaLayerException ex) {
            System.out.println("JAVA LAYER EXCEPTION");
        } catch (IOException ex) {
            System.out.println("IO EXCEPTION");
        }

        // we need to have another thread here so that program lets you select other options as well
        new Thread() {

            @Override
            public void run() {
                try {
                    player.play();
                } catch (JavaLayerException ex) {
                    System.out.println("JAVA LAYER EXCEPTION");
                }
            }
        }.start();

    }
//******************************************************************************

    public void LOOP() {
        {
            if (loop == 0) {
                loop = 1;
                System.out.println("LOOPING");
            } else if (loop == 1) {
                loop = 0;
                System.out.println("NOT LOOPING");
            }

        }}
        
        
    public void Random(){
       String []SONGS= {"8 - Birdy - Skinny Love.mp3","8 - Dynoro - In My Mind.mp3","9 - Bazzi - Beautiful (feat. Camila Cabello).mp3","9 - Michael Schulte - You Let Me Walk Alone.mp3"};
    
     int random= (int) (Math.random()*(SONGS.length-1));
     
     String NEWFILE= SONGS[random];
            Play(NEWFILE);
    }
       }
//******************************************************************************
