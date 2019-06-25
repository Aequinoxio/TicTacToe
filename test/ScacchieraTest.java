import javafx.print.PageOrientation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ScacchieraTest {
    ScacchieraConsolle scacchiera = new ScacchieraConsolle();
    Scacchiera.Simboli simbolo = Scacchiera.Simboli.Croce;
    @BeforeEach
    void setUp() {
        scacchiera.putMove(new Scacchiera.Posizione(2,0),simbolo);
//        scacchiera.putMove(new Scacchiera.Posizione(0,1),simbolo);
//        scacchiera.putMove(new Scacchiera.Posizione(1,0),simbolo);
        scacchiera.putMove(new Scacchiera.Posizione(1,1),simbolo);
        scacchiera.putMove(new Scacchiera.Posizione(2,2),simbolo);

    }

    @Test
    void cercaPosizioneLiberaConDueOccupate() {
        scacchiera.mostraScacchiera();
        ArrayList<Scacchiera.Posizione> posiziones = scacchiera.cercaPosizioneLiberaConDueOccupate(simbolo);
        for(Scacchiera.Posizione p : posiziones){
            System.out.println(p);
        }
    }
}