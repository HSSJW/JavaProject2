import javax.sound.sampled.*;
import java.io.*;
import java.lang.*;

public class BgmThread extends Thread {
    private Clip clip;
    private String filePath = null;
    BgmThread me = this;

    //생성자
    public BgmThread(String filePath){
        this.filePath = filePath;
    }

    private void bgmPlay(String filePath){
        try {
            clip = AudioSystem.getClip(); //빈 클립생성
            File audioFile = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            clip.open(audioInputStream);

        }
        catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {e.printStackTrace();}

    }


  //  bgm재생
    public void startBgm() {
        clip.start();
    }
    //bgm 정지
    public void stopBgm(){
        clip.stop();
    }

    public void playSound() {

    }


    @Override
    public void run() {
        bgmPlay(filePath);
        clip.start();
    }
}
