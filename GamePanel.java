import javax.swing.*;
import java.awt.*;

//패널 3개 (GameGround 좌측, HpPanel 우상단, PlayerPanel 우하단) 배치만
public class GamePanel extends JPanel {
    private HpPanel hpPanel = null;
    private PlayerPanel playerPanel = null;
    private GameGround gameGround = null;

    //생성자
    public GamePanel(HpPanel hpPanel) {
        setLayout(new BorderLayout());
        setSize(1920, 1080);
        this.hpPanel = hpPanel;
        gameGround = new GameGround(hpPanel, playerPanel, this);
        playerPanel = new PlayerPanel(gameGround);
        splitPanel();

    }
    //패널을 3개로 분리한다
    private void splitPanel() {
        JSplitPane hPane = new JSplitPane(); //좌우로(게임/ hp,player) 분리한 팬 생성
        hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT); //좌우로 분리
        hPane.setDividerLocation(1200);
        add(hPane); //GamePanel에 분리한 팬 추가

        JSplitPane vPane = new JSplitPane();
        vPane.setOrientation(JSplitPane.VERTICAL_SPLIT); //상하로 분리
        vPane.setDividerLocation(500);
        vPane.setTopComponent(hpPanel);
        vPane.setBottomComponent(playerPanel);
        vPane.getBottomComponent().setSize(720, 500);
        hPane.setRightComponent(vPane);

        
        //왼쪽팬(게임진행)부분에 GameGround부착
        hPane.setLeftComponent(gameGround);
    }
}
