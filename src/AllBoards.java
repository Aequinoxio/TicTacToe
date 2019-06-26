import java.util.ArrayList;

/**
 * Classe che genera tutte le possibili scacchiere ammissibili
 * Visto su https://stackoverflow.com/questions/7466429/generate-a-list-of-all-unique-tic-tac-toe-boards
 */
public class AllBoards {
    ArrayList<ScacchieraConsolle> scacchieraConsolles = new ArrayList<>();

    public ArrayList<ScacchieraConsolle> getScacchieraConsolles() {
        return scacchieraConsolles;
    }


    // Eliminando le scacchiere non legittime dovrei ottenere 5477 posizioni vere delle 19683 possibili
    // Tic-Tac-Toe is has 5477 valid states excluding the empty position
    //
    // Fonte: https://stackoverflow.com/questions/7466429/generate-a-list-of-all-unique-tic-tac-toe-boards
    // Incompleto al 26 giugno 2019, le strategie:
    //      ComputerPlayerPaper (se si inizia per primo) e
    //      ComputerPlayerSecondToMove (se si inizia per secondi) sono quelle perfette
    public void generateAllBoards(){
        int c = 0;
        while (c < 262144){
            boolean valid = (c & 3) < 3;
            valid &= ((c >>  2) & 3) < 3;
            valid &= ((c >>  4) & 3) < 3;
            valid &= ((c >>  6) & 3) < 3;
            valid &= ((c >>  8) & 3) < 3;
            valid &= ((c >> 10) & 3) < 3;
            valid &= ((c >> 12) & 3) < 3;
            valid &= ((c >> 14) & 3) < 3;
            valid &= ((c >> 16) & 3) < 3;

            if (valid){
                int i = c;
                int j = 0;
                int riga=0;
                int colonna=0;
                ScacchieraConsolle s = new ScacchieraConsolle();
                while (j < 9){
                    riga=j%3;
                    colonna = j/3;
                    //System.out.println(i & 3) ;
                    Scacchiera.Simboli simbolo= Scacchiera.Simboli.Vuoto;
                    switch (i&3){
                        case 0: simbolo = Scacchiera.Simboli.Vuoto; break;
                        case 1: simbolo = Scacchiera.Simboli.Croce; break;
                        case 2: simbolo = Scacchiera.Simboli.Cerchio; break;
                    }
                    s.putMove(riga,colonna,simbolo);
                    i >>= 2;
                    j++;
                }
                //System.out.println();
                if (isValid(s)) {
                    scacchieraConsolles.add(s);
                }
                //s.mostraScacchiera();
            }

            c++;
        }

        //System.out.println(scacchieraConsolles.size());
    }

    /**
     * Conto il numero di linee vincitrici e di vincitori
     * @param scacchiera
     * @return
     */
    int howManyWinnerLines(Scacchiera scacchiera){
        int playerCroceCountDiag1=0;
        int playerCerchioCountDiag1=0;
        int winners=0;

        // Check diagonali
        int playerCroceCountDiag2=0;
        int playerCerchioCountDiag2 = 0;
        for (int i=0;i<3;i++){
            if (scacchiera.getSimbol(i,i)== Scacchiera.Simboli.Cerchio){
                playerCerchioCountDiag1++;
            }
            if (scacchiera.getSimbol(i,i)== Scacchiera.Simboli.Croce){
                playerCroceCountDiag1++;
            }

            if (scacchiera.getSimbol(2-i,i)== Scacchiera.Simboli.Cerchio){
                playerCerchioCountDiag2++;
            }
            if (scacchiera.getSimbol(2-i,i)== Scacchiera.Simboli.Croce){
                playerCroceCountDiag2++;
            }

        }
        if (playerCerchioCountDiag1==3 || playerCerchioCountDiag2==3){
            winners++;
        }
        if (playerCroceCountDiag1==3 || playerCroceCountDiag2==3){
            winners++;
        }

        // Check linee
        for (int i = 0; i < 3; i++) {
            int playerCroceCountRiga=0;
            int playerCerchioCountRiga=0;
            int playerCroceCountColonna=0;
            int playerCerchioCountColonna=0;
            for (int j = 0; j < 3; j++) {
                if (scacchiera.getSimbol(i,j)== Scacchiera.Simboli.Cerchio){
                    playerCerchioCountRiga++;
                }
                if (scacchiera.getSimbol(i,j)== Scacchiera.Simboli.Croce){
                    playerCroceCountRiga++;
                }
                if (scacchiera.getSimbol(j,i)== Scacchiera.Simboli.Cerchio){
                    playerCerchioCountColonna++;
                }
                if (scacchiera.getSimbol(j,i)== Scacchiera.Simboli.Croce){
                    playerCroceCountColonna++;
                }
                if (playerCerchioCountColonna==3 || playerCerchioCountRiga==3){
                    winners++;
                }
                if (playerCroceCountColonna==3 || playerCroceCountRiga==3){
                    winners++;
                }

            }
        }
        return winners;
    }

    /**
     * Verifico se la scacchiera è valida.
     * Ci deve essere al max un vincitore ed al massimo una linea vincitrice (condizione non perfetta TODO: da migliorare)
     * TODO: aggiungere altre condizioni per arivare a selezionare solo 5477 scacchiere
     * @param s
     * @return
     */
    boolean isValid(ScacchieraConsolle s){
        int numCroce=0;
        int numCerchio=0;
        boolean retVal = false;

        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                switch (s.getSimbolo(i,j)){
                    case Croce:numCroce++;break;
                    case Cerchio:numCerchio++;break;
                }
            }
        }

        // Ci devono essere un numero quasi uguale dei due simboli altrimenti la scacchiera non va bene
        if ((numCerchio-numCroce)==0 || (numCerchio-numCroce)==-1 || (numCerchio-numCroce)==1){
            retVal=true;
        }

        // Check se ci sono troppe righe o colonne vincitrici
        // Check incompleto in quanto potrebbe capitare che una vittoria sia con due linee (es. angolo)
        // TODO: migliorare
        if (howManyWinnerLines(s.getScacchiera())>1){
            retVal=false;
        }

        return retVal;
    }
}
