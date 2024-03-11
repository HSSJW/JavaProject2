import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//게임 시작화면
//배경화면 : 히페리온
public class GameIntroPanel extends JPanel {
    //난이도 설정을 위해 인트로 패널에서 HpPanel선언
    private HpPanel hpPanel = new HpPanel();

    private ImageIcon introBackground = new ImageIcon("gameIntroBackground.jpg");
    private Image backgroundImage = introBackground.getImage(); //배경화면이 될 이미지
    //게임시작버튼
    private JButton gameStartBtn = new JButton(new ImageIcon("gameStartbutton1.png"));

    //단어 추가 기능
    private JTextField wordPlusField = new JTextField(20); //추가할 단어 입력받기
    private TextSource textSource = new TextSource(); //멤버 함수사용을 위해 선언
    private JButton wordPlusBtn = new JButton("단어 추가"); //이 버튼을 누르면 textSource.addWord 호출해서 단어 추가

    //난이도
    private JButton hard = new JButton("HARD");
    private JButton normal = new JButton("NORMAL");
    private JButton easy = new JButton("EASY");

    private JLabel tileName = new JLabel("ULTRA DEFENSE");
    private Font title = new Font("Gill Sans", Font.ITALIC, 100);



    //생성자
    GameIntroPanel(){
        GameIntroPanel introPanel = this;
        new BgmThread("bgm.wav").start(); //배경음악 시작
        setLayout(null);


        tileName.setSize(900, 200);
        tileName.setLocation(420, 250);
        tileName.setForeground(Color.RED);
        tileName.setFont(title);
        add(tileName);

        add(gameStartBtn); //게임 시작 버튼 부착
        gameStartBtn.setRolloverIcon(new ImageIcon("gameStartbutton2.png"));
        gameStartBtn.setBorderPainted(false);//버튼 외곽선 지우기
        gameStartBtn.setContentAreaFilled(false); //버튼 채우기 지우기
        gameStartBtn.setFocusPainted(false);
        gameStartBtn.setSize(300, 100);
        gameStartBtn.setLocation(600, 680); //버튼 위치
//------------------------------------------------------------------------------------------------------------------- 단어 추가 부분
        add(wordPlusBtn); //단어 추가 버튼 부착
        wordPlusBtn.setLocation(1400, 700); //버튼 위치
        wordPlusBtn.setSize(100, 40);
        add(wordPlusField); //텍스트 필드 부착
        wordPlusField.setSize(300, 20);
        wordPlusField.setLocation(wordPlusBtn.getX() - 200, wordPlusBtn.getY() + 50); //텍스트필드 위치
//-------------------------------------------------------------------------------------------------------------------- 사용자 이름 입력받기

//-------------------------------------------------------------------------------------------------------------------- 난이도 조절 버튼
        add(hard); add(normal); add(easy);
        hard.setSize(100, 40);
        normal.setSize(100, 40);
        easy.setSize(100, 40);
        //위치
        hard.setLocation(700, 500);
        normal.setLocation(hard.getX(), hard.getY()+50);
        easy.setLocation(hard.getX(), hard.getY() + 100);
//---------------------------------------------------------------------------------------------------------------------

        //난이도 조절 버튼 이벤트 리스너
        hard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hpPanel.hard();
            }
        });
        normal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hpPanel.normal();
            }
        });
        easy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hpPanel.easy();
            }
        });

        //버튼 누르면 단어 추가하느 이벤트 리스너
        wordPlusBtn.addMouseListener(new MouseAdapter() {
            @Override //버튼 클릭하면 텍스트필드 읽어와서 addWord(input)을 단어 추가
            public void mouseClicked(MouseEvent e) {
                JTextField tf = wordPlusField; //텍스트필드에서 사용자가 입력한 내용 가져오기
                String inputWord = tf.getText();
                if(!inputWord.isEmpty()) //빈칸입력하면 추가 x
                    textSource.addWord(inputWord);//words.txt에 단어 추가

                tf.setText(""); //텍스트 필드 비우기
            }
        });

        gameStartBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // 마우스가 버튼에 들어갈 때 크기 살짝 키우기
                gameStartBtn.setSize(new Dimension(gameStartBtn.getWidth() + 15, gameStartBtn.getHeight() + 15));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                // 마우스가 버튼에서 나갈 때 다시 크기 원래대로 돌리기
                gameStartBtn.setSize(new Dimension(gameStartBtn.getWidth() - 15, gameStartBtn.getHeight() - 15));
            }

            //게임 시작버튼 클릭하면 화면 GamePanel로 변경
            public void mouseClicked(MouseEvent e) {
                introPanel.setVisible(false);
                GamePanel gamePanel = new GamePanel(hpPanel);
                introPanel.getParent().add(gamePanel, BorderLayout.CENTER);
                //GameFrame의 콘텐츠 판 센터에 부착

            }
        });
        setVisible(true);
    }
    //생성자


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null); //인트로 배경화면 그리기
    }
}
