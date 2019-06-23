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
