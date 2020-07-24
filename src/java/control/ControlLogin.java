/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import models.UserEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.FileUploadEvent;
import util.HibernateUtil;

/**
 *
 * @author nb150
 */
@ManagedBean
@SessionScoped
public class ControlLogin implements Serializable 
{
    //login
    private String username;
    private String password;
    private String poruka;
    private String tip;
    
    public String koriscnicki(){
        return "korisnicki";
    }
    
    public String login()
    {   
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        boolean ulogovan = false;
        for (Object userObject : 
                hibSession.createCriteria(UserEntity.class).add(Restrictions.eq("username", username)).add(Restrictions.eq("tip", tip)).list()){
            UserEntity user = (UserEntity)userObject;
            poruka = "";

            if (user.getPassword().equals(password)){
                if (user.getOdobren() == 1){
                    ulogovan = true;
                    HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                    sesija.setAttribute("username", username);
                } else {
                    poruka = "zao nam je vas zahtev jos uvek nije odobren (ne brinite imamo vas u evidenciji)";
                    FacesContext.getCurrentInstance().addMessage("index:error", new FacesMessage(FacesMessage.SEVERITY_INFO, poruka, poruka));
                    hibSession.close();
                    return null;
                }
            } else {
                poruka = "uneti podaci nisu dobri, molimo pokusajte ponovo";
                    FacesContext.getCurrentInstance().addMessage("index:error", new FacesMessage(FacesMessage.SEVERITY_INFO, poruka, poruka));
                    hibSession.close();
                    return null;
            }
        }

        if (!ulogovan){
            poruka = "uneti podaci nisu dobri, molimo pokusajte ponovo";
            FacesContext.getCurrentInstance().addMessage("index:error", new FacesMessage(FacesMessage.SEVERITY_INFO, poruka, poruka));
            hibSession.close();
            return null;
        }

        hibSession.close();
        if(tip.equals("admin")) return "admin/admin";
        else if(tip.equals("supervizor")) return "supervizor/supervizor";
        else {return "ucesnik";}
    }

    //registracija
    private String tip_registracije;
    private UserEntity u = new UserEntity();
    
