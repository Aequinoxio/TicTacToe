import java.util.Random;


/**
 * Implementazione delle regole: http://www.cs4fn.org/teachers/activities/intelligentpaper/intelligentpaper.pdf
 */
class ComputerPlayerPaper extends Player {
    private int stato = 0;

    ComputerPlayerPaper(Scacchiera.Simboli mioSimbolo, Scacchiera scacchiera, boolean primoAMuovere) {
        super(mioSimbolo, scacchiera, primoAMuovere);
        setDescrizione("Paper player");
    }

    public Scacchiera.Posizione miaMossa() {
        Random random = new Random();

        //System.out.println("PC stato" + stato);

        if (stato == 0) {  // inizio e scelgo un angolo a caso
            int angolo = random.nextInt(4);
            stato = 1;
            switch (angolo) {
                case 0:
                    primaMossa = new Scacchiera.Posizione(0, 0);
                    return primaMossa;
                case 1:
                    primaMossa = new Scacchiera.Posizione(0, 2);
                    return primaMossa;
                case 2:
                    primaMossa = new Scacchiera.Posizione(2, 0);
                    return primaMossa;
                case 3:
                    primaMossa = new Scacchiera.Posizione(2, 2);
                    return primaMossa;
            }
        }

        if (stato == 1) {
            // Prendo gli angoli per cui il modulo 4 va bene
            int r = (primaMossa.getRiga() + 2) % 4;
            int c = (primaMossa.getColonna() + 2) % 4;
            stato = 2;

            Scacchiera.Posizione mossa = new Scacchiera.Posizione(r, c);
            if (scacchiera.isFree(mossa)) {
                return mossa;
            } else {
                // Dovrebbe essere libera
                return new Scacchiera.Posizione(primaMossa.getRiga(), mossa.getColonna());
            }
        }

        if (stato == 2 || stato == 3) { // Mossa 3,4
            stato++;
            // Cerco uma linea con due simboli uguali e uno spazio libero
            Scacchiera.Posizione posizioneTemp = null;
            Scacchiera.Posizione posizioneAvversario = null;
            for (int i = 0; i < 3; i++) {
                int numSimbMio = 0;
                int numSimbAltro = 0;
                int numVuoti = 0;

                // Ricerca orizzontale
                for (int j = 0; j < 3; j++) {
                    if (scacchiera.getSimbol(i, j) == mioSimbolo) {
                        numSimbMio++;
                    } else if (scacchiera.getSimbol(i, j) == Scacchiera.Simboli.Vuoto) {
                        numVuoti++;
                        posizioneTemp = new Scacchiera.Posizione(i, j); // TODO: non molto efficiente, genero una nuova mossa ogni volta che incontro uno spazio vuoto
                    } else {
                        numSimbAltro++;
                    }
                }

                if (numVuoti == 1 && numSimbMio == 2) {
                    //System.out.println("trovata linea vincente (H): " + posizioneTemp);
                    return posizioneTemp;
                }
                if (numVuoti == 1 && numSimbAltro == 2) {
                    //System.out.println("trovata linea difensiva (H): " + posizioneTemp);
                    posizioneAvversario = posizioneTemp;
                }


                numSimbMio = 0;
                numSimbAltro = 0;
                numVuoti = 0;

                // Ricerca verticale
                for (int j = 0; j < 3; j++) {
                    if (scacchiera.getSimbol(j, i) == mioSimbolo) {
                        numSimbMio++;
                    } else if (scacchiera.getSimbol(j, i) == Scacchiera.Simboli.Vuoto) {
                        numVuoti++;
                        posizioneTemp = new Scacchiera.Posizione(j, i); // TODO: non molto efficiente, genero una nuova mossa ogni volta che incontro uno spazio vuoto
                    } else {
                        numSimbAltro++;
                    }
                }

                if (numVuoti == 1 && numSimbMio == 2) {
                    //System.out.println("trovata linea vincente (V): " + posizioneTemp);
                    return posizioneTemp;
                }
                if (numVuoti == 1 && numSimbAltro == 2) {
                    //System.out.println("trovata linea difensiva (V): " + posizioneTemp);
                    posizioneAvversario = posizioneTemp;
                }

            }

            // Se ho trovato una posizione con 2 simboli avversari mi ci metto
            if (posizioneAvversario != null) {
                return posizioneAvversario;
            }

            // Se arrivo qui devo mettermi in un angolo
            Scacchiera.Posizione posizione = posizioneAngoloLibero();
            if (posizione == null) {
                // Modifica rispetto a documento: se è libero il centro mi metto li. Converge prima verso una vittoria
                if (scacchiera.isFree(1,1)){
                    posizione = new Scacchiera.Posizione(1,1);
                } else {
                    posizione = posizioneCasuale();
                }
            }
            return (posizione);
        }

        // Dallo stato 5 in poi mi metto in una posizione libera
        // Modifica rispetto a documento: se è libero il centro mi metto li. Converge prima verso una vittoria
        //        if (scacchiera.isFree(1,1)){
//            p = new Scacchiera.Posizione(1,1);
//        } else {
//            p = posizioneCasuale();
//        }

        return posizioneCentraleOCasuale();
    }

}
