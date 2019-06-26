class MainClass {
    private Scacchiera.Simboli squalificato = null;

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();

        mainClass.multiPartite();
        //mainClass.playGameWithHuman1();
        //mainClass.playGameWithHuman2();
        //mainClass.playGameWithHuman3();
        //mainClass.playGameWithHuman4();
        //mainClass.playGamePcVSPc6();
//        Scacchiera s = new Scacchiera();
////        mainClass.playGame(s,
////                new ComputerPlayerPaper(Scacchiera.Simboli.Croce, s, true),
////                new HumanPlayer(Scacchiera.Simboli.Cerchio, s, false)
////        );
////        s = new Scacchiera();
////        mainClass.playGame(s,
////                new HumanPlayer(Scacchiera.Simboli.Cerchio, s, true),
////                new ComputerPlayerSecondToMove(Scacchiera.Simboli.Croce, s, false)
////        );
////        s = new Scacchiera();
//        mainClass.playGame(s,
//                new ComputerPlayerPaper(Scacchiera.Simboli.Cerchio, s, true),
//                new ComputerPlayerSecondToMove(Scacchiera.Simboli.Croce, s, false),
//                false
//        );
//        s = new Scacchiera();
//        mainClass.playGame(s,
//                new ComputerPlayerSemiRandom(Scacchiera.Simboli.Cerchio, s, true),
//                new ComputerPlayerSecondToMove(Scacchiera.Simboli.Croce, s, false),
//                false
//        );
    }

    /**
     * Gioca più partite Computer vs Computer
     */
    private void multiPartite() {
        int vittorieCerchio = 0;
        int vittorieCroce = 0;
        int patte = 0;


        int MAXPARTITE = 1000;
        Scacchiera.Vincitori vincitore;

        for (int i = 0; i < MAXPARTITE; i++) {

            vincitore = playGamePcVSPc7();

            switch (vincitore) {
                case Croce:
                    vittorieCroce++;
                    break;
                case Cerchio:
                    vittorieCerchio++;
                    break;
                case Patta:
                    patte++;
                    break;
            }
        }


        System.out.println("Vittorie Cerchio: " + vittorieCerchio);
        System.out.println("Vittorie Croce: " + vittorieCroce);
        System.out.println("Patte: " + patte);
    }

    /**
     * Paper player vs human
     */
    private void playGameWithHuman1() {
        ScacchieraConsolle s = new ScacchieraConsolle();
        ComputerPlayerPaper cp = new ComputerPlayerPaper(Scacchiera.Simboli.Croce, s.getScacchiera(), true);
        HumanPlayer hp = new HumanPlayer(Scacchiera.Simboli.Cerchio, s.getScacchiera(), false);
        boolean exitCondition = false;
        boolean mossaLecita;
        Scacchiera.Simboli squalificato = null;

        System.out.println("Gioca  : player 1 " + cp +
                "\ncontro : player 2 " + hp);
        System.out.println("Inizia: " + cp.getMioSimbolo());
        //System.out.print("Turno al PC: ");

        while (!exitCondition) {

            //
            Scacchiera.Posizione posPc = cp.miaMossa();
            System.out.println(posPc);
            mossaLecita = s.putMove(posPc, cp.getMioSimbolo());
            if (!mossaLecita) {
                System.out.println("PC: Mossa non lecita. Squalificato");
                squalificato = cp.getMioSimbolo();
                exitCondition = true;
                continue;
            }

            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
                exitCondition = true;
                s.mostraScacchiera();
                continue;
            }


            s.mostraScacchiera();
            System.out.print("Mossa (" + hp.getExitString() + " per uscire) : ");
            Scacchiera.Posizione posizione = hp.miaMossa();
            if (hp.voglioTerminare()) {
                exitCondition = true;
                continue;
            }
            mossaLecita = s.putMove(posizione, hp.getMioSimbolo());
            if (!mossaLecita) {
                System.out.println("Umano: mossa non lecita. Squalificato");
                squalificato = hp.getMioSimbolo();
                exitCondition = true;
                continue;
            }
            s.mostraScacchiera();

//            if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
                exitCondition = true;
            }
        }

        if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
            System.out.println("Vincitore: " + s.whatWinner());
        } else {
            if (squalificato == null) {
                System.out.println("Nessun vincitore");
            } else {
                System.out.println("Squalificato: " + squalificato);
            }

        }
    }

    /**
     * Mossa al computer
     *
     * @return true se esiste una mossa possibile, false se la mossa è illecita
     */
    private boolean movePC(ScacchieraConsolle scacchiera, Player computerPlayer, boolean printScacchiera) {
        boolean mossaLecita;
        Scacchiera.Posizione posPc = computerPlayer.miaMossa();

        //DEBUG
        //System.out.println(posPc);
        //
        mossaLecita = scacchiera.putMove(posPc, computerPlayer.getMioSimbolo());
        if (!mossaLecita) {
            System.out.println("PC: Mossa non lecita. Squalificato");
            squalificato = computerPlayer.getMioSimbolo();
            return false;
        }

        if (printScacchiera) {
            scacchiera.mostraScacchiera();
        }
        return true;
    }

    /**
     * Mossa al giocatore. Mostro sempre la scacchiera
     *
     * @param scacchiera  Scacchiera su cui fare la mossa
     * @param humanPlayer Oggetto che rappresenta il giocatore umano
     * @return true se la mossa è valida, false altrmenti
     */
    private boolean moveHuman(ScacchieraConsolle scacchiera, Player humanPlayer) {
        boolean mossaLecita;
        System.out.print(humanPlayer.getMioSimbolo() + " - Mossa: ");

        mossaLecita = scacchiera.putMove(humanPlayer.miaMossa(), humanPlayer.getMioSimbolo());
        if (!mossaLecita) {
            System.out.println("Umano: mossa non lecita. Squalificato");
            squalificato = humanPlayer.getMioSimbolo();
            return false;
        }

        scacchiera.mostraScacchiera();

        return true;
    }

    /**
     * Computer contro umano. Il computer segue la strategia paper modificata
     */
    private void playGameWithHuman2() {
        ScacchieraConsolle s = new ScacchieraConsolle();
        ComputerPlayer cp = new ComputerPlayer(Scacchiera.Simboli.Croce, s.getScacchiera(), true);
        HumanPlayer hp = new HumanPlayer(Scacchiera.Simboli.Cerchio, s.getScacchiera(), false);
        boolean exitCondition = false;

        System.out.println("Gioca  : player 1 " + cp +
                "\ncontro : player 2 " + hp);
        System.out.println("Inizia: " + cp.getMioSimbolo());
        //System.out.println("Turno al PC: ");

        while (!exitCondition) {
            exitCondition = !movePC(s, cp, true);
            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
                exitCondition = true;
                continue;
            }
            if (exitCondition) {
                continue;
            }

            exitCondition = !moveHuman(s, hp);
            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
                exitCondition = true;
            }

        }
        if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
            System.out.println("Vincitore: " + s.whatWinner());
        } else {
            if (squalificato == null) {
                System.out.println("Nessun vincitore");
            } else {
                System.out.println("Squalificato: " + squalificato);
            }

        }
    }

    /**
     * Umano vs Computer, muove prima l'umano. Il computer segue la strategia paper modificata
     */
    private void playGameWithHuman3() {
        boolean exitCondition = false;
        ScacchieraConsolle s = new ScacchieraConsolle();
        ComputerPlayer cp;
        HumanPlayer hp;

        cp = new ComputerPlayer(Scacchiera.Simboli.Croce, s.getScacchiera(), false);
        hp = new HumanPlayer(Scacchiera.Simboli.Cerchio, s.getScacchiera(), true);

        System.out.println("Gioca  : player 1 " + cp +
                "\ncontro : player 2 " + hp);
        System.out.println("Inizia: " + hp.getMioSimbolo());
        //System.out.print("Turno al PC: ");
        s.mostraScacchiera();
        while (!exitCondition) {
            exitCondition = !moveHuman(s, hp);
            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
                exitCondition = true;
                continue;
            }
            if (exitCondition) {
                continue;
            }

            exitCondition = !movePC(s, cp, true);
            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
                exitCondition = true;
            }

        }
        if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
            System.out.println("Vincitore: " + s.whatWinner());
        } else {
            if (squalificato == null) {
                System.out.println("Nessun vincitore");
            } else {
                System.out.println("Squalificato: " + squalificato);
            }

        }
    }


    /**
     * Paper Player contro Paper Player modificato. inizia paperplayer
     *
     * @return simbolo del vincitore; simbolo vuoto se è patta
     */
    private Scacchiera.Vincitori playGamePcVSPc1() {
        boolean exitCondition = false;
        ScacchieraConsolle s = new ScacchieraConsolle();
        ComputerPlayer player1;
        ComputerPlayerPaper player2;

        player2 = new ComputerPlayerPaper(Scacchiera.Simboli.Cerchio, s.getScacchiera(), true);
        //player2.setDescrizione("Paper player");
        player1 = new ComputerPlayer(Scacchiera.Simboli.Croce, s.getScacchiera(), false);
        //player1.setDescrizione("Paper modified player");

        System.out.println("Gioca  : player 1 " + player1 +
                "\ncontro : player 2 " + player2);
        System.out.println("Inizia: " + player2.getMioSimbolo());
        //System.out.print("Turno al PC: ");
        s.mostraScacchiera();
        while (!exitCondition) {
            exitCondition = !movePC(s, player2, false);
            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
                exitCondition = true;
                continue;
            }
            if (exitCondition) {
                continue;
            }

            exitCondition = !movePC(s, player1, false);
            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
                exitCondition = true;
            }

        }

        Scacchiera.Vincitori vincitore = Scacchiera.Vincitori.Patta;


        if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
            System.out.println("Vincitore: " + s.whatWinner());
            vincitore = s.whatWinner();
        } else {
            if (squalificato == null) {
                System.out.println("Nessun vincitore");
            } else {
                System.out.println("Squalificato: " + squalificato);
            }

        }

        return (vincitore);
    }


    /**
     * Semirandom contro paper player. Inizia paperPlayer
     *
     * @return simbolo del vincitore; simbolo vuoto se è patta
     */
    private Scacchiera.Vincitori playGamePcVSPc2() {
        boolean exitCondition = false;
        ComputerPlayerSemiRandom player1;
        ComputerPlayerPaper player2;

        ScacchieraConsolle s = new ScacchieraConsolle();

        player2 = new ComputerPlayerPaper(Scacchiera.Simboli.Cerchio, s.getScacchiera(), true);
        //player2.setDescrizione("Paper player");
        player1 = new ComputerPlayerSemiRandom(Scacchiera.Simboli.Croce, s.getScacchiera(), false);
        //player1.setDescrizione("Paper modified player");

        System.out.println("Gioca  : player 1 " + player1 +
                "\ncontro : player 2 " + player2);
        System.out.println("Inizia: " + player2.getMioSimbolo());
        //System.out.print("Turno al PC: ");
        s.mostraScacchiera();
        while (!exitCondition) {
            exitCondition = !movePC(s, player2, false);
            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
                exitCondition = true;
                continue;
            }
            if (exitCondition) {
                continue;
            }

            exitCondition = !movePC(s, player1, false);
            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
                exitCondition = true;
            }

        }

        // TODO: Mancano gli altri stati: patta e squalificato (tra due robot non dovrebbe accadere per cui modello lo stato patta come vuoto)
        Scacchiera.Vincitori vincitore = Scacchiera.Vincitori.Patta;

        if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
            System.out.println("Vincitore: " + s.whatWinner());
            vincitore = s.whatWinner();
        } else {
            if (squalificato == null) {
                System.out.println("Nessun vincitore");
            } else {
                System.out.println("Squalificato: " + squalificato);
            }

        }

        return (vincitore);
    }


    /**
     * Semirandom contro Semirandom
     *
     * @return simbolo del vincitore; simbolo vuoto se è patta
     */
    private Scacchiera.Vincitori playGamePcVSPc3() {
        boolean exitCondition = false;
        ComputerPlayerSemiRandom player1;
        ComputerPlayerSemiRandom player2;

        ScacchieraConsolle s = new ScacchieraConsolle();

        player2 = new ComputerPlayerSemiRandom(Scacchiera.Simboli.Cerchio, s.getScacchiera(), true);
        //player2.setDescrizione("Paper player");
        player1 = new ComputerPlayerSemiRandom(Scacchiera.Simboli.Croce, s.getScacchiera(), false);
        //player1.setDescrizione("Paper modified player");

        System.out.println("Gioca  : player 1 " + player1 +
                "\ncontro : player 2 " + player2);
        System.out.println("Inizia: " + player2.getMioSimbolo());

        //s.print();
        while (!exitCondition) {
            exitCondition = !movePC(s, player2, false);
            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
                exitCondition = true;
                continue;
            }
            if (exitCondition) {
                continue;
            }

            exitCondition = !movePC(s, player1, false);
            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
                exitCondition = true;
            }
        }

        // TODO: Mancano gli altri stati: patta e squalificato (tra due robot non dovrebbe accadere per cui modello lo stato patta come vuoto)
        Scacchiera.Vincitori vincitore = Scacchiera.Vincitori.Patta;

        if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
            System.out.println("Vincitore: " + s.whatWinner());
            vincitore = s.whatWinner();
        } else {
            if (squalificato == null) {
                System.out.println("Nessun vincitore");
            } else {
                System.out.println("Squalificato: " + squalificato);
            }

        }

        return (vincitore);
    }


    /**
     * Semirandom contro Paper player. Inizia Semirandom
     *
     * @return simbolo del vincitore; simbolo vuoto se è patta
     */
    private Scacchiera.Vincitori playGamePcVSPc4() {
        boolean exitCondition = false;
        ComputerPlayerSemiRandom player1;
        ComputerPlayerPaper player2;

        ScacchieraConsolle s = new ScacchieraConsolle();

        player1 = new ComputerPlayerSemiRandom(Scacchiera.Simboli.Croce, s.getScacchiera(), true);
        //player1.setDescrizione("Paper modified player");
        player2 = new ComputerPlayerPaper(Scacchiera.Simboli.Cerchio, s.getScacchiera(), false);
        //player2.setDescrizione("Paper player");

        System.out.println("Gioca  : player 1 " + player1 +
                "\ncontro : player 2 " + player2);
        System.out.println("Inizia: " + player1.getMioSimbolo());
        //System.out.print("Turno al PC: ");
        s.mostraScacchiera();
        while (!exitCondition) {
            exitCondition = !movePC(s, player1, false);
            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
                exitCondition = true;
                continue;
            }
            if (exitCondition) {
                continue;
            }

            exitCondition = !movePC(s, player2, false);
            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
                exitCondition = true;
            }

        }

        // TODO: Mancano gli altri stati: patta e squalificato (tra due robot non dovrebbe accadere per cui modello lo stato patta come vuoto)
        Scacchiera.Vincitori vincitore = Scacchiera.Vincitori.Patta;

        if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
            System.out.println("Vincitore: " + s.whatWinner());
            vincitore = s.whatWinner();
        } else {
            if (squalificato == null) {
                System.out.println("Nessun vincitore");
            } else {
                System.out.println("Squalificato: " + squalificato);
            }

        }

        return (vincitore);
    }


    /**
     * Paper Player contro Paper player. Inizia Semirandom
     *
     * @return simbolo del vincitore; simbolo vuoto se è patta
     */
    private Scacchiera.Vincitori playGamePcVSPc5() {
        boolean exitCondition = false;
        ComputerPlayerPaper player1;
        ComputerPlayerPaper player2;

        ScacchieraConsolle s = new ScacchieraConsolle();

        player1 = new ComputerPlayerPaper(Scacchiera.Simboli.Croce, s.getScacchiera(), true);
        //player1.setDescrizione("Paper modified player");
        player2 = new ComputerPlayerPaper(Scacchiera.Simboli.Cerchio, s.getScacchiera(), false);
        //player2.setDescrizione("Paper player");

        System.out.println("Gioca  : player 1 " + player1 +
                "\ncontro : player 2 " + player2);
        System.out.println("Inizia: " + player1.getMioSimbolo());
        //System.out.print("Turno al PC: ");
        s.mostraScacchiera();
        while (!exitCondition) {
            exitCondition = !movePC(s, player1, false);
            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
                exitCondition = true;
                continue;
            }
            if (exitCondition) {
                continue;
            }

            exitCondition = !movePC(s, player2, false);
            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
                exitCondition = true;
            }

        }

        // TODO: Mancano gli altri stati: patta e squalificato (tra due robot non dovrebbe accadere per cui modello lo stato patta come vuoto)
        Scacchiera.Vincitori vincitore = Scacchiera.Vincitori.Patta;

        if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
            System.out.println("Vincitore: " + s.whatWinner());
            vincitore = s.whatWinner();
        } else {
            if (squalificato == null) {
                System.out.println("Nessun vincitore");
            } else {
                System.out.println("Squalificato: " + squalificato);
            }

        }

        return (vincitore);
    }


    /**
     * Paper Player contro Second computer player. Inizia Semirandom
     *
     * @return simbolo del vincitore; simbolo vuoto se è patta
     */
    private Scacchiera.Vincitori playGamePcVSPc6() {
        boolean exitCondition = false;
        ComputerPlayerPaper player1;
        ComputerPlayerSecondToMove player2;

        ScacchieraConsolle s = new ScacchieraConsolle();

        player1 = new ComputerPlayerPaper(Scacchiera.Simboli.Croce, s.getScacchiera(), true);
        //player1.setDescrizione("Paper modified player");
        player2 = new ComputerPlayerSecondToMove(Scacchiera.Simboli.Cerchio, s.getScacchiera(), false);
        //player2.setDescrizione("Paper player");

        System.out.println("Gioca  : player 1 " + player1 +
                "\ncontro : player 2 " + player2);
        System.out.println("Inizia: " + player1.getMioSimbolo());
        //System.out.print("Turno al PC: ");
        s.mostraScacchiera();
        while (!exitCondition) {
            exitCondition = !movePC(s, player1, false);
            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
                exitCondition = true;
                continue;
            }
            if (exitCondition) {
                continue;
            }

            exitCondition = !movePC(s, player2, false);
            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
                exitCondition = true;
            }

        }

        Scacchiera.Vincitori vincitore = Scacchiera.Vincitori.Patta;

        if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
            System.out.println("Vincitore: " + s.whatWinner());
            vincitore = s.whatWinner();
        } else {
            if (squalificato == null) {
                System.out.println("Nessun vincitore");
            } else {
                System.out.println("Squalificato: " + squalificato);
            }

        }

        return (vincitore);
    }

    /**
     * Paper Player contro Secndmove. Inizia secondmove (iniziano a parti invertite: paper è fatto per iniziare per primo l'altro per secondo)
     *
     * @return simbolo del vincitore; simbolo vuoto se è patta
     */
    private Scacchiera.Vincitori playGamePcVSPc7() {

        ComputerPlayerSecondToMove player1;
        ComputerPlayerPaper player2;
        ScacchieraConsolle s = new ScacchieraConsolle();

        player1 = new ComputerPlayerSecondToMove(Scacchiera.Simboli.Cerchio, s.getScacchiera(), true);
        player2 = new ComputerPlayerPaper(Scacchiera.Simboli.Croce, s.getScacchiera(), false);

        System.out.println("Gioca  : player 1 " + player1 +
                "\ncontro : player 2 " + player2);
        System.out.println("Inizia: " + player1.getMioSimbolo());

        s.mostraScacchiera();
        return giocaTraPC(s.getScacchiera(),player1,player2,true);
//        while (!exitCondition) {
//            exitCondition = !movePC(s, player1, false);
//            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
//            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
//                exitCondition = true;
//                continue;
//            }
//            if (exitCondition) {
//                continue;
//            }
//
//            exitCondition = !movePC(s, player2, false);
//            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
//            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
//                exitCondition = true;
//            }
//
//        }
//
//        Scacchiera.Vincitori vincitore = Scacchiera.Vincitori.Patta;
//
//        if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
//            System.out.println("Vincitore: " + s.whatWinner());
//            vincitore = s.whatWinner();
//        } else {
//            if (squalificato == null) {
//                System.out.println("Nessun vincitore");
//            } else {
//                System.out.println("Squalificato: " + squalificato);
//            }
//
//        }
//
//        return (vincitore);
    }

    private Scacchiera.Vincitori giocaTraPC(Scacchiera scacchiera, Player player1 , Player player2,boolean mostraScacchiera){
        boolean exitCondition = false;
        ScacchieraConsolle s = new ScacchieraConsolle(scacchiera);
        while (!exitCondition) {
            exitCondition = !movePC(s, player1, mostraScacchiera);
            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
                exitCondition = true;
                continue;
            }
            if (exitCondition) {
                continue;
            }

            exitCondition = !movePC(s, player2, mostraScacchiera);
            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
                exitCondition = true;
            }

        }

        Scacchiera.Vincitori vincitore = Scacchiera.Vincitori.Patta;

        if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
            System.out.println("Vincitore: " + s.whatWinner());
            vincitore = s.whatWinner();
        } else {
            if (squalificato == null) {
                System.out.println("Nessun vincitore");
            } else {
                System.out.println("Squalificato: " + squalificato);
            }

        }

        return (vincitore);
    }

    /**
     * Umano vs Computer, muove prima l'umano. Il computer segue la strategia player secondo
     */
    private void playGameWithHuman4() {
        boolean exitCondition = false;
        ScacchieraConsolle s = new ScacchieraConsolle();
        ComputerPlayerSecondToMove cp;
        HumanPlayer hp;

        cp = new ComputerPlayerSecondToMove(Scacchiera.Simboli.Croce, s.getScacchiera(), false);
        hp = new HumanPlayer(Scacchiera.Simboli.Cerchio, s.getScacchiera(), true);

        System.out.println("Gioca  : player 1 " + cp +
                "\ncontro : player 2 " + hp);
        System.out.println("Inizia: " + hp.getMioSimbolo());
        //System.out.print("Turno al PC: ");
        s.mostraScacchiera();
        while (!exitCondition) {
            exitCondition = !moveHuman(s, hp);
            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
                exitCondition = true;
                continue;
            }
            if (exitCondition) {
                continue;
            }

            exitCondition = !movePC(s, cp, true);
            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
                exitCondition = true;
            }

        }
        if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
            System.out.println("Vincitore: " + s.whatWinner());
        } else {
            if (squalificato == null) {
                System.out.println("Nessun vincitore");
            } else {
                System.out.println("Squalificato: " + squalificato);
            }

        }
    }


    /**
     * Metodo generico per giocare, muove prima player 1
     * @param scacchiera Scacchiera su cui giocare
     * @param player1 Primo giocatore
     * @param player2 Secondo giocatore
     * @param showMoves true mostra tutte le mosse sel PC, false altrimenti ma mostra la scacchiera finale
     */
    private Scacchiera.Vincitori  playGame(Scacchiera scacchiera, Player player1, Player player2, boolean showMoves) {
        boolean exitCondition = false;
        ScacchieraConsolle s = new ScacchieraConsolle(scacchiera);

        System.out.println("Gioca  : player 1 " + player1 +
                "\ncontro : player 2 " + player2);
        System.out.println("Inizia: " + player1.getMioSimbolo());

        if (showMoves) {
            s.mostraScacchiera();
        }
        while (!exitCondition) {
            if (player1 instanceof HumanPlayer) {
                exitCondition = !moveHuman(s, player1);
            } else {
                exitCondition = !movePC(s, player1, showMoves);
            }
            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
                exitCondition = true;
                continue;
            }

            if (exitCondition) {
                continue;
            }

            if (player2 instanceof HumanPlayer) {
                exitCondition = !moveHuman(s, player2);
            } else {
                exitCondition = !movePC(s, player2, showMoves);
            }

            //if (s.getFreeSpaces() == 0 || s.whatWinner() != null) {
            if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
                exitCondition = true;
            }

        }

        Scacchiera.Vincitori vincitore = Scacchiera.Vincitori.Patta;

        if (s.whatWinner() != Scacchiera.Vincitori.NonDecidibile) {
            System.out.println("Vincitore: " + s.whatWinner());
            vincitore=s.whatWinner();
        } else {
            if (squalificato == null) {
                System.out.println("Nessun vincitore");
                vincitore= Scacchiera.Vincitori.Patta;
            } else {
                System.out.println("Squalificato: " + squalificato);
                switch (squalificato){
                    case Cerchio:
                        vincitore = Scacchiera.Vincitori.Croce; break;
                    case Croce:
                        vincitore = Scacchiera.Vincitori.Cerchio; break;
                    default: // Non dovrei mai arrivarci ma la metto per sicurezza
                        vincitore= Scacchiera.Vincitori.Patta;
                }

            }

        }
        if (!showMoves) {
            s.mostraScacchiera();
        }

        return vincitore;
    }
}