    public String registracija() throws IOException
    {   
        u.setTip(tip_registracije);
        
        String sifra = u.getPassword();
        boolean is_jmbg=true;
        for(int i=0;i<u.getJmbg().length();i++)
        {
            if(!Character.isDigit(u.getJmbg().charAt(i))){is_jmbg=false;break;}
        }
        if(u.getJmbg().length()!=13){is_jmbg=false;}
        if(!is_jmbg)
        {
            String poruka_reg_a = "registracija nije uspela. jmbg nije pravilnog formata";
            FacesContext.getCurrentInstance().addMessage("index:error", new FacesMessage(FacesMessage.SEVERITY_INFO, poruka_reg_a, poruka_reg_a));
            return null;
        }
        
        boolean dobra = true;
        
        if (!Character.isAlphabetic(sifra.charAt(0))){
            dobra = false;
        }
        
        if (sifra.length() < 8 || sifra.length() > 12){
            dobra = false;
        }
        
        int brojVelikihSlova = 0;
        int brojMalihSlova = 0;
        int brojNumerika = 0;
        int brojSpecijalnih = 0;
        int najviseUzastopnih = 1;
        int brojUzastopnih = 1;
        char proslo = '0';
        
        for (int i = 0; i < sifra.length(); i++){
            char slovo = sifra.charAt(i);
            
            if (slovo == proslo){
                brojUzastopnih++;
            } else {
                if (brojUzastopnih > najviseUzastopnih){
                    najviseUzastopnih = brojUzastopnih;
                }
                brojUzastopnih = 1;
            }
            
            proslo = slovo;
            
            if (Character.isAlphabetic(slovo)){
                if (Character.isLowerCase(slovo)){
                    brojMalihSlova++;
                } else {
                    brojVelikihSlova++;
                }
            } else if (Character.isDigit(slovo)){
                brojNumerika++;
            } else {
                brojSpecijalnih++;
            }
        }
        
        if (najviseUzastopnih > 2){
            dobra = false;
        }
        
        if (brojVelikihSlova < 1){
            dobra = false;
        }
        
        if (brojMalihSlova < 3){
            dobra = false;
        }
        
        if (brojNumerika < 1){
            dobra = false;
        }
        
        if (brojSpecijalnih < 1){
            dobra = false;
        }
        
        if (dobra){
            Session hibSession = HibernateUtil.getSessionFactory().openSession();
            
            UserEntity postoji_user = (UserEntity) hibSession.get(UserEntity.class, u.getUsername());
            
            if (postoji_user == null){
                
                System.out.println(u.getSlika().getFileName());
                
                Transaction t = hibSession.beginTransaction();
                hibSession.save(u);
                t.commit();

                hibSession.close();

                String ekstenzija = u.getSlika().getFileName().substring(u.getSlika().getFileName().lastIndexOf(".") + 1);
               
                String cuvaj_sliku = "images/users/" + u.getUsername() + "." + ekstenzija;
                
                String cuvaj_sliku_abs = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + cuvaj_sliku;
                
                File fajl = new File(cuvaj_sliku_abs);
                fajl.createNewFile();
                byte[] buffer = new byte[u.getSlika().getInputstream().available()];
                u.getSlika().getInputstream().read(buffer);
                FileOutputStream stream = new FileOutputStream(fajl);
                stream.write(buffer);
                stream.flush();
                stream.close();

                String poruka_reg_a = "registracija uspesna";
                FacesContext.getCurrentInstance().addMessage("index:error", new FacesMessage(FacesMessage.SEVERITY_INFO, poruka_reg_a, poruka_reg_a));
                return null;
            } else {
                String poruka_reg_a = "registracija nije uspela. username vec postoji...";
                FacesContext.getCurrentInstance().addMessage("index:error", new FacesMessage(FacesMessage.SEVERITY_INFO, poruka_reg_a, poruka_reg_a));
                return null;
            }
        } else {
            String poruka_reg_a = "registracija nije uspela. sifra mora zadovoljavati sledece uslove: ...";
            FacesContext.getCurrentInstance().addMessage("index:error", new FacesMessage(FacesMessage.SEVERITY_INFO, poruka_reg_a, poruka_reg_a));
            return null;
        }
    }
    
    public void handleFileUpload(FileUploadEvent event){
//        FacesMessage msg = new FacesMessage("Uspesno ste uploadovali sliku!");
//        FacesContext.getCurrentInstance().addMessage("index:msgfori11", msg);
        u.setSlika(event.getFile());
    }
    
    //zamena sifre
    private String zamena_username, zamena_password, zamena_novi_password, potvrda;
    private String greska_password;
    public String zameni()
    {
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        String poruka_reg_a = "";
        
        UserEntity za_izmenu = null;
        
        for (Object userObject : hibSession.createCriteria(UserEntity.class).add(Restrictions.eq("username", zamena_username)).add(Restrictions.eq("odobren",1)).list()){
            UserEntity user = (UserEntity) userObject;
            
            if (user.getPassword().equals(zamena_password)){
                za_izmenu = user;
            } else {
                poruka_reg_a = "podaci koje ste uneli nisu validni";
                FacesContext.getCurrentInstance().addMessage("index:error", new FacesMessage(FacesMessage.SEVERITY_INFO, poruka_reg_a, poruka_reg_a));
                hibSession.close();
                return null;
            }
        }
        if(za_izmenu==null)
        {
               poruka_reg_a = "vi ste neregistrovan korisnik pa nemozete da koristite ovu opciju";
                FacesContext.getCurrentInstance().addMessage("index:error", new FacesMessage(FacesMessage.SEVERITY_INFO, poruka_reg_a, poruka_reg_a));
                hibSession.close();
                return null; 
        }
        String sifra = zamena_novi_password;
        
        boolean dobra = true;
        
        if (!Character.isAlphabetic(sifra.charAt(0))){
            dobra = false;
        }
        
        if (sifra.length() < 8 || sifra.length() > 12){
            dobra = false;
        }
        
        int brojVelikihSlova = 0;
        int brojMalihSlova = 0;
        int brojNumerika = 0;
        int brojSpecijalnih = 0;
        int najviseUzastopnih = 1;
        int brojUzastopnih = 1;
        char proslo = '0';
        
        for (int i = 0; i < sifra.length(); i++){
            char slovo = sifra.charAt(i);
            
            if (slovo == proslo){
                brojUzastopnih++;
            } else {
                if (brojUzastopnih > najviseUzastopnih){
                    najviseUzastopnih = brojUzastopnih;
                }
                brojUzastopnih = 1;
            }
            
            proslo = slovo;
            
            if (Character.isAlphabetic(slovo)){
                if (Character.isLowerCase(slovo)){
                    brojMalihSlova++;
                } else {
                    brojVelikihSlova++;
                }
            } else if (Character.isDigit(slovo)){
                brojNumerika++;
            } else {
                brojSpecijalnih++;
            }
        }
        
        if (najviseUzastopnih > 2){
            dobra = false;
        }
        
        if (brojVelikihSlova < 1){
            dobra = false;
        }
        
        if (brojMalihSlova < 3){
            dobra = false;
        }
        
        if (brojNumerika < 1){
            dobra = false;
        }
        
        if (brojSpecijalnih < 1){
            dobra = false;
        }
        
        if (dobra){
            if (sifra.equals(potvrda)){
                Transaction t = hibSession.beginTransaction();
                za_izmenu.setPassword(sifra);
                hibSession.save(za_izmenu);
                t.commit();
                
                hibSession.close();
                return "korisnicki";
            } else {
                poruka_reg_a = "unete sifre se ne podudaraju";
                FacesContext.getCurrentInstance().addMessage("index:error", new FacesMessage(FacesMessage.SEVERITY_INFO, poruka_reg_a, poruka_reg_a));
                hibSession.close();
                return null;
            }
        } else {
            poruka_reg_a = "nova sifra nije validnog formata";
            FacesContext.getCurrentInstance().addMessage("index:error", new FacesMessage(FacesMessage.SEVERITY_INFO, poruka_reg_a, poruka_reg_a));
            hibSession.close();
            return null;
        }
    }
    
    //zaboravljena lozinka
    private String zaboravljen_username,zaboravljen_jmbg,zaboravljeno_pitanje,zaboravljen_odgovor,njegov_odgovor;
    private boolean zaboravi_b1;
    private String zaboravi_password,zaboravi_potvrda_password;
    private String zaboravljen_tip;
    public String zaboravi()
    {
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        String poruka_reg_a = "";
        
        UserEntity za_izmenu = null;
        
        for (Object userObject : hibSession.createCriteria(UserEntity.class).add(Restrictions.eq("username", zaboravljen_username)).list()){
            UserEntity user = (UserEntity) userObject;
            
            if (user.getJmbg().equals(zaboravljen_jmbg)){
                za_izmenu = user;
            } else {
                poruka_reg_a = "podaci koje ste uneli nisu validni";
                FacesContext.getCurrentInstance().addMessage("index:error", new FacesMessage(FacesMessage.SEVERITY_INFO, poruka_reg_a, poruka_reg_a));
                hibSession.close();
                return null;
            }
        }
        
        if (za_izmenu == null){
            poruka_reg_a = "podaci koje ste uneli nisu validni";
            FacesContext.getCurrentInstance().addMessage("index:error", new FacesMessage(FacesMessage.SEVERITY_INFO, poruka_reg_a, poruka_reg_a));
            hibSession.close();
            return null;
        }
        
        zaboravljeno_pitanje = za_izmenu.getPitanje();
        zaboravljen_odgovor = za_izmenu.getOdgovor();
        zaboravljen_tip = za_izmenu.getTip();
        zaboravi_b1 = true;
        
        return null;
    }
    
