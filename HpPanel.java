import javax.swing.*;
import java.awt.*;

public class HpPanel extends JPanel {
    private int howKill = 0;
    private JLabel killScoreLabel = new JLabel(Integer.toString(howKill));//킬 출력
    private JLabel board = new JLabel("처치한 울트라");

    private JLabel hp = new JLabel("남은 체력");
    private int hpBarWidth = 200; // JLabel의 width가 된다
    private JLabel hpBar = new JLabel();

    public HpPanel() {
        setLayout(null);
        setBackground(Color.PINK);
        add(board);
        add(killScoreLabel);
        add(hp);
        add(hpBar);
        hpBar.setSize(hpBarWidth, 20);
        hpBar.setBackground(Color.GREEN);
        hpBar.setOpaque(true);
        hpBar.setLocation(60, 300);
        hp.setSize(100, 50);
        hp.setLocation(130, 250);


        board.setSize(100, 50);
        killScoreLabel.setSize(100, 50);
        board.setLocation(125, 0);
        killScoreLabel.setLocation(board.getX() + 35, board.getY() + 20 );
    }

    //정답 맞췄을 때 >> KILL+1
    public void increase() {
        howKill += 1;
        killScoreLabel.setText(Integer.toString(howKill));
    }

    //공격당하면 체력 감소
    public void decreaseHp() {
        if (hpBarWidth > 0) {
            hpBarWidth -= 10;
            hpBar.setSize(hpBarWidth, 20);
        }
    }
    //어려음
    public void hard() {
        hpBarWidth = 100;
        hpBar.setLocation(110, 300);
        hpBar.setSize(hpBarWidth, 20);

    }
    //보통
    public void normal() {
        hpBarWidth = 200;

        hpBar.setSize(hpBarWidth, 20);
    }
    //쉬움
    public void easy() {
        hpBarWidth = 300;
        hpBar.setLocation(15, 300);
        hpBar.setSize(hpBarWidth, 20);
    }

    public int getHp(){
        return hpBarWidth;
    }
    
    //킬수 리턴
    public int getKill(){
        return howKill;
    }
}
