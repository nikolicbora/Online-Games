/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Random;

/**
 *
 * @author Nikola Markovic
 */
public class MojBrojEntity {
    private int[] jednocifreni = new int[4];
    private int dvocifreni; //10 15 ili 20
    private int trocifreni; //25 50 75 ili 100

    private int cilj; // 1 - 999
    private String pokusaj;
    
    public MojBrojEntity(){
        jednocifreni = new int[]{0, 0, 0, 0};
        dvocifreni = 0;
        trocifreni = 0;
        cilj = 0;
    }

    public String getPokusaj() {
        return pokusaj;
    }

    public void setPokusaj(String pokusaj) {
        this.pokusaj = pokusaj;
    }

    public int getCilj() {
        return cilj;
    }

    public void setCilj(int cilj) {
        this.cilj = cilj;
    }

    public int getDvocifreni() {
        return dvocifreni;
    }

    public void setDvocifreni(int dvocifreni) {
        this.dvocifreni = dvocifreni;
    }

    public int[] getJednocifreni() {
        return jednocifreni;
    }

    public void setJednocifreni(int[] jednocifreni) {
        this.jednocifreni = jednocifreni;
    }

    public int getTrocifreni() {
        return trocifreni;
    }

    public void setTrocifreni(int trocifreni) {
        this.trocifreni = trocifreni;
    }
    
    public void generisi(){
        boolean dodat = false;
        
        Random rnd = new Random();
        
        for (int i = 0; i < 4; i++){
            if (jednocifreni[i] == 0){
                jednocifreni[i] = rnd.nextInt(9) + 1;
                dodat = true;
                break;
            }
        }
        
        if (dodat){
            return;
        } else {
            if (dvocifreni == 0){
                int rndbroj = rnd.nextInt(3);
                
                switch(rndbroj){
                    case 0:
                        dvocifreni = 10;
                        break;
                    case 1:
                        dvocifreni = 15;
                        break;
                    case 2:
                        dvocifreni = 20;
                        break;
                }
            
                return;
            } else if (trocifreni == 0) {
                int rndbroj = rnd.nextInt(4);
                
                switch(rndbroj){
                    case 0:
                        trocifreni = 25;
                        break;
                    case 1:
                        trocifreni = 50;
                        break;
                    case 2:
                        trocifreni = 75;
                        break;
                    case 3:
                        trocifreni = 100;
                        break;
                }
            
                return;
            } else {
                if (cilj == 0){
                    cilj = rnd.nextInt(999) + 1;
                }
            }
        }
    }
}
