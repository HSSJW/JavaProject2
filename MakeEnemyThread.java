import javax.swing.*;
import java.awt.*;

import java.util.Random;
import java.util.Vector;

public class MakeEnemyThread extends Thread {
    private GameGround gameGround = null;
    private HpPanel hpPanel = null;
    private TextSource textSource = null;
    private PlayerPanel playerPanel = null;
    private GamePanel gamePanel = null;

    //빈 쓰레드 생성
    private UltraliskThread ultraliskThread = null;
    //울트라 리스크 쓰레드가 들어갈 벡터
    private Vector<UltraliskThread> ultraThreadVector = new Vector<UltraliskThread>();

    //울트라 리스크 라벨 >> UltraliskThread() 호출할 때 사용
    private ImageIcon ultraImg = new ImageIcon("ultraliskWalking.gif");
    private JLabel ultraLabel = new JLabel(ultraImg); //울트라 라벨
    private Vector<JLabel> ultraLabelVector = new Vector<JLabel>();
    private BgmThread finishBgm = new BgmThread("marineDie.wav");
    private int count = 0; //울트라의 개수를 센다
    private int delay = 2000; //울트라 생성 간격 조절에 사용 >> 줄이면 난이도 상승

    MakeEnemyThread(GameGround gameGround, TextSource textlist, HpPanel hpPanel, PlayerPanel playerPanel, GamePanel gamePanel) {
        this.gameGround = gameGround;
        this.hpPanel = hpPanel;
        this.textSource = textlist;
        this.playerPanel = playerPanel;
        this.gamePanel = gamePanel;

        //울트라리스크 라벨 벡터 초기화
        ultraLabel.setSize(ultraImg.getIconWidth(), ultraImg.getIconHeight());
        gameGround.add(ultraLabel);

    }
    @Override
    public void run(){
        while (true) {
            whenDie(); //남은 체력이 0

                try {
                    sleep(delay);
                } catch (InterruptedException e) {
                    return;
                }

                ultraLabelVector.add(new JLabel(ultraImg));//울트라 이미지가 들어간 라벨 벡터 하나씩 생성
                ultraLabelVector.get(count).setSize(ultraImg.getIconWidth(), ultraImg.getIconHeight());
                gameGround.add(ultraLabelVector.get(count));

                int y = new Random().nextInt(600);
                ultraLabelVector.get(count).setLocation(gameGround.getWidth()- 150, y);
                ultraThreadVector.add(new UltraliskThread(ultraLabelVector.get(count), gameGround, textSource, hpPanel));
                ultraThreadVector.get(count).start();
                count++;

        }

    }

    //울트라쓰레드벡터의 단어와 사용자가 지금 입력한 값(매개변수)가 같으면 true리턴
    public boolean checkWord(String input) {
        //모든 울트라 스레드를 돌면서 단어가 맞는지 체크
        for (int i = 0; i < count; i++) {
            String now = ultraThreadVector.get(i).getUltraText(); //이 쓰레드의 단어
            if (input.equals(now)) {
                ultraThreadVector.get(i).setUltraText();//맞춘 벡터의 텍스트는 이상한값으로 바꿈
                ultraThreadVector.get(i).interrupt();//맞았으면 쓰레드 정지 >> 정지하면 UltraliskThread내부 catch문에서 라벨 삭제
                System.out.println("죽였다.");
                return true; //정답
            }
        }
        return false; //오답

    }

    public void whenDie(){
        if(hpPanel.getHp() <= 0){ // 죽었을 때

            finishBgm.start();

            gamePanel.setVisible(false);
            gamePanel.getParent().add(new GameFinishPanel(hpPanel, textSource), BorderLayout.CENTER);
            gameFinish(); //모든 쓰레드 종료
        }
    }

    //지금 존재하는 모든 울트라스레드를 죽이고 자신도 정지한다.
    public void gameFinish(){
        for(int i = 0 ; i < count; i++){ //현재가지 존재하는 모든 울트라쓰레드 삭제
            ultraThreadVector.get(i).interrupt();
        }

        this.interrupt();
    }

    //현재 존재하는 모든 울트라쓰레드 삭제 및 count 0 으로 초기화
    public void destoryUltra() {
        for(int i = 0 ; i < count; i++){ //현재가지 존재하는 모든 울트라쓰레드 삭제
            ultraThreadVector.get(i).interrupt();
        }
        ultraThreadVector.clear();
        count = 0;
    }
}