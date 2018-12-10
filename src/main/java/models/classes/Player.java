package models.classes;

import models.enums.PlayerColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {

    private List<Domino> dominoes;
    private List<King> kings;
    private Castle castle;
    private StartTile startTile;
    private PlayerColor playerColor;
    private Scanner scan = new Scanner(System.in);

    // TODO Add user board and other useful attributes

    public Player(PlayerColor playerColor) {
        this.setPlayerColor(playerColor);
        this.dominoes = new ArrayList<>();
        this.kings = new ArrayList<>();
    }

    /**
     *
     * GETTERS AND SETTERS
     *
     */

    public List<Domino> getDominoes() {
        return dominoes;
    }

    public void setDominoes(List<Domino> dominoes) {
        this.dominoes = dominoes;
    }

    public void addDomino(Domino domino) {
        this.dominoes.add(domino);
    }

    public List<King> getKings() {
        return kings;
    }

    public void setKings(List<King> kings) {
        this.kings = kings;
    }

    public void addKing(King king) {
        this.kings.add(king);
    }

    public Castle getCastle() {
        return castle;
    }

    public void setCastle(Castle castle) {
        this.castle = castle;
    }

    public StartTile getStartTile() {
        return startTile;
    }

    public void setStartTile(StartTile startTile) {
        this.startTile = startTile;
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }

    public void chooseColor(ArrayList<PlayerColor> playerColors){

        System.out.println("Choisissez votre couleur :");
        for(int i=0;i<playerColors.size();i++){                       // menu
            System.out.println("Tapez " + i + " pour choisir la couleur " + playerColors.get(i));
        }
        int numeroChoisi = scan.nextInt();
        scan.nextLine();
        this.setPlayerColor(playerColors.get(numeroChoisi));          //  set the color that was chosen
        playerColors.remove(numeroChoisi);                          //  remove the color in the list


    }



    public void chooseStartTile(ArrayList<StartTile> startTiles){
        Scanner scan = new Scanner(System.in);
        System.out.println("Choisissez votre tuile de départ :");
        for(int i=0;i<startTiles.size();i++){                       // menu
            System.out.println("Tapez " + i + " pour choisir la tuile : " + startTiles.get(i));
        }
        int numeroChoisi = scan.nextInt();
        scan.nextLine();
        this.setStartTile(startTiles.get(numeroChoisi));          //  set the tile that was chosen
        startTiles.remove(numeroChoisi);                          //  remove the tile in the liste
    }

    public void assignKings(int nbPlayer) {
        if (nbPlayer>2){
            this.kings.add(new King(this.playerColor));}
        else {
            for(int i=0;i<2;i++){
                this.kings.add(new King(this.playerColor));}
            }
        }
            
    }



}
