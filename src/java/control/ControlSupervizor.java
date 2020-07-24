/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import models.AnagramEntity;
import models.FiveXFiveEntity;
import models.KategorijaReciEntity;
import models.PeharEntity;
import models.RecnikEntity;
import models.VezaEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtil;

/**
 *
 * @author nb150
 */
@ManagedBean
@SessionScoped
public class ControlSupervizor implements Serializable{
    private String novaRec;
    private String novaKategorija;

    private String anagramPitanje;
    private String anagramOdgovor;

    private String fiveRec1;
    private String fiveRec2;
    private String fiveRec3;
    private String fiveRec4;
    private String fiveRec5;

    private String[] peharPitanja = new String[13];
    private String[] peharOdgovori = new String[13];
    
    private List<VezaEntity> list_igara = new ArrayList<VezaEntity>();
    private List<RecZaProveru> list_reci = new ArrayList<>();
    private VezaEntity odabrana_igra = null;
    private boolean igra_prikazana = false;
    
    public ControlSupervizor(){
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        
        list_igara = hibSession.createCriteria(VezaEntity.class).add(Restrictions.eq("status", 1)).list();
        
        hibSession.close();
    }
    
    public String napraviPrikaz(VezaEntity igra){
        String prikaz = "";
        
        String provera = igra.getProvera();
        
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        
        for (String rec : provera.split(",")){
            if (!rec.trim().equals("")){
            String kat = rec.split(":")[0];
            String r = rec.split(":")[1];
            
            prikaz += ((KategorijaReciEntity)hibSession.get(KategorijaReciEntity.class, Integer.parseInt(kat))).getKategorija() + " - " + r + "<br/>";
            }
        }
        
        hibSession.close();
        
        return prikaz;
    }
    
    public String prikazIgre(VezaEntity igra)
    {
        igra_prikazana = true;
        
        odabrana_igra = igra;
        
        String za_proveru = napraviPrikaz(igra);
        
        list_reci.clear();
        
        for(String rec : za_proveru.split("<br/>"))
        {
            String kat = rec.split(" - ")[0];
            String r = rec.split(" - ")[1];
            
            RecZaProveru recZaProveru = new RecZaProveru();
            recZaProveru.setKategorija(kat);
            recZaProveru.setRec(r);
            
            list_reci.add(recZaProveru);
        }
        
        return null;
    }

    public String odobriRec(RecZaProveru rec)
    {
        String provera = odabrana_igra.getProvera();
        String nova_provera = "";
        
        for (String recs : provera.split(","))
        {
            if (!recs.trim().equals(""))
            {
                if (!recs.split(":")[1].equals(rec.rec))
                {
                    nova_provera += recs+",";
                }
            }
        }
        
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        
        odabrana_igra.setProvera(nova_provera);
        
        if (nova_provera.equals(""))
        {
            odabrana_igra.setStatus(0);
            odabrana_igra.setPoeni4(odabrana_igra.getPoeni4() + 2);
            
            Transaction t = hibSession.beginTransaction();
            hibSession.saveOrUpdate(odabrana_igra);
            t.commit();
            
           igra_prikazana = false;
           list_reci.clear();
           list_igara.remove(odabrana_igra);
           odabrana_igra = null;
        } 
        else 
        {
            odabrana_igra.setPoeni4(odabrana_igra.getPoeni4() + 2);
            
            Transaction t = hibSession.beginTransaction();
            hibSession.saveOrUpdate(odabrana_igra);
            t.commit(); 
            
            list_reci.remove(rec);
        }
        
        hibSession.close();
        return null;
    }
    
