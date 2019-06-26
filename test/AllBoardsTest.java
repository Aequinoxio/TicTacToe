import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AllBoardsTest {
    AllBoards allBoards = new AllBoards();
    @BeforeEach
    void setUp() {
    }

    @Test
    void generateAllBoards() {
        allBoards.generateAllBoards();
        ArrayList<ScacchieraConsolle> sc = allBoards.getScacchieraConsolles();
        int counter=1;
        int maxScacchiere= sc.size();
        System.out.println(maxScacchiere);
        for (ScacchieraConsolle s : sc){
            //System.out.println(String.format("Scacchiera %d di %d",counter++,maxScacchiere));
            // DEBUG
            if (s.getFreeSpaces()==0){
             //   s.mostraScacchiera();
            }
            //s.mostraScacchiera();
        }
    }
}