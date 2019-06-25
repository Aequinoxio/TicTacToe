import java.util.ArrayList;
import java.util.Random;


/**
 * Implementazione delle regole: http://www.cs4fn.org/teachers/activities/intelligentpaper/intelligentpaper.pdf
 * Modificate per farlo giocare anche come secondo
 */
class ComputerPlayerSecondToMove extends Player {
    private int stato = 0;
    Scacchiera.Simboli simboloAvversario;
    Random random = new Random();

    public ComputerPlayerSecondToMove(Scacchiera.Simboli mioSimbolo, Scacchiera scacchiera, boolean primoAMuovere) {
        super(mioSimbolo, scacchiera, primoAMuovere);
        setDescrizione("Computer player - strategy for second move");
        if (mioSimbolo == Scacchiera.Simboli.Cerchio) {
            simboloAvversario = Scacchiera.Simboli.Croce;
        } else {
            simboloAvversario = Scacchiera.Simboli.Cerchio;
        }
    }

    public Scacchiera.Posizione miaMossa() {
        Scacchiera.Posizione p;

        ArrayList<Scacchiera.Posizione> posVincenti = scacchiera.cercaPosizioneLiberaConDueOccupate(mioSimbolo);

        if (stato++ == 0 && scacchiera.isFree(1,1)) {
            return new Scacchiera.Posizione(1, 1); // All'inizio mi metto al centro
        }

        // Se trovo una posizione vincente la sfrutto subito
        if (posVincenti != null && posVincenti.size()>0) {
            return posVincenti.get(0); // Mi metto nella prima posizione disponibile;

        }

        // Se mi devo difendere lo faccio subito
        ArrayList<Scacchiera.Posizione> posPerdenti = scacchiera.cercaPosizioneLiberaConDueOccupate(simboloAvversario);
        if (posPerdenti != null && posPerdenti.size()>0) {
            return posPerdenti.get(0);
        }

        Scacchiera.Posizione[] posizioniCroce = {
                new Scacchiera.Posizione(1, 0),
                new Scacchiera.Posizione(1, 2),
                new Scacchiera.Posizione(0, 1),
                new Scacchiera.Posizione(0, 2),
        };

        // Scelgo una posizione a croce
        for (int i=0;i<4;i++) {
            if (scacchiera.isFree(posizioniCroce[i])) {
                return posizioniCroce[i];
            }
        }

        return posizioneAngoloLibero();

    }
}