    public String zaborav()
    {
        if(njegov_odgovor.equals(zaboravljen_odgovor)) return "zaboravljena_lozinka_potvrda";
        else 
        {
             
            String poruka_reg_a = "pogresan odgovor na tajanstveno pitanje";
            FacesContext.getCurrentInstance().addMessage("index1:error1", new FacesMessage(FacesMessage.SEVERITY_INFO, poruka_reg_a, poruka_reg_a));
            return null;
        }
    }
    
    public String zaboravi_povrda()
    {
        String poruka_reg_a;
        if(isPassword(zaboravi_password)==false)
        {
            poruka_reg_a="sifra nije korektnog formata";
            FacesContext.getCurrentInstance().addMessage("index:error", new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka_reg_a, poruka_reg_a));
            return null;
        }
        if(!zaboravi_password.equals(zaboravi_potvrda_password))
        {
                poruka_reg_a="uneli ste pogresnu potvrdu sifre";
                FacesContext.getCurrentInstance().addMessage("index:error", new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka_reg_a, poruka_reg_a));
                return null;
        }
        
        ///
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
       
        
        UserEntity za_izmenu = null;
        
        for (Object userObject : hibSession.createCriteria(UserEntity.class).add(Restrictions.eq("username", zaboravljen_username)).add(Restrictions.eq("odobren",1)).list())
        {
            za_izmenu= (UserEntity) userObject;
            
          
        }
        if(za_izmenu==null)
        {
            poruka_reg_a="vi niste registrovan korisnik pa nemate pravo na ovu opciju";
            FacesContext.getCurrentInstance().addMessage("index:error", new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka_reg_a, poruka_reg_a));
            hibSession.close();
            return null;
        }
        Transaction t = hibSession.beginTransaction();
        za_izmenu.setPassword(zaboravi_password);
        hibSession.save(za_izmenu);
        t.commit();

        hibSession.close();
        ///
        HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        sesija.setAttribute("username", username);
        if (zaboravljen_tip.equals("admin"))
            return "admin";
        else if (zaboravljen_tip.equals("supervizor"))
            return "supervizor";
        else {return "ucesnik";}
        
    }
    
    public String povratak_pocetna(){return "korisnicki";}
    public String registruj()
    {
        tip_registracije=u.getTip();
        u= new UserEntity();
        return "registracija";
    }
    public String na_promenu_lozinke()
    {
        zamena_username=zamena_password=zamena_novi_password=potvrda="";
        greska_password="";
        return "promena_lozinke";
    }
    public String na_zaboravljenu_lozinku()
    {
        zaboravljen_username=zaboravljen_jmbg=zaboravljeno_pitanje=zaboravljen_odgovor=njegov_odgovor="";
        zaboravi_password=zaboravi_potvrda_password="";
        zaboravljen_tip="";
        zaboravi_b1=false;
        return "zaboravljena_lozinka";
    }
    public String logout()
    {
        HttpSession sesija =(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        sesija.invalidate();
        return "index";
    }
   












//getteri i setteri
 
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getTip_registracije() {
        return tip_registracije;
    }

    public void setTip_registracije(String tip_registracije) {
        this.tip_registracije = tip_registracije;
    }

    public UserEntity getU() {
        return u;
    }

    public void setU(UserEntity u) {
        this.u = u;
    }

    public String getZamena_username() {
        return zamena_username;
    }

    public void setZamena_username(String zamena_username) {
        this.zamena_username = zamena_username;
    }

    public String getZamena_password() {
        return zamena_password;
    }

    public void setZamena_password(String zamena_password) {
        this.zamena_password = zamena_password;
    }

    public String getZamena_novi_password() {
        return zamena_novi_password;
    }

    public void setZamena_novi_password(String zamena_novi_password) {
        this.zamena_novi_password = zamena_novi_password;
    }

    public String getPotvrda() {
        return potvrda;
    }

    public void setPotvrda(String potvrda) {
        this.potvrda = potvrda;
    }

    public String getZaboravljen_username() {
        return zaboravljen_username;
    }

    public void setZaboravljen_username(String zaboravljen_username) {
        this.zaboravljen_username = zaboravljen_username;
    }

    public String getZaboravljen_jmbg() {
        return zaboravljen_jmbg;
    }

    public void setZaboravljen_jmbg(String zaboravljen_jmbg) {
        this.zaboravljen_jmbg = zaboravljen_jmbg;
    }

    public String getZaboravljeno_pitanje() {
        return zaboravljeno_pitanje;
    }

    public void setZaboravljeno_pitanje(String zaboravljeno_pitanje) {
        this.zaboravljeno_pitanje = zaboravljeno_pitanje;
    }

    public String getZaboravljen_odgovor() {
        return zaboravljen_odgovor;
    }

    public void setZaboravljen_odgovor(String zaboravljen_odgovor) {
        this.zaboravljen_odgovor = zaboravljen_odgovor;
    }

    public boolean isZaboravi_b1() {
        return zaboravi_b1;
    }

    public void setZaboravi_b1(boolean zaboravi_b1) {
        this.zaboravi_b1 = zaboravi_b1;
    }

    public String getNjegov_odgovor() {
        return njegov_odgovor;
    }

    public void setNjegov_odgovor(String njegov_odgovor) {
        this.njegov_odgovor = njegov_odgovor;
    }
    
    private boolean isPassword(String sifra)
    {
       
        
        boolean dobra = true;
        
        if (!Character.isAlphabetic(sifra.charAt(0))){
            dobra = false;
        }
        
        if (sifra.length() < 8 || sifra.length() > 12){
            dobra = false;
        }
        
        int brojVelikihSlova = 0;
        int brojMalihSlova = 0;
        int brojNumerika = 0;
        int brojSpecijalnih = 0;
        int najviseUzastopnih = 1;
        int brojUzastopnih = 1;
        char proslo = '0';
        
        for (int i = 0; i < sifra.length(); i++){
            char slovo = sifra.charAt(i);
            
            if (slovo == proslo){
                brojUzastopnih++;
            } else {
                if (brojUzastopnih > najviseUzastopnih){
                    najviseUzastopnih = brojUzastopnih;
                }
                brojUzastopnih = 1;
            }
            
            proslo = slovo;
            
            if (Character.isAlphabetic(slovo)){
                if (Character.isLowerCase(slovo)){
                    brojMalihSlova++;
                } else {
                    brojVelikihSlova++;
                }
            } else if (Character.isDigit(slovo)){
                brojNumerika++;
            } else {
                brojSpecijalnih++;
            }
        }
        
        if (najviseUzastopnih > 2){
            dobra = false;
        }
        
        if (brojVelikihSlova < 1){
            dobra = false;
        }
        
        if (brojMalihSlova < 3){
            dobra = false;
        }
        
        if (brojNumerika < 1){
            dobra = false;
        }
        
        if (brojSpecijalnih < 1){
            dobra = false;
        }
        
        return dobra;
    }

    public String getZaboravi_password() {
        return zaboravi_password;
    }

    public void setZaboravi_password(String zaboravi_password) {
        this.zaboravi_password = zaboravi_password;
    }

    public String getZaboravi_potvrda_password() {
        return zaboravi_potvrda_password;
    }

    public void setZaboravi_potvrda_password(String zaboravi_potvrda_password) {
        this.zaboravi_potvrda_password = zaboravi_potvrda_password;
    }

    public String getZaboravljen_tip() {
        return zaboravljen_tip;
    }

    public void setZaboravljen_tip(String zaboravljen_tip) {
        this.zaboravljen_tip = zaboravljen_tip;
    }
    
    
    
}
