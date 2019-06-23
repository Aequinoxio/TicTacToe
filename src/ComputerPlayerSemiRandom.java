import java.util.Random;

public class ComputerPlayerSemiRandom extends Player {
    private final Random random = new Random();

    ComputerPlayerSemiRandom(Scacchiera.Simboli mioSimbolo, Scacchiera scacchiera, boolean primoAMuovere) {
        super(mioSimbolo, scacchiera, primoAMuovere);
        setDescrizione("Semirandom player");
    }

    /**
     * Mi metto principalmnte al centro
     * poi scelgo casualmente tra una posizione totalmente casuale ed un angolo
     * @return Posizione scelta
     */
    @Override
    public Scacchiera.Posizione miaMossa() {
        if (scacchiera.isFree(1, 1)) {
            return posizioneCentraleOCasuale();
        } else {
            if (random.nextBoolean()){
                return posizioneCasuale();
            }else {
                Scacchiera.Posizione p = posizioneAngoloLibero();
                if (p==null){
                    return posizioneCasuale();
                } else {
                    return p;
                }
            }
        }
    }
}
