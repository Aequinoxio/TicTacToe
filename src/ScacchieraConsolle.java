/**
 * Implementazione della scacchiera per usarla sulla consolle
 */
class ScacchieraConsolle {
    final Scacchiera scacchiera = new Scacchiera();

    public Scacchiera getScacchiera() {
        return scacchiera;
    }

    public Scacchiera.Posizione getUltimaMossa() {
        return scacchiera.getUltimaMossa();
    }

    public boolean putMove(int riga, int colonna, Scacchiera.Simboli simbolo) {
        return scacchiera.putMove(riga, colonna, simbolo);
    }

    public boolean putMove(Scacchiera.Posizione posizione, Scacchiera.Simboli simbolo) {
        return scacchiera.putMove(posizione, simbolo);
    }

    public boolean isFree(Scacchiera.Posizione posizione) {
        return scacchiera.isFree(posizione);
    }

    public boolean isFree(int riga, int colonna) {
        return scacchiera.isFree(riga, colonna);
    }

    public Scacchiera.Simboli getSimbolo(int riga, int colonna) {
        return scacchiera.getSimbol(riga, colonna);
    }

    public Scacchiera.Vincitori whatWinner() {
        return scacchiera.whatIsTheWinner();
    }

    public int getFreeSpaces() {
        return scacchiera.getNumberOfFreeSpaces();
    }

    public void mostraScacchiera() {
        String t="";
        System.out.println("  "+"   0    " + "   1    " + "   2   ");
        for (int i = 0; i < 3; i++) {
            System.out.print(i+" ");
            for (int j = 0; j < 3; j++) {
                switch (scacchiera.getSimbol(i,j)){
                    case Vuoto:   t="       "; break;
                    case Cerchio: t="   O   "; break;
                    case Croce:   t="   X   "; break;
                }
                System.out.print(t);
                if (j!=2) {
                    System.out.print("|");
                }
            }

            System.out.println();
            if (i!=2) {
                System.out.println("  "+"-------+" + "-------+" + "-------");
            }
        }
    }
}
