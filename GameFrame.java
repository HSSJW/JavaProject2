import javax.swing.*;


public class GameFrame extends JFrame {

    //생성자
    public GameFrame(){
        super("스타크래프트 산성비");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GameIntroPanel g = new GameIntroPanel();


        setSize(1920, 1080);
        setVisible(true);
        setContentPane(g);


        //게임환경 로딩
    }
    //생성자

}