     public String odbaciRec(RecZaProveru rec)
     {
        String provera = odabrana_igra.getProvera();
        String nova_provera = "";
        
        for (String recs : provera.split(","))
        {
            if (!recs.trim().equals(""))
            {
                if (!recs.split(":")[1].equals(rec.rec))
                {
                    nova_provera += recs+",";
                }
            }
        }
        
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        
        odabrana_igra.setProvera(nova_provera);
        
        if (nova_provera.equals(""))
        {
            odabrana_igra.setStatus(0);
            
            Transaction t = hibSession.beginTransaction();
            hibSession.saveOrUpdate(odabrana_igra);
            t.commit();
            
           igra_prikazana = false;
           list_reci.clear();
           list_igara.remove(odabrana_igra);
           odabrana_igra = null;
        } 
        else 
        {
            Transaction t = hibSession.beginTransaction();
            hibSession.saveOrUpdate(odabrana_igra);
            t.commit(); 
            
            list_reci.remove(rec);
        }
        
        hibSession.close();
        return null;
    }
    
    public List<RecZaProveru> getList_reci() {
        return list_reci;
    }

    public void setList_reci(List<RecZaProveru> list_reci) {
        this.list_reci = list_reci;
    }

    public void setIgra_prikazana(boolean igra_prikazana) {
        this.igra_prikazana = igra_prikazana;
    }

    public boolean isIgra_prikazana() {
        return igra_prikazana;
    }
    
    public List<VezaEntity> getList_igara() {
        return list_igara;
    }

    public void setList_igara(List<VezaEntity> list_igara) {
        this.list_igara = list_igara;
    }
    
    public String getFiveRec1() {
        return fiveRec1;
    }

    public void setFiveRec1(String fiveRec1) {
        this.fiveRec1 = fiveRec1;
    }

    public String getFiveRec2() {
        return fiveRec2;
    }

    public void setFiveRec2(String fiveRec2) {
        this.fiveRec2 = fiveRec2;
    }

    public String getFiveRec3() {
        return fiveRec3;
    }

    public void setFiveRec3(String fiveRec3) {
        this.fiveRec3 = fiveRec3;
    }

    public String getFiveRec4() {
        return fiveRec4;
    }

    public void setFiveRec4(String fiveRec4) {
        this.fiveRec4 = fiveRec4;
    }

    public String getFiveRec5() {
        return fiveRec5;
    }

    public void setFiveRec5(String fiveRec5) {
        this.fiveRec5 = fiveRec5;
    }
    
    public String getAnagramOdgovor() {
        return anagramOdgovor;
    }

    public void setAnagramOdgovor(String anagramOdgovor) {
        this.anagramOdgovor = anagramOdgovor;
    }

    public String getAnagramPitanje() {
        return anagramPitanje;
    }

    public void setAnagramPitanje(String anagramPitanje) {
        this.anagramPitanje = anagramPitanje;
    }
    
    public String getNovaKategorija() {
        return novaKategorija;
    }

    public void setNovaKategorija(String novaKategorija) {
        this.novaKategorija = novaKategorija;
    }

    public String getNovaRec() {
        return novaRec;
    }

    public void setNovaRec(String novaRec) {
        this.novaRec = novaRec;
    }

    public String[] getPeharOdgovori() {
        return peharOdgovori;
    }

    public void setPeharOdgovori(String[] peharOdgovori) {
        this.peharOdgovori = peharOdgovori;
    }

    public String[] getPeharPitanja() {
        return peharPitanja;
    }

    public void setPeharPitanja(String[] peharPitanja) {
        this.peharPitanja = peharPitanja;
    }
    
    public String dodajRec(){  
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        
        KategorijaReciEntity kategorija = (KategorijaReciEntity) hibSession.createCriteria(KategorijaReciEntity.class).add(Restrictions.eq("kategorija", novaKategorija)).list().get(0);
        
        RecnikEntity rec = new RecnikEntity();
        rec.setKategorija(kategorija);
        rec.setRec(novaRec);
        
        List<RecnikEntity> postojeca = hibSession.createCriteria(RecnikEntity.class).add(Restrictions.eq("rec", novaRec)).add(Restrictions.eq("kategorija", kategorija)).list();
        
        if (postojeca.size() == 0){
            Transaction t = hibSession.beginTransaction();
            hibSession.save(rec);
            t.commit();
            
            novaRec = "";
            
            String poruka = "rec uspesno dodata!";
            FacesContext.getCurrentInstance().addMessage("forma:error", new FacesMessage(FacesMessage.SEVERITY_INFO, poruka, poruka));
        } else {
            String poruka = "uneta rec vec postoji u recniku!";
            FacesContext.getCurrentInstance().addMessage("forma:error", new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, poruka));
        }
        
