/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.script.ScriptException;
import javax.servlet.http.HttpSession;
import models.AnagramEntity;
import models.FiveXFiveEntity;
import models.GeografijaEntity;
import models.KategorijaReciEntity;
import models.MojBrojEntity;
import models.PartijaEntity;
import models.PeharEntity;
import models.RecnikEntity;
import models.VezaEntity;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtil;
import util.RezultatModel;

/**
 *
 * @author nb150
 */
@ManagedBean
@SessionScoped
public class ControlUcesnik implements Serializable
{
    private int igra = 1;
    private boolean sledeca = false;
    
    private AnagramEntity anagram = new AnagramEntity();
    private MojBrojEntity moj_broj = new MojBrojEntity();
    private FiveXFiveEntity fiveXfive = new FiveXFiveEntity();
    private GeografijaEntity geografija = new GeografijaEntity();
    private PeharEntity pehar = new PeharEntity();
    
    private List<Character> dostupna_slova = new ArrayList<>();
    private List<String> moguci_smer = new ArrayList<String>();
    private List<Integer> moguci_redni = new ArrayList<Integer>();
    private String odabran_smer;
    private int odabran_redni;

    private String trenutno_pitanje;
    private String trenutni_odgovor;
    private int redni_broj_trenutnog_pitanja;
    
    private boolean postoji_igra_dana = false;
    private boolean odbrojavam = true;
    private int count=60;
    
    private RezultatModel rezultat;
    
    public ControlUcesnik(){
        igra_dana();
    }
    
