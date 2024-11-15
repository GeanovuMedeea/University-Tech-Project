import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PIF {

    class Pair{
        String token;
        int stPosition;

        Pair(String token, int stPosition){
            this.token = token;
            this.stPosition = stPosition;
        }

        String getToken(){
            return token;
        }

        int getStPosition(){
            return stPosition;
        }
    }

    List<Pair> pairList;

    PIF(){
        pairList = new ArrayList<>();
    }

    void genPIF(String token, int tokenCode){
        Pair newPair = new Pair(token,tokenCode);
        this.pairList.add(newPair);
    }

    void printPIF(){
        int pifSize = this.pairList.size();
        try {
            FileWriter writer = new FileWriter("PIF.out");
            for(int index=0; index<pifSize;index++)
                writer.write("Token: " + pairList.get(index).getToken() + " Code: " + pairList.get(index).getStPosition() +"\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
