import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;


public class GameFinishPanel extends JPanel {
    private HpPanel hpPanel = null;
    private TextSource textSource = null;

    private ImageIcon finishBackground = new ImageIcon("victory.png");
    private Image backgroundImage = finishBackground.getImage();



    private JTextField loginField = new JTextField(20);
    private JLabel loginId = new JLabel("ID");
    private Font rankFont = new Font("Gill Sans",Font.ITALIC, 30); //랭킹 출력에 사용할 폰트

    //생성자
    public GameFinishPanel(HpPanel hpPanel, TextSource textSource){  //HpPanel >> howKill 필요 >> 순위 기록위해
        setSize(1920, 1080);
        setBackground(Color.yellow);
        setLayout(null);
        setVisible(true);
        this.hpPanel = hpPanel;
        this.textSource = textSource;


        add(loginField);
        add(loginId);
        loginId.setSize(15, 15);
        loginId.setLocation(60, 720);
        loginId.setBackground(Color.WHITE);

        loginId.setOpaque(true);
        loginField.setSize(200, 20);
        loginField.setLocation(loginId.getX(), loginId.getY() + 30);

        printRank();

        //새로 ID를 입력 >> 엔터로 인식
        //패널에 진입했을 때 랭킹이 출력되어있을 텐데 엔터를 쳐서 자신의 점수가 10위권 이내라면 repaint()
        loginField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField tf = (JTextField)(e.getSource());
                String inputWord = tf.getText();

                //textSource.addId(inputWord); //Id 추가
                makeRecord(inputWord, hpPanel.getKill()); //입력받은 ID와 킬수 Record.txt에 저장

                printRank();
                tf.setText("");
            }
        });
    }
    public static void makeRecord(String Id, int kill) {
        try {
            // 기존 레코드 읽기
            List<String> records = readRecords();

            // 새 레코드를 리스트에 추가
            String newRecord = Id + "  >>  " + kill;
            records.add(newRecord);

            // kill 값에 따라 내림차순으로 레코드 정렬
            Collections.sort(records, Comparator.comparingInt(s -> Integer.parseInt(s.split("  >>  ")[1])));
            Collections.reverse(records);

            // 정렬된 레코드를 파일에 쓰기
            writeRecords(records);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Record.txt 읽어와서 List<String> 형태로 반환
    private static List<String> readRecords() throws IOException {
        List<String> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Record.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                records.add(line);
            }
        }
        return records;
    }

    //정렬된 레코드 목록을 받아와서 "Record.txt" 파일에 쓴다
    // records는 이미 정렬된 상태
    private static void writeRecords(List<String> records) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Record.txt"))) {
            for (String record : records) {
                bw.write(record);
                bw.newLine();
            }
        }
    }

    //화면에 랭킹 라벨을 출력
    private void printRank(){
        try {

            //새로 ID입력했을 때를 대비해 컴포넌트 비우고 시작
            Component[] components = getComponents();
            for (Component component : components) {
                if (component instanceof JLabel && !((JLabel) component).getText().equals("ID")) {
                    remove(component);
                }
            }

            List<String> records = readRecords();

            // 랭킹을 담을 JLabel 배열
            JLabel[] rankLabels = new JLabel[records.size()];

            for (int i = 0; i < records.size() && i < 10; i++) { //10명까지 읽어와 출력
                String record = records.get(i);
                rankLabels[i] = new JLabel((i + 1) + ". " + record + "KILL");
                rankLabels[i].setSize(300, 50);
                rankLabels[i].setLocation(50, 50+ i*50);
                rankLabels[i].setForeground(Color.YELLOW); //글자색 노랑
                rankLabels[i].setFont(rankFont); //폰트, 글자크기 변경
                add(rankLabels[i]);
            }

            // 다시 그리기를 통해 변경된 내용을 화면에 반영
            revalidate();
            this.repaint();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null); //인트로 배경화면 그리기
        printRank();//id 입력하면 랭킹 다시 그리기
    }

}
