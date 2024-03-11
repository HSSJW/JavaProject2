import javax.swing.*;
import java.awt.*;
import java.util.Vector;

//여러개의 쓰레드가 들어갈 배열을 GameGround에서 작성


//타겟 쓰레드랑 같은 역할
//울트라 라벨 출력
public class UltraliskThread extends Thread {
        private HpPanel hpPanel = null;
        private JLabel ultraliskLabel = null;
        private GameGround gameGround = null;
        private JLabel textBox = new JLabel();
        private String text = null;

        //생성자 >> 다른 클래스에서 생성할 때 매개변수 필요
        //textlist 울트라 머리위에 올라가 단어라벨 생성에 사용
        public UltraliskThread(JLabel ultraliskLabel, GameGround gameGround, TextSource textlist, HpPanel hpPanel){
                this.gameGround = gameGround; //게임 그라운드에서 호출해서 사용할 것이기 때문
                this.ultraliskLabel = ultraliskLabel; //라벨 정보 가져오기
                this.hpPanel = hpPanel;

                text = textlist.get();
                textBox = new JLabel(text); //랜덤 텍스트 박스 생성
                textBox.setSize(text.length()*20,20); //박스 사이즈
                textBox.setBackground(Color.WHITE);
                textBox.setHorizontalAlignment(SwingConstants.CENTER);
                textBox.setVisible(true);
                textBox.setOpaque(true);
                gameGround.add(textBox);

        }

        //GameFrame에서 UltraliskThread.start()하면 이게 실행됨
        //초기 위치 설정은 GameGround의 startGame함수 초반에 설정
        @Override
        public void run(){
                int x = ultraliskLabel.getX(); //초기위치는 화면 우측 (  Ground패널Width)
                int y = ultraliskLabel.getY(); //y축 좌표는 Ground패널에서 랜덤으로 추출해 적용할 것임

                while(true){


                        //벙커앞까지 도착하지 않았을 경우
                        if(x > 235){ //벙커이미지 넓이 235
                                x = ultraliskLabel.getX() - 5; //좌측으로 이동
                                y = ultraliskLabel.getY();
                                ultraliskLabel.getParent().repaint();//이동했을 경우 repaint

                        }
                        else{ //벙커앞에 도착했을 경우
                                //공격모션이미지로 변경
                                ultraliskLabel.setIcon(new ImageIcon("ultraliskAttack.gif"));

                                hpPanel.decreaseHp();

                                try {
                                        Thread.sleep(1000);
                                }
                                catch (InterruptedException e) {
                                        ultraliskLabel.getParent().remove(ultraliskLabel);
                                        textBox.getParent().remove(textBox);
                                        ultraliskLabel.setIcon(new ImageIcon("ultraliskWalking.gif"));

                                        // 스레드 종료
                                        return;
                                }
                        }

                        ultraliskLabel.setLocation(x, y); //울트라 위치 변경
                        textBox.setLocation(x + 80, y + 40); //울트라 머리위에 텍스트박스 출력

                        try{
                                Thread.sleep(60);
                        }
                        //사용자가 정답을 입력했을경우 >> GameGround에서 interrupted시킨다
                        catch (InterruptedException e){
                                //라벨 삭제 미 쓰레드 종료
                                ultraliskLabel.getParent().remove(ultraliskLabel);
                                textBox.getParent().remove(textBox);


                                // 스레드 종료
                                return;
                        }
                }
        }
        
        public String getUltraText(){
                return text; //이 울트라의 텍스트 리턴 >> MakeEnemyThread에서 이 text값과 사용자가 입력한 값 비교해서 맞다면 해당 울트라쓰레드 interrupt
                             // MakeEnemyThread에서 함수 만들어서 GameGround에서 호출해서 사용할 수있게 해야함
        }

        //정답을 맞췄을 때 해당하는 단어를 이상한걸로 바꿈
        public void setUltraText(){
                text="qqqqqqqqqqqqqqqqqqq";
        }


}
