//Stanley Zhou
//December 16, 2024
//Code for playing sounds for soundtrack

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundTrack {
    
    public int volume; // 1 - 100

    // Method that plays music in loop for soundtrack
    // Credit to Max O'Didily and his youtube guide: https://www.youtube.com/watch?v=wJO_cq5XeSA&t=7s&ab_channel=MaxO%27Didily
    //                and additional playlist guide: https://www.youtube.com/watch?v=DkQFO6IMwi8&ab_channel=MaxO%27Didily
    // Modified guide slightly to fit needs of this program
    public static void setUp() {
 
        //create list of filepaths
        List<String> soundtrackList = new ArrayList<String>();
        soundtrackList.add("Sounds\\Plants vs Zombies Soundtrack. [Main Menu].wav");
        soundtrackList.add("Sounds\\Plants vs Zombies Soundtrack. [Day Stage].wav");
        soundtrackList.add("Sounds\\Plants vs Zombies Soundtrack. [Night Stage].wav");
        soundtrackList.add("Sounds\\Plants vs Zombies Soundtrack. [Pool Stage].wav");
        soundtrackList.add("Sounds\\Plants vs Zombies Soundtrack. [Roof Stage].wav");
        soundtrackList.add("Sounds\\Plants vs Zombies Soundtrack. [Fog Stage].wav");
        soundtrackList.add("Sounds\\Plants vs Zombies Music - Daytime in Back Yard (Horde).wav");
        soundtrackList.add("Sounds\\Plants vs Zombies Music - Night Time in Front Yard (Horde).wav");
        soundtrackList.add("Sounds\\Plants VS. Zombies Music_ Ultimate Battle.wav");
        soundtrackList.add("Sounds\\Plants vs Zombies Soundtrack. [Zomboss Stage].wav");

        int length = soundtrackList.size();
        try{
            for(int i = 0; i < length; i++) {
                Clip currentClip = PlayMusic(soundtrackList.get(i));
                while(currentClip.getMicrosecondLength() != currentClip.getMicrosecondPosition()) {
                    //empty while loop to wait until clip finishes playing
                } 

                if(i == length - 1) {
                    i = -1;
                }
            }
        } catch(Exception e) {
            System.out.println("Exception: Error with music playing.");
        }
    }

    private static Clip PlayMusic(String location) {
        try{
            File musicPath = new File(location);

            if(musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                return clip;
            } else {
                System.out.println("Error: Cannot locate file");
            }
        } catch(Exception e) {
            System.out.println("Exception: Error with music playing.");
        }
        return null;
    }


}


