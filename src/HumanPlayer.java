import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HumanPlayer extends Player {
    private boolean wantExit;
    private final String exitString = "exit";


    HumanPlayer(Scacchiera.Simboli mioSimbolo, Scacchiera scacchiera, boolean primoAMuovere) {
        super(mioSimbolo, scacchiera, primoAMuovere);
        setDescrizione("Giocatore umano");
    }

    /**
     * Ritorna la stringa per terminare la partita
     * @return exit string da inserire per terminare la partita
     */
    public String getExitString() {
        return exitString;
    }

    /**
     * Ritorna una mossa inserira dal giocatore da consolle nella forma:
     *      riga,colonna
     * @return Posizione scelta
     */
    @Override
    public Scacchiera.Posizione miaMossa() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String valore = null;
        wantExit=false;
        try {
            valore = reader.readLine().trim();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (valore==null || valore.equals(exitString)) {
            wantExit=true;
            return null;
        }

        // Splitto con qualsiasi sequenza di caratteri non numerici
        String[] rc = valore.split("[^\\d]+");

        return new Scacchiera.Posizione(Integer.valueOf(rc[0]),Integer.valueOf(rc[1]));
    }

    boolean voglioTerminare(){
        return wantExit;
    }
}
