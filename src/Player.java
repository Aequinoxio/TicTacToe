import java.util.Random;

abstract class Player {
    final Scacchiera.Simboli mioSimbolo;
    final boolean primoAMuovere;
    private final int[] angoloScacchiera = {0, 2}; // Appoggio per riferirsi rapidament agli angoli

    private String descrizione;

    final Scacchiera scacchiera;
    Scacchiera.Posizione primaMossa;
    Player(Scacchiera.Simboli mioSimbolo, Scacchiera scacchiera, boolean primoAMuovere){
        this.mioSimbolo = mioSimbolo;
        this.scacchiera = scacchiera;
        this.primoAMuovere=primoAMuovere;
    }

    public Scacchiera.Simboli getMioSimbolo() {
        return mioSimbolo;
    }

    void setDescrizione(String descrizione){
        this.descrizione=descrizione;
    }

    @Override
    public java.lang.String toString() {
        return (descrizione+" ("+mioSimbolo+")");
    }

    Scacchiera.Posizione posizioneAngoloLibero() {
        //System.out.println("("+ mioSimbolo+")AngoloLibero()");
        Scacchiera.Posizione p = null;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (scacchiera.isFree(angoloScacchiera[i], angoloScacchiera[j])) {
                    p = new Scacchiera.Posizione(angoloScacchiera[i], angoloScacchiera[j]);
                }
            }
        }

        return p;
    }

    Scacchiera.Posizione posizioneCasuale() {
        int freePos = scacchiera.getNumberOfFreeSpaces();
        if (freePos == 0) {
            return null;
        }

        int posCounter = new Random().nextInt(freePos);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (scacchiera.isFree(i, j)) {
                    posCounter--;
                    if (posCounter <= 0) {
                        return new Scacchiera.Posizione(i, j);
                    }
                }
            }
        }

//        // test
//        System.out.println("("+ mioSimbolo+")NO POSIZIONI LIBERRE!!!!!!!!!!1");
        return null;
    }

    Scacchiera.Posizione posizioneCentraleOCasuale(){
        if (scacchiera.isFree(1,1)) {
            return new Scacchiera.Posizione(1, 1);
        }
        else {
            return posizioneCasuale();
        }
    }

    public abstract Scacchiera.Posizione miaMossa();

}