    public String igraj()
    {
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        PartijaEntity partija = (PartijaEntity) hibSession.get(PartijaEntity.class, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        
        igra = 1;
        
        boolean igrao = false;
        
        for(char c = 'a'; c <= 'z'; c++)
        {
            dostupna_slova.add(c);
        }
        
        moguci_smer.add("horizontalno");
        moguci_smer.add("vertikalno");
        
        for (int i = 1; i < 6; i++){
            moguci_redni.add(i);
        }
        
        if (partija != null){
            anagram = partija.getAnagram();
            fiveXfive = partija.getFiveXfive();
            geografija = partija.getGeografija();
            pehar = partija.getPehar();
            
            fiveXfive.setReci(new String[]{
                fiveXfive.getRec1(),
                fiveXfive.getRec2(),
                fiveXfive.getRec3(),
                fiveXfive.getRec4(),
                fiveXfive.getRec5()
            });
            
            pehar.setPitanja(new String[]{
                pehar.getP1(),
                pehar.getP2(),
                pehar.getP3(),
                pehar.getP4(),
                pehar.getP5(),
                pehar.getP6(),
                pehar.getP7(),
                pehar.getP8(),
                pehar.getP9(),
                pehar.getP10(),
                pehar.getP11(),
                pehar.getP12(),
                pehar.getP13()
            });
            
            pehar.setOdgovori(new String[]{
                pehar.getO1(),
                pehar.getO2(),
                pehar.getO3(),
                pehar.getO4(),
                pehar.getO5(),
                pehar.getO6(),
                pehar.getO7(),
                pehar.getO8(),
                pehar.getO9(),
                pehar.getO10(),
                pehar.getO11(),
                pehar.getO12(),
                pehar.getO13()
            });
            
            trenutno_pitanje = pehar.getP1();
            redni_broj_trenutnog_pitanja = 0;
            
            hibSession.close();
            
            for (VezaEntity veza : partija.getIgrali()){
                if (veza.getUser().getUsername().equals(session.getAttribute("username").toString())){
                    igrao = true;
                }
            }
            
            if (igrao){
                String poruka_reg_a="vec ste danas igrali igru dana";
                FacesContext.getCurrentInstance().addMessage("index:error", new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka_reg_a, poruka_reg_a));
                return null;
            } else {
                return "igra/anagram";
            }
        } else {
            String poruka_reg_a="ne postoji igra dana za danasnji dan";
            FacesContext.getCurrentInstance().addMessage("index:error", new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka_reg_a, poruka_reg_a));
            return null;
        }
    }
    
    public String obrada_timer() throws IOException
    {
        if(count==0){
            if (igra == 5){
                daj_odgovor_pehar("");
                return null;
            } else {
                sledeca = true;
                igra += 1;
                return null;
            }
        }
        else{
            if (odbrojavam){
                count--;
            }
            return null;
        }
    }
    
    public String daj_odgovor_anagram()
    {
        count = 60;
        sledeca = false;
        odbrojavam = false;
        igra = 2;
        return "moj_broj";
    }
    
    public String daj_odgovor_moj_broj()
    {
        count = 60;
        sledeca = false;
        odbrojavam = true;
        igra = 3;
        return "5_puta_5";
    }

    public String five_x_five_zavrsi_igru(){
        count = 120;
        sledeca = false;
        odbrojavam = true;
        igra = 4;
        return "geografija";
    }
    
    public String geografija_zavrsi_igru(){
        count = 30;
        sledeca = false;
        odbrojavam = true;
        igra = 5;
        return "pehar";
    }
    
    public String pehar_zavrsi_igru(){
        count = 0;
        sledeca = false;
        odbrojavam = false;
        igra = 6;
        
        rezultat = new RezultatModel(anagram, moj_broj, fiveXfive, geografija, pehar);
        try {
            rezultat.izracunajRezultat();
        } catch (ScriptException ex) {
            Logger.getLogger(ControlUcesnik.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "rezultati";
    }
    
    public void moj_broj_stop(){
        moj_broj.generisi();
        if (moj_broj.getCilj() != 0){
            odbrojavam = true;
        }
    }
    
    public void five_x_five_odabir_slova() throws IOException
    {
        int j = 0;
        for (String rec : fiveXfive.getReci()){
            for (int i = 0; i < 5; i++){
                if (fiveXfive.getPokusaj_karakter() == rec.charAt(i)){
                    char[] staro = fiveXfive.getPokusaj()[j].toCharArray();
                    staro[i] = rec.charAt(i);
                    fiveXfive.getPokusaj()[j] = new String(staro);
                }
            }
            j++;
        }
        Character c = (Character)fiveXfive.getPokusaj_karakter();
        dostupna_slova.remove(c);
        
        boolean reseno = true;
        
        j=0;
        
        for (String prec : fiveXfive.getPokusaj()){
            if (!prec.equals(fiveXfive.getReci()[j]))
            {
                reseno = false;
            }
            j++;
        }
        
        if (reseno){
            count = 60;
            sledeca = true;
//            FacesContext.getCurrentInstance().getExternalContext().redirect("igra/geografija.xhtml");
        }
    }
    
    public String daj_odgovor_five_x_five() throws IOException{
        if(fiveXfive.getPokusaj_rec().length() != 5){
            String poruka_reg_a="uneta rec mora imati tacno 5 slova";
            FacesContext.getCurrentInstance().addMessage("form:error", new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka_reg_a, poruka_reg_a));
            return null;
        }
        boolean isrec = true;
        
        for(int i = 0; i < fiveXfive.getPokusaj_rec().length(); i++)
        {
            if(!Character.isLetter(fiveXfive.getPokusaj_rec().charAt(i))){isrec=false;break;}
        }
        
        if(!isrec)
        {
            String poruka_reg_a="rec se mora sastojati samo od slova";
            FacesContext.getCurrentInstance().addMessage("form:error", new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka_reg_a, poruka_reg_a));
            return null;
        }else{
        
            if (odabran_smer.equals("horizontalno")){
                if (fiveXfive.getPokusaj_rec().equals(fiveXfive.getReci()[odabran_redni-1])){
                   fiveXfive.getPokusaj()[odabran_redni-1] = fiveXfive.getReci()[odabran_redni-1];
                   String poruka_reg_a = "tacan odgovor";
                   FacesContext.getCurrentInstance().addMessage("form:error", new FacesMessage(FacesMessage.SEVERITY_INFO, poruka_reg_a, poruka_reg_a));
                   
                    boolean reseno = true;
                    int j = 0;
                    
                    for (String prec : fiveXfive.getPokusaj()){
                        if (!prec.equals(fiveXfive.getReci()[j]))
                        {
                            reseno = false;
                        }
                        j++;
                    }

                    if (reseno){
                        count = 60;
                        sledeca = true;
//                        FacesContext.getCurrentInstance().getExternalContext().redirect("igra/geografija.xhtml");
                        return "reseno";
                    } else {
                        return null;
                    }
                } else {
                   String poruka_reg_a = "netacan odgovor";
                   FacesContext.getCurrentInstance().addMessage("form:error", new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka_reg_a, poruka_reg_a));
                   return null;
                }
            } else {
                int i = odabran_redni - 1;
                
                String temp_rec = "";
                
                for (String rec : fiveXfive.getReci()){
                    temp_rec += rec.charAt(i);
                }
                
                 if (fiveXfive.getPokusaj_rec().equals(temp_rec)){
                     int j = 0;
                    for (String rec : fiveXfive.getPokusaj()){
                        char[] chars = rec.toCharArray();
                        chars[i] = temp_rec.charAt(j);
                        fiveXfive.getPokusaj()[j] = new String(chars);
                        j++;
                    }
                   
                   String poruka_reg_a = "tacan odgovor";
                   FacesContext.getCurrentInstance().addMessage("form:error", new FacesMessage(FacesMessage.SEVERITY_INFO, poruka_reg_a, poruka_reg_a));
                    
                    boolean reseno = true;

                    j = 0;
                    
                    for (String prec : fiveXfive.getPokusaj()){
                        if (!prec.equals(fiveXfive.getReci()[j]))
                        {
                            reseno = false;
                        }
                        j++;
                    }

                    if (reseno){
                        count = 60;
                        sledeca = true;
//                        FacesContext.getCurrentInstance().getExternalContext().redirect("igra/geografija.xhtml");
                        return "reseno";
                    } else {
                        return null;
                    }
                } else {
                   String poruka_reg_a = "netacan odgovor";
                   FacesContext.getCurrentInstance().addMessage("form:error", new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka_reg_a, poruka_reg_a));
                   return null;
                }
            }
            
        }
    }
    
    public String geografija_provera(String tip){
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        
        boolean postoji = false;
                    
        if (tip.equals("drzava")){
            if (geografija.getRecDrzava().charAt(0) == geografija.getSlovo().charAt(0)){
                KategorijaReciEntity kategorija = 
                        (KategorijaReciEntity) hibSession.createCriteria(KategorijaReciEntity.class).add(Restrictions.eq("kategorija", "drzava")).list().get(0);
                
                for (RecnikEntity rec : kategorija.getReci()){
                    if (geografija.getRecDrzava().equals(rec.getRec())){
                        postoji = true;
                        break;
                    }
                }
            }
        } 
        else if (tip.equals("grad")){
            if (geografija.getRecGrad().charAt(0) == geografija.getSlovo().charAt(0)){
                KategorijaReciEntity kategorija = 
                        (KategorijaReciEntity) hibSession.createCriteria(KategorijaReciEntity.class).add(Restrictions.eq("kategorija", "grad")).list().get(0);
                
                for (RecnikEntity rec : kategorija.getReci()){
                    if (geografija.getRecGrad().equals(rec.getRec())){
                        postoji = true;
                        break;
                    }
                }
            }
        }
        else if (tip.equals("jezero"))
        {
            if (geografija.getRecJezero().charAt(0) == geografija.getSlovo().charAt(0)){
                KategorijaReciEntity kategorija = 
                        (KategorijaReciEntity) hibSession.createCriteria(KategorijaReciEntity.class).add(Restrictions.eq("kategorija", "jezero")).list().get(0);
                
                for (RecnikEntity rec : kategorija.getReci()){
                    if (geografija.getRecJezero().equals(rec.getRec())){
                        postoji = true;
                        break;
                    }
                }
            }
        }
        else if (tip.equals("planina"))
        {
            if (geografija.getRecPlanina().charAt(0) == geografija.getSlovo().charAt(0)){
                KategorijaReciEntity kategorija = 
                        (KategorijaReciEntity) hibSession.createCriteria(KategorijaReciEntity.class).add(Restrictions.eq("kategorija", "planina")).list().get(0);
                
                for (RecnikEntity rec : kategorija.getReci()){
                    if (geografija.getRecPlanina().equals(rec.getRec())){
                        postoji = true;
                        break;
                    }
                }
            }
        }
        else if (tip.equals("reka"))
        {
            if (geografija.getRecReka().charAt(0) == geografija.getSlovo().charAt(0)){
                KategorijaReciEntity kategorija = 
                        (KategorijaReciEntity) hibSession.createCriteria(KategorijaReciEntity.class).add(Restrictions.eq("kategorija", "reka")).list().get(0);
                
                for (RecnikEntity rec : kategorija.getReci()){
                    if (geografija.getRecReka().equals(rec.getRec())){
                        postoji = true;
                        break;
                    }
                }
            }
        }
        else if (tip.equals("zivotinja"))
        {
            if (geografija.getRecZivotinja().charAt(0) == geografija.getSlovo().charAt(0)){
                KategorijaReciEntity kategorija = 
                        (KategorijaReciEntity) hibSession.createCriteria(KategorijaReciEntity.class).add(Restrictions.eq("kategorija", "zivotinja")).list().get(0);
                
                for (RecnikEntity rec : kategorija.getReci()){
                    if (geografija.getRecZivotinja().equals(rec.getRec())){
                        postoji = true;
                        break;
                    }
                }
            }
        }
        else if (tip.equals("biljka"))
        {
            if (geografija.getRecBiljka().charAt(0) == geografija.getSlovo().charAt(0)){
                KategorijaReciEntity kategorija = 
                        (KategorijaReciEntity) hibSession.createCriteria(KategorijaReciEntity.class).add(Restrictions.eq("kategorija", "biljka")).list().get(0);
                
                for (RecnikEntity rec : kategorija.getReci()){
                    if (geografija.getRecBiljka().equals(rec.getRec())){
                        postoji = true;
                        break;
                    }
                }
            }
        }
        else if (tip.equals("grupa"))
        {
            if (geografija.getRecGrupa().charAt(0) == geografija.getSlovo().charAt(0)){
                KategorijaReciEntity kategorija = 
                        (KategorijaReciEntity) hibSession.createCriteria(KategorijaReciEntity.class).add(Restrictions.eq("kategorija", "muzicka grupa")).list().get(0);
                
                for (RecnikEntity rec : kategorija.getReci()){
                    if (geografija.getRecGrupa().equals(rec.getRec())){
                        postoji = true;
                        break;
                    }
                }
            }
        }
        
        if (postoji){
            String poruka_reg_a = "Bravo, uneta rec postoji u bazi";
            FacesContext.getCurrentInstance().addMessage(tip + ":error", new FacesMessage(FacesMessage.SEVERITY_INFO, poruka_reg_a, poruka_reg_a));
        } else {
            String poruka_reg_a = "Uneta rec nepostoji u bazi i morace je proveriti supervizor";
            FacesContext.getCurrentInstance().addMessage(tip + ":error", new FacesMessage(FacesMessage.SEVERITY_WARN, poruka_reg_a, poruka_reg_a));
        }
        
        hibSession.close();
        return null;
    }

    public String daj_odgovor_pehar(String tip){
        count = 30;
        sledeca = false;
        
        if (tip.equals("")){
            trenutni_odgovor = "VREME";
        }
        
        pehar.getPokusaj()[redni_broj_trenutnog_pitanja] = trenutni_odgovor;
        
        if (trenutni_odgovor.equalsIgnoreCase(pehar.getOdgovori()[redni_broj_trenutnog_pitanja])){
            pehar.getPrikaz()[redni_broj_trenutnog_pitanja] = trenutni_odgovor;
        } else {
            pehar.getPrikaz()[redni_broj_trenutnog_pitanja] = pehar.getOdgovori()[redni_broj_trenutnog_pitanja] + 
                    "<br/><p style='color: red; font-size: 0.6em; margin-right: 20px; margin-bottom: -20px; margin-top: -5px; letter-spacing: 0em; line-height: 0em'>(" + trenutni_odgovor + ")</p>";
        }
        
        redni_broj_trenutnog_pitanja++;
        
        if (tip.equals("")){
            trenutni_odgovor = "";
        }
        
        if (redni_broj_trenutnog_pitanja == 13){
            return null;
        } else {
            trenutno_pitanje = pehar.getPitanja()[redni_broj_trenutnog_pitanja];
            return null;
        }
    }
    
    public String logout()
    {
        HttpSession sesija =(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        sesija.invalidate();
        return "index";
    }
    
    public GeografijaEntity getGeografija() {
        return geografija;
    }

    public void setGeografija(GeografijaEntity geografija) {
        this.geografija = geografija;
    }
    
    public FiveXFiveEntity getFiveXfive() {
        return fiveXfive;
    }

    public void setFiveXfive(FiveXFiveEntity fiveXfive) {
        this.fiveXfive = fiveXfive;
    }
    
    public AnagramEntity getAnagram() {
        return anagram;
    }

    public void setAnagram(AnagramEntity a) {
        this.anagram = a;
    }

    public MojBrojEntity getMoj_broj() {
        return moj_broj;
    }

    public void setMoj_broj(MojBrojEntity moj_broj) {
        this.moj_broj = moj_broj;
    }
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Character> getDostupna_slova() {
        return dostupna_slova;
    }

    public void setDostupna_slova(List<Character> dostupna_slova) {
        this.dostupna_slova = dostupna_slova;
    }

    public List<Integer> getMoguci_redni() {
        return moguci_redni;
    }

    public void setMoguci_redni(List<Integer> moguci_redni) {
        this.moguci_redni = moguci_redni;
    }

    public List<String> getMoguci_smer() {
        return moguci_smer;
    }

    public void setMoguci_smer(List<String> moguci_smer) {
        this.moguci_smer = moguci_smer;
    }

    public int getOdabran_redni() {
        return odabran_redni;
    }

    public void setOdabran_redni(int odabran_redni) {
        this.odabran_redni = odabran_redni;
    }

    public String getOdabran_smer() {
        return odabran_smer;
    }

    public void setOdabran_smer(String odabran_smer) {
        this.odabran_smer = odabran_smer;
    }

    public boolean isSledeca() {
        return sledeca;
    }

    public void setSledeca(boolean sledeca) {
        this.sledeca = sledeca;
    }

    public PeharEntity getPehar() {
        return pehar;
    }

    public void setPehar(PeharEntity pehar) {
        this.pehar = pehar;
    }

    public int getRedni_broj_trenutnog_pitanja() {
        return redni_broj_trenutnog_pitanja;
    }

    public void setRedni_broj_trenutnog_pitanja(int redni_broj_trenutnog_pitanja) {
        this.redni_broj_trenutnog_pitanja = redni_broj_trenutnog_pitanja;
    }

    public String getTrenutno_pitanje() {
        return trenutno_pitanje;
    }

    public void setTrenutno_pitanje(String trenutno_pitanje) {
        this.trenutno_pitanje = trenutno_pitanje;
    }

    public String getTrenutni_odgovor() {
        return trenutni_odgovor;
    }

    public void setTrenutni_odgovor(String trenutni_odgovor) {
        this.trenutni_odgovor = trenutni_odgovor;
    }

    public boolean isPostoji_igra_dana() {
        return postoji_igra_dana;
    }

    public void setPostoji_igra_dana(boolean postoji_igra_dana) {
        this.postoji_igra_dana = postoji_igra_dana;
    }
    
    public String igra_dana(){
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        Date key = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        PartijaEntity partija = (PartijaEntity) hibSession.get(PartijaEntity.class, key);
        
        if (partija == null){
            postoji_igra_dana = false;
        } else {
            postoji_igra_dana = true;
        }
        
        hibSession.close();
        
        return null;
    }

    public RezultatModel getRezultat() {
        return rezultat;
    }

    public void setRezultat(RezultatModel rezultat) {
        this.rezultat = rezultat;
    }
    
    public String povratak_na_index(){
        if (igra == 6)
        {
            igra = 1;
            sledeca = false;

            anagram = new AnagramEntity();
            moj_broj = new MojBrojEntity();
            fiveXfive = new FiveXFiveEntity();
            geografija = new GeografijaEntity();
            pehar = new PeharEntity();

            dostupna_slova = new ArrayList<>();
            moguci_smer = new ArrayList<String>();
            moguci_redni = new ArrayList<Integer>();
            odabran_smer = null;
            odabran_redni = 0;

            trenutno_pitanje = null;
            trenutni_odgovor = null;
            redni_broj_trenutnog_pitanja = 0;

            odbrojavam = true;
            count=60;

            RezultatModel rezultat = null;
        }
        
        return "/index";
    }
    
    public String povratak_na_ranglist(){
        return "/ranglista";
    }
}
