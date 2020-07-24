/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpSession;
import models.AnagramEntity;
import models.FiveXFiveEntity;
import models.GeografijaEntity;
import models.MojBrojEntity;
import models.PartijaEntity;
import models.PeharEntity;
import models.RecnikEntity;
import models.UserEntity;
import models.VezaEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author nb150
 */
public class RezultatModel {
    
    private PartijaEntity partija;
    private MojBrojEntity moj_broj;
    private VezaEntity rezultat;
    
    private int poeni = 0;
    
    public RezultatModel(AnagramEntity a, MojBrojEntity mb, FiveXFiveEntity x5, GeografijaEntity g, PeharEntity p){
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        this.partija = (PartijaEntity) hibSession.get(PartijaEntity.class, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        hibSession.close();
        
        this.partija.setAnagram(a);
        this.moj_broj = mb;
        this.partija.setFiveXfive(x5);
        this.partija.setGeografija(g);
        this.partija.setPehar(p);
    }
    
    public void izracunajRezultat() throws ScriptException{
        rezultat = new VezaEntity();
        rezultat.setStatus(0);
        
        // Provera anagram
        if (partija.getAnagram().getPokusaj() != null){
            if (partija.getAnagram().getOdgovor1().equalsIgnoreCase(partija.getAnagram().getPokusaj())){
                poeni += 10;
                rezultat.setPoeni1(10);
            }
        }
        // ----
        // Provera moj broj
        if (moj_broj.getPokusaj() != null){
            if (!moj_broj.getPokusaj().equals("")){
                ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
                Object res = engine.eval(moj_broj.getPokusaj());
                ArrayList<String> brojevi = izvuciBrojeve(moj_broj.getPokusaj());

                if (res != null){
                    if (res.toString().equals("" + moj_broj.getCilj())){
                        boolean fer = true;

                        for (String broj : brojevi){
                            int br = Integer.parseInt(broj);
                            boolean j = false;
                            
                            for (int jednocifreni : moj_broj.getJednocifreni()){
                                if (br == jednocifreni){
                                    j = true;
                                }
                            }
                            
                            if (!j){
                                if (moj_broj.getDvocifreni() != br && moj_broj.getTrocifreni() != br){
                                    fer = false;
                                    break;
                                }
                            }
                        }

                        if (fer){
                            poeni += 10;
                            rezultat.setPoeni2(10);
                        }
                    }
                }
            }
        }
        // ----
        // Provera 5 x 5
        int i = 0;
        int poeni_za_5x5 = 0;
        
        for (String pok : partija.getFiveXfive().getPokusaj()){
            if (pok.equalsIgnoreCase(partija.getFiveXfive().getReci()[i])){
                poeni += 2;
                poeni_za_5x5 += 2;
            }
            
            i++;
        }
        
        for (i = 0; i < 5; i++){
            String pok = "";
            for (String p : partija.getFiveXfive().getPokusaj()){
                pok += p.charAt(i);
            }
            String rec = "";
            for (String r : partija.getFiveXfive().getReci()){
                rec += r.charAt(i);
            }
            
            if (pok.equalsIgnoreCase(rec)){
                poeni += 2;
                poeni_za_5x5 += 2;
            }
        }
        
        rezultat.setPoeni3(poeni_za_5x5);
        // ----
        // Provera geografija
        
        ArrayList<String> reci = new ArrayList<String>(){{
            add(partija.getGeografija().getRecDrzava());
            add(partija.getGeografija().getRecGrad());
            add(partija.getGeografija().getRecJezero());
            add(partija.getGeografija().getRecPlanina());
            add(partija.getGeografija().getRecReka());
            add(partija.getGeografija().getRecZivotinja());
            add(partija.getGeografija().getRecBiljka());
            add(partija.getGeografija().getRecGrupa());
        }};
        
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        
        String za_proveru = "";
        int poeni_za_geo = 0;
        for (String rec : reci){
            
            if (rec != null){
                if (rec.length() > 0){
                    if (("" + rec.charAt(0)).equalsIgnoreCase(partija.getGeografija().getSlovo())){
                        List<String> rez = hibSession.createCriteria(RecnikEntity.class).add(Restrictions.eq("rec", rec)).list();

                        if (rez.isEmpty()){
                            za_proveru += "" + (reci.indexOf(rec) + 1) + ":" + rec + ",";
                            rezultat.setStatus(1);
                        } else {
                            poeni_za_geo += 2;
                            poeni += 2;
                        }
                    }
                }
            }
        }
        
        rezultat.setPoeni4(poeni_za_geo);
        rezultat.setProvera(za_proveru);
        // ----
        // Provera pehar
        
        i = 0;
        int poeni_za_pehar = 0;
        for (String pok : partija.getPehar().getPokusaj()){
            if (pok.equalsIgnoreCase(partija.getPehar().getOdgovori()[i])){
                poeni += 2;
                poeni_za_pehar += 2;
            }
            i++;
        }
        
        rezultat.setPoeni5(poeni_za_pehar);
        // ----
        
        rezultat.setPartija(partija);
        
        HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

        rezultat.setUser(
                (UserEntity) hibSession.createCriteria(UserEntity.class).add(
                        Restrictions.eq("username", sesija.getAttribute("username").toString())
                ).list().get(0));
        
        Transaction t = hibSession.beginTransaction();
        hibSession.save(rezultat);
        t.commit();
    }
    
    private ArrayList<String> izvuciBrojeve(String izraz){
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(izraz);
        
        ArrayList<String> rezultat = new ArrayList<>();
        
        while(m.find()) {
            rezultat.add(m.group());
        }
        
        return rezultat;
    }

    public int getPoeni() {
        return poeni;
    }

    public void setPoeni(int poeni) {
        this.poeni = poeni;
    }

    public VezaEntity getRezultat() {
        return rezultat;
    }

    public void setRezultat(VezaEntity rezultat) {
        this.rezultat = rezultat;
    }
}
