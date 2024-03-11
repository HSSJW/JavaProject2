import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class PlayerPanel extends JPanel {
    private GameGround gameGround = null;
    private MakeEnemyThread makeEnemyThread = null;
    private BgmThread bgmThread = new BgmThread("newClear.wav");

    //마린 초상화
    private ImageIcon marine = new ImageIcon("marine2.gif");
    private  JLabel playerImage = new JLabel(marine);
    //뉴클리어 버튼
    private ImageIcon newClear = new ImageIcon("newClear.png");
    private JButton newClearBtn = new JButton(newClear);
    private ImageIcon bombImg = new ImageIcon("explosion.gif");
    private JLabel bomb = new JLabel(bombImg);
    private boolean doBomb = false; //한번만 사용할 수있게

    //생성자
    public PlayerPanel(GameGround gameGround) {
        setLayout(null);//구분 배치관리자
        this.gameGround = gameGround;
        makeEnemyThread = gameGround.getMakeEnemyThread(); //MakeEnemyThread 가져옴

        setBackground(Color.GRAY);
        add(playerImage); //초상화 추가
        add(newClearBtn);//뉴클리어 버튼 추가
        playerImage.setSize(marine.getIconWidth(), marine.getIconHeight());
        newClearBtn.setSize(newClear.getIconWidth(), newClear.getIconHeight());
        playerImage.setLocation(100, 0);
        newClearBtn.setLocation(110, 180);

        //이미지 외 버튼모양 없애기
        newClearBtn.setBorderPainted(false);
        newClearBtn.setContentAreaFilled(false);
        newClearBtn.setFocusPainted(false);

        bomb.setSize(bombImg.getIconWidth(), bombImg.getIconHeight());
        bomb.setLocation(gameGround.getWidth()/2, gameGround.getHeight()/2);

        //버튼 이벤트리스너 추가
        newClearBtn.addMouseListener(new MouseAdapter() {

            @Override //클릭했을 때 >> 핵 이미지 떨어지면 몬스터 전체 삭제
            public void mouseClicked(MouseEvent e) {
                if(doBomb == false) {
                    bgmThread.start(); //효과음

                    gameGround.playGifOnce();
                    newClearBtn.setIcon(new ImageIcon("blackNewClear.png"));
                    //현존하는 울트라 스레드 모두 interrupt
                    makeEnemyThread.destoryUltra(); //울트라 삭제
                    doBomb = true;
                }
            }
        });
    }

}
