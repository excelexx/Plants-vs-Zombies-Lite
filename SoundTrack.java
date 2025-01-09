//Alexander Zhang and Stanley Zhou
//2025-01-09
//Code for playing sounds for soundtrack

//imports
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//soundtrack class code
public class SoundTrack {
    //imports
    public static int volume; // 0 - 100
    static int length;
    static List<String> soundtrackList = new ArrayList<>();
    private static Clip currentClip;

    // Method that plays music in loop for soundtrack
    // Credit to Max O'Didily and his youtube guide: https://www.youtube.com/watch?v=wJO_cq5XeSA&t=7s&ab_channel=MaxO%27Didily
    //                and additional playlist guide: https://www.youtube.com/watch?v=DkQFO6IMwi8&ab_channel=MaxO%27Didily
    // Modified guide slightly to fit needs of this program
    public static void setUp() {
        refreshSoundtrackList();

        new Thread(() -> {
            try {
                int i = 0;
                while (true) {
                    if (i >= soundtrackList.size()) {
                        i = 0; // Loop the playlist
                    }

                    currentClip = playMusic(soundtrackList.get(i));
                    if (currentClip != null) {
                        while (currentClip.getMicrosecondLength() != currentClip.getMicrosecondPosition()) {
                            // Check if game status changes mid-song
                            if ((GamePanel.isRunning && length == 1) || (!GamePanel.isRunning && length != 1)) {
                                currentClip.stop();
                                refreshSoundtrackList();
                                i = 0; // Reset to start of the new list
                                currentClip = playMusic(soundtrackList.get(i));
                                continue;
                            }
                            // Empty while loop to wait until the clip finishes playing
                        }
                    } else {
                        System.out.println("Error: Failed to play music at index " + i);
                    }

                    i++;
                }
            } catch (Exception e) {
                System.out.println("Exception: Error with music playback.");
            }
        }).start();
    }

    //refreshes the list of soundtracks
    private static void refreshSoundtrackList() {
        soundtrackList.clear();
        if (!GamePanel.isRunning) {
            // Menu soundtrack
            soundtrackList.add("Soundtracks\\Plants vs Zombies Soundtrack. [Main Menu].wav");
        } else if(GamePanel.difficulty == 1){
            // In-game soundtrack
            soundtrackList.add("Soundtracks\\Plants vs Zombies Soundtrack. [Day Stage].wav");
            soundtrackList.add("Soundtracks\\Plants vs Zombies Soundtrack. [Night Stage].wav");
            soundtrackList.add("Soundtracks\\Plants vs Zombies Soundtrack. [Pool Stage].wav");
            soundtrackList.add("Soundtracks\\Plants vs Zombies Soundtrack. [Roof Stage].wav");
            soundtrackList.add("Soundtracks\\Plants vs Zombies Soundtrack. [Fog Stage].wav");
            soundtrackList.add("Soundtracks\\Plants vs Zombies Music - Daytime in Back Yard (Horde).wav");
            soundtrackList.add("Soundtracks\\Plants vs Zombies Music - Night Time in Front Yard (Horde).wav");
            soundtrackList.add("Soundtracks\\Plants VS. Zombies Music_ Ultimate Battle.wav");
            soundtrackList.add("Soundtracks\\Plants vs Zombies Soundtrack. [Zomboss Stage].wav");
        } else{
            //jazzy soundtrack
            soundtrackList.add("Soundtracks\\Graze The Roof Jazz.wav");
            soundtrackList.add("Soundtracks\\Grasswalk and Moongrains Jazz.wav");
            soundtrackList.add("Soundtracks\\Loonboon Jazz.wav");
            soundtrackList.add("Soundtracks\\Battle For Brains Jazz.wav");
        }

        length = soundtrackList.size();
    }
    //plays music
    private static Clip playMusic(String location) {
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