        hibSession.close();
        return null;
    }
    
    public String dodajAnagram(){
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        
        List<AnagramEntity> postojeci = hibSession.createCriteria(AnagramEntity.class).add(Restrictions.eq("pitanje1", anagramPitanje)).list();
        
        if (postojeci.size() == 0){
            
            
            
            AnagramEntity anagram = new AnagramEntity();
            anagram.setOdgovor1(anagramOdgovor);
            anagram.setPitanje1(anagramPitanje);
            
            Transaction t = hibSession.beginTransaction();
            hibSession.save(anagram);
            t.commit();
            
            anagramOdgovor = "";
            anagramPitanje = "";
            
            String poruka = "anagram uspesno dodat!";
            FacesContext.getCurrentInstance().addMessage("forma:error", new FacesMessage(FacesMessage.SEVERITY_INFO, poruka, poruka));
        } else {
            String poruka = "uneti anagram vec postoji!";
            FacesContext.getCurrentInstance().addMessage("forma:error", new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, poruka));
        }
        
        hibSession.close();
        return null;
    }
    
    public String dodaj5x5(){
        
        if(fiveRec1.length()!=5 || fiveRec2.length()!=5 ||fiveRec3.length()!=5 ||fiveRec4.length()!=5 ||fiveRec5.length()!=5)
        {
            String upozorenje="duzina svih 5 reci mora biti tacno 5 slova";
            FacesContext.getCurrentInstance().addMessage("forma:error", new FacesMessage(FacesMessage.SEVERITY_INFO, upozorenje, upozorenje));
            return null;
        }
        
        
        Session hibSession = HibernateUtil.getSessionFactory().openSession();

        FiveXFiveEntity five = new FiveXFiveEntity();
        five.setRec1(fiveRec1);
        five.setRec2(fiveRec2);
        five.setRec3(fiveRec3);
        five.setRec4(fiveRec4);
        five.setRec5(fiveRec5);
        
        Transaction t = hibSession.beginTransaction();
        hibSession.save(five);
        t.commit();

        fiveRec1 = "";
        fiveRec2 = "";
        fiveRec3 = "";
        fiveRec4 = "";
        fiveRec5 = "";
        
        String poruka = "igra uspesno dodata!";
        FacesContext.getCurrentInstance().addMessage("forma:error", new FacesMessage(FacesMessage.SEVERITY_INFO, poruka, poruka));

        hibSession.close();
        return null;
    }
    
    public String dodajPehar()
    {
        
        if(isduzina()==false)
        {
            String upozorenje="duzina reci ne postuje pravila igre";
            FacesContext.getCurrentInstance().addMessage("forma:error", new FacesMessage(FacesMessage.SEVERITY_INFO, upozorenje, upozorenje));
            return null;
        }
        if(isuslov()==false)
        {
            
            String upozorenje="rasporedjenost slova ne postuje pravila igre";
            FacesContext.getCurrentInstance().addMessage("forma:error", new FacesMessage(FacesMessage.SEVERITY_INFO, upozorenje, upozorenje));
            return null;
        }
        
        
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        
        PeharEntity pehar = new PeharEntity();
        pehar.setO1(peharOdgovori[0]);
        pehar.setO2(peharOdgovori[1]);
        pehar.setO3(peharOdgovori[2]);
        pehar.setO4(peharOdgovori[3]);
        pehar.setO5(peharOdgovori[4]);
        pehar.setO6(peharOdgovori[5]);
        pehar.setO7(peharOdgovori[6]);
        pehar.setO8(peharOdgovori[7]);
        pehar.setO9(peharOdgovori[8]);
        pehar.setO10(peharOdgovori[9]);
        pehar.setO11(peharOdgovori[10]);
        pehar.setO12(peharOdgovori[11]);
        pehar.setO13(peharOdgovori[12]);
       
        pehar.setP1(peharPitanja[0]);
        pehar.setP2(peharPitanja[1]);
        pehar.setP3(peharPitanja[2]);
        pehar.setP4(peharPitanja[3]);
        pehar.setP5(peharPitanja[4]);
        pehar.setP6(peharPitanja[5]);
        pehar.setP7(peharPitanja[6]);
        pehar.setP8(peharPitanja[7]);
        pehar.setP9(peharPitanja[8]);
        pehar.setP10(peharPitanja[9]);
        pehar.setP11(peharPitanja[10]);
        pehar.setP12(peharPitanja[11]);
        pehar.setP13(peharPitanja[12]);
        
        Transaction t = hibSession.beginTransaction();
        hibSession.save(pehar);
        t.commit();
        
        String poruka = "igra uspesno dodata!";
        FacesContext.getCurrentInstance().addMessage("forma:error", new FacesMessage(FacesMessage.SEVERITY_INFO, poruka, poruka));

        peharPitanja = new String[13];
        peharOdgovori = new String[13];
        
        hibSession.close();
        return null;
    }
    
    private boolean isduzina()
    {
        boolean ok=true;
        if(peharOdgovori[0].length()!=9){ok=false;}
        if(peharOdgovori[1].length()!=8){ok=false;}
        if(peharOdgovori[2].length()!=7){ok=false;}
        if(peharOdgovori[3].length()!=6){ok=false;}
        if(peharOdgovori[4].length()!=5){ok=false;}
        if(peharOdgovori[5].length()!=4){ok=false;}
        if(peharOdgovori[6].length()!=3){ok=false;}
        if(peharOdgovori[7].length()!=4){ok=false;}
        if(peharOdgovori[8].length()!=5){ok=false;}
        if(peharOdgovori[9].length()!=6){ok=false;}
        if(peharOdgovori[10].length()!=7){ok=false;}
        if(peharOdgovori[11].length()!=8){ok=false;}
        if(peharOdgovori[12].length()!=9){ok=false;}
        return ok;
    }
    
    private boolean isuslov()
    {
        boolean ok=true;
        for(int i=6;i>=1;i--)
        {
            
            String s1=peharOdgovori[i].toLowerCase();
            String s2=peharOdgovori[i-1].toLowerCase();
            for(int j=0;j<s1.length();j++)
            {
                char c=s1.charAt(j);
                if(count(s1,c)>count(s2,c))
                {
                    ok=false;
                   
                }
               
            }
        }
        for(int i=6;i<=11;i++)
        {
            String s1=peharOdgovori[i].toLowerCase();
            String s2=peharOdgovori[i+1].toLowerCase();
            for(int j=0;j<s1.length();j++)
            {
                char c=s1.charAt(j);
                if(count(s1,c)>count(s2,c))
                {
                    ok=false;
                    
                }
               
            }
        }
        return ok;
    }
    
    private int count(String s,char c)
    {
        int j=0;
        for(int i=0;i<s.length();i++)
        {
            if(s.charAt(i)==c){j++;}
        }
        return j;
    }
    
    public String logout()
    {
        HttpSession sesija =(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        sesija.invalidate();
        return "/index";
    }
    
    
    public class RecZaProveru {
        private String kategorija;
        private String rec;

        public String getKategorija() {
            return kategorija;
        }

        public String getRec() {
            return rec;
        }

        public void setKategorija(String kategorija) {
            this.kategorija = kategorija;
        }

        public void setRec(String rec) {
            this.rec = rec;
        }
    }
}
