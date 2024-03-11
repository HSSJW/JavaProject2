import java.io.*;
import java.util.Vector;

//새로만든 TextSource 객체에서 멤버함수 호출하더라도 중요한건 words.txt이기 때문에 상관없음 >> 빈 함수 객체
public class TextSource {
    private Vector<String> words = new Vector<String>();
    String filePath = "words.txt"; //파일 경로지정



    //생성자 >> word.txt에서 한줄씩 읽어와 벡터 word에 저장
    public TextSource(){

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String wordFromFile;
            while ((wordFromFile = br.readLine()) != null) {
                // 파일에서 한 줄씩 읽어와서 벡터에 추가
                this.words.add(wordFromFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //랜덤 단어 하나 리턴하는 함수
    public String get(){
        int index = (int)(Math.random()* words.size());
        return words.get(index);
    }

    public void addWord(String word){

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {

            // 단어를 파일에 추가
            bw.write(word);

            bw.newLine(); // 새로운 줄로 이동

        }
        catch (IOException e) {
            e.printStackTrace(); //제대로 진행 안됐으면 중단
        }
    }

}
