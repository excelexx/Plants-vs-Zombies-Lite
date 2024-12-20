//Stanley Zhou and Alexander Zhang
//18-12-2024
//Code for playing game action sounds

// Credit to Max O'Didily and his youtube guide: https://www.youtube.com/watch?v=wJO_cq5XeSA&t=7s&ab_channel=MaxO%27Didily
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

    public static int volume; // 0 - 100

    public static Clip playMusic(String location) {
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
