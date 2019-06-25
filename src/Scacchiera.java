import java.util.ArrayList;

/**
 * Classe che rappresenta la scacchiera con gli anum types relativi
 */
public class Scacchiera {

    private final Simboli[][] scacchiera;
    private Posizione ultimaMossa;


    Posizione getUltimaMossa() {
        return ultimaMossa;
    }

    Scacchiera() {
        scacchiera = new Simboli[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                scacchiera[i][j]=Simboli.Vuoto;
            }
        }

    }

    boolean putMove(int riga, int colonna, Simboli simbolo){
        if (scacchiera[riga][colonna]==Simboli.Vuoto) {
            scacchiera[riga][colonna] = simbolo;
            ultimaMossa = new Posizione(riga,colonna);
            return true;
        }
        return false;
    }

    boolean putMove(Scacchiera.Posizione posizione, Simboli simbolo){
        return putMove(posizione.getRiga(), posizione.getColonna(),simbolo);
    }

    @SuppressWarnings("RedundantIfStatement")
    boolean isFree(Posizione posizione){
        if (scacchiera[posizione.getRiga()][posizione.getColonna()]==Simboli.Vuoto){
            return true;
        }
        return false;
    }

    boolean isFree(int riga, int colonna){
        //noinspection RedundantIfStatement
        if (scacchiera[riga][colonna]==Simboli.Vuoto){
            return true;
        }
        return false;
    }

    Simboli getSimbol(int riga, int colonna){
        return scacchiera[riga][colonna];
    }

    Vincitori whatIsTheWinner(){
        int playerCroceCount=0;
        int playerCerchioCount=0;

        // Check diagonali
        int playerCroceCountdiag2=0;
        int playerCerchioCountdiag2 = 0;
        for (int i=0;i<3;i++){
            if (getSimbol(i,i)==Simboli.Cerchio){
                playerCerchioCount++;
            }
            if (getSimbol(i,i)==Simboli.Croce){
                playerCroceCount++;
            }

            if (getSimbol(i,2-i)==Simboli.Cerchio){
                playerCerchioCountdiag2++;
            }
            if (getSimbol(i,2-i)==Simboli.Croce){
                playerCroceCountdiag2++;
            }


        }
        if (playerCerchioCount==3 || playerCerchioCountdiag2==3){
            return Vincitori.Cerchio;
        }
        if (playerCroceCount==3 || playerCroceCountdiag2==3){
            return Vincitori.Croce;
        }

        // Check linee
        for (int i = 0; i < 3; i++) {
            int playerCroceCountRiga=0;
            int playerCerchioCountRiga=0;
            int playerCroceCountColonna=0;
            int playerCerchioCountColonna=0;
            for (int j = 0; j < 3; j++) {
                if (scacchiera[i][j]==Simboli.Cerchio){
                    playerCerchioCountRiga++;
                }
                if (scacchiera[i][j]==Simboli.Croce){
                    playerCroceCountRiga++;
                }
                if (scacchiera[j][i]==Simboli.Cerchio){
                    playerCerchioCountColonna++;
                }
                if (scacchiera[j][i]==Simboli.Croce){
                    playerCroceCountColonna++;
                }
                if (playerCerchioCountColonna==3 || playerCerchioCountRiga==3){
                    return Vincitori.Cerchio;
                }
                if (playerCroceCountColonna==3 || playerCroceCountRiga==3){
                    return Vincitori.Croce;
                }

            }
        }

        if (getNumberOfFreeSpaces()==0) {
            return Vincitori.Patta;
        }

        return Vincitori.NonDecidibile;
    }

    int getNumberOfFreeSpaces(){
        int counter=0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (scacchiera[i][j]==Simboli.Vuoto){
                    counter++;
                }
            }
        }
        return counter;
    }

    ArrayList<Posizione> cercaPosizioneLiberaConDueOccupate(Simboli simbolo){
        ArrayList<Posizione> posizioni = new ArrayList<>();

        // Cerco uma linea con due simboli uguali e uno spazio libero
        Scacchiera.Posizione posizioneTemp = null;
        Scacchiera.Posizione posizioneAvversario = null;

        // Ricerca diagonale 1
        int numSimboloDiag1 = 0;
        int numVuotiDiag1 = 0;
        int posRigaDiag1=0;
        int posColonnaDiag1=0;

        // Ricerca diagonale 2
        int numSimboloDiag2 = 0;
        int numVuotiDiag2 = 0;
        int posRigaDiag2=0;
        int posColonnaDiag2=0;

        for (int i = 0; i < 3; i++) {
            int numSimbAltro = 0;

            // Ricerca orizzontale
            int numSimboloHoriz = 0;
            int numVuotiHoriz = 0;
            int posRigaHoriz=0;
            int posColonnaHoriz=0;

            // Ricerca verticale
            int numSimboloVert = 0;
            int numVuotiVert = 0;
            int posRigaVert=0;
            int posColonnaVert=0;


            for (int j = 0; j < 3; j++) {
                // Ricerca orizzontale
                if (scacchiera[i][j] == simbolo) {
                    numSimboloHoriz++;
                } else if (scacchiera[i][j] == Scacchiera.Simboli.Vuoto) {
                    numVuotiHoriz++;
                    posRigaHoriz=i;      // Non importa se sovrascrivo in quanto genero una posizione libera solo se ce n'è una sola
                    posColonnaHoriz=j;
                } else {
                    numSimbAltro++; // TODO: Eliminare
                }

                // Ricerca verticale
                if (scacchiera[j][i] == simbolo) {
                    numSimboloVert++;
                } else if (scacchiera[j][i] == Scacchiera.Simboli.Vuoto) {
                    numVuotiVert++;
                    posRigaVert=j;      // Non importa se sovrascrivo in quanto genero una posizione libera solo se ce n'è una sola
                    posColonnaVert=i;

                } else {
                    numSimbAltro++; // Todo eliminare
                }


            }
            // Ricerca diagonale 1
            if (scacchiera[i][i] == simbolo) {
                numSimboloDiag1++;
            } else if (scacchiera[i][i] == Scacchiera.Simboli.Vuoto) {
                numVuotiDiag1++;
                posRigaDiag1=i;      // Non importa se sovrascrivo in quanto genero una posizione libera solo se ce n'è una sola
                posColonnaDiag1=i;

            } else {
                numSimbAltro++; // Todo eliminare
            }

            // Ricerca diagonale 2
            if (scacchiera[2-i][i] == simbolo) {
                numSimboloDiag2++;
            } else if (scacchiera[2-i][i] == Scacchiera.Simboli.Vuoto) {
                numVuotiDiag2++;
                posRigaDiag2=2-i;      // Non importa se sovrascrivo in quanto genero una posizione libera solo se ce n'è una sola
                posColonnaDiag2=i;

            } else {
                numSimbAltro++; // Todo eliminare
            }


            if (numVuotiHoriz == 1 && numSimboloHoriz == 2) {
                //System.out.println("trovata linea vincente (H): " + posizioneTemp);
                posizioni.add(new Posizione(posRigaHoriz,posColonnaHoriz));
            }
            if (numVuotiVert== 1 && numSimboloVert == 2) {
                //System.out.println("trovata linea vincente (H): " + posizioneTemp);
                posizioni.add(new Posizione(posRigaVert,posColonnaVert));
            }
        }

        if (numVuotiDiag1 == 1 && numSimboloDiag1 == 2) {
            //System.out.println("trovata linea vincente (H): " + posizioneTemp);
            posizioni.add(new Posizione(posRigaDiag1,posColonnaDiag1));
        }
        if (numVuotiDiag2== 1 && numSimboloDiag2 == 2) {
            //System.out.println("trovata linea vincente (H): " + posizioneTemp);
            posizioni.add(new Posizione(posRigaDiag2,posColonnaDiag2));
        }

        return posizioni;
    }

    ///////////////////////// Enumerazioni //////////////////

    enum Simboli {
        Croce,
        Cerchio,
        Vuoto
    }

    enum Vincitori {
        Croce,
        Cerchio,
        Patta,
        NonDecidibile,
        Squalificato
    }

    static class Posizione {
        private final int riga;
        private final int colonna;

        int getRiga() {
            return riga;
        }

        int getColonna() {
            return colonna;
        }

        Posizione(int riga, int colonna) {
            this.riga = riga;
            this.colonna = colonna;
        }

        @Override
        public String toString(){
            return "("+riga+","+colonna+")";
        }
    }
}
