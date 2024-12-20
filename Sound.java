//Stanley Zhou and Alexander Zhang
//18-12-2024
//Code for playing game action sounds

// Credit to Max O'Didily and his youtube guide: https://www.youtube.com/watch?v=wJO_cq5XeSA&t=7s&ab_channel=MaxO%27Didily
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

    public static int volume; // 0 - 100
    static boolean playingZombieSounds = false;
    static boolean playingEatingSounds = false;

    public static Clip playSingleSound(String location, float volume) {
        try{
            File musicPath = new File(location);

            if(musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);

                // Adjust the volume
                FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volume = Math.min(volume, 6.0206f); // Max volume in decibels (6 dB)
                volume = Math.max(volume, volumeControl.getMinimum()); // Minimum volume allowed by control
                volumeControl.setValue(volume);

                clip.start();
                GamePanel.soundPlayed = false;
                return clip;
            } else {
                System.out.println("Error: Cannot locate file");
            }
        } catch(Exception e) {
            System.out.println("Exception: Error with music playing.");
        }
        
        return null;
    }

    public static void playRepeatSounds(String filePath) {
        try {
            File musicPath = new File(filePath);

            if(musicPath.exists()) {
                // Plays music
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY); //plays continuously, repeats when song ends 
            } else {
                // Error: cannot find file path
                System.out.println("Cannot Find File: please ensure code has filepath correctly entered.");
            }

        } catch(Exception e) {
            // All other errors
            System.out.println("Exception: Error with music playing.");
        }
    }

    public static void playZombieSounds() {
        if(!playingZombieSounds && GamePanel.isRunning) {
            playingZombieSounds = true;
            playRepeatSounds("Sounds\\Plants vs. Zombies - Groan 4 Sound Effect.wav");
            //note currently playingzombiesounds is never turned back to false
        }
    }

    public static void playEatingSounds() {
        if(!playingEatingSounds && GamePanel.isRunning) {
            playSingleSound("Sounds\\Plants vs Zombies eating sound - Made with Clipchamp.wav", -10);
        }
    }

}
