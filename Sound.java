//Alexander Zhang and Stanley Zhou
//2025-01-17
//Code for playing game action sounds

// Credit to Max O'Didily and his youtube guide: https://www.youtube.com/watch?v=wJO_cq5XeSA&t=7s&ab_channel=MaxO%27Didily
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

    //declares all variables
    public static int volume; // 0 - 100
    static boolean playingZombieGroans = false;
    static boolean playingEatingSounds = false;

    //sets the volume
    public void setVolume(int v) {
        volume = v;
    }

    //plays the sound
    public static Clip playSingleSound(String location, float volume) {
        try {
            //take in filepath
            File musicPath = new File(location);

            //check to ensure filepath is valid
            if (musicPath.exists()) {
                //file path valid
                //set up audio clip
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput); //open audio clip

                // Adjust the volume
                FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN); //also made with help of max o didily video above
                volume = Math.min(volume, 6.0206f); // Max volume allowed by control
                volume = Math.max(volume, volumeControl.getMinimum()); // Minimum volume allowed by control
                volumeControl.setValue(volume);

                clip.start(); //start playing music clip
                GamePanel.soundPlayed = false;
                return clip;
            }
            /* else {
                System.out.println("Error: Cannot locate file");
            } */
        } catch (Exception e) {
            //file path invalid, print error
            System.out.println("Exception: Error with music playing.");
        }

        //returns null
        return null;
    }

    //repeats the sound
    public static void playRepeatSounds(String filePath) {
        try {
            //take in filepath
            File musicPath = new File(filePath);

            //check if file path valid
            if (musicPath.exists()) {
                // Plays music
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip(); // load clip
                clip.open(audioInput); //open clip
                clip.start(); //start playing clip
                clip.loop(Clip.LOOP_CONTINUOUSLY); //plays continuously, repeats when song ends 
            } else {
                // file path invalid
                System.out.println("Cannot Find File: please ensure code has filepath correctly entered.");
            }

        } catch (Exception e) {
            // All other errors
            System.out.println("Exception: Error with music playing.");
        }
    }

    //plays zombie groans
    public static void playZombieGroans() {
        if (!playingZombieGroans && GamePanel.isRunning) {
            playingZombieGroans = true;
            playRepeatSounds("Sounds\\groan.wav"); //play repeatedly
            //note currently playingZombieGroans is never turned back to false
        }
    }

    //plays eating sounds
    public static void playEatingSounds() {
        //check if requirements to play eating sound are valid
        if (!playingEatingSounds && GamePanel.isRunning) {
            //play the single eating sound as long as requirements are valid
            playSingleSound("Sounds\\eat.wav", -10);
        }
    }

}
