/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import models.UserEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import models.AnagramEntity;
import models.FiveXFiveEntity;
import models.GeografijaEntity;
import models.PartijaEntity;
import models.PeharEntity;
import org.hibernate.Query;
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
public class ControlAdmin implements Serializable
{
    //onload
    private List<UserEntity> list_user=new ArrayList<UserEntity>();
    private int i=0;
    public String LoadAdmin()
    {
        //System.out.println("ovde za i="+(++i));
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        list_user=new ArrayList<UserEntity>();
        for (Object user : hibSession.createCriteria(UserEntity.class).add(Restrictions.eq("odobren", 0)).list()){
            list_user.add((UserEntity)user);
        }
        
        anagrami = hibSession.createCriteria(AnagramEntity.class).list();
        fiveXfive = hibSession.createCriteria(FiveXFiveEntity.class).list();
        pehari = hibSession.createCriteria(PeharEntity.class).list();
        
        
        String upit="from PartijaEntity p1 where p1.dan not in(select v2.partija.dan from VezaEntity v2) and p1.dan>='"+new java.sql.Date(new java.util.Date().getTime())+"'";
        System.out.println(upit);
        Query query = hibSession.createQuery(upit);
        partije_nepocete=query.list();
        
        
        
        hibSession.close();
        
        return "admin";
    }
    
    //odobri zabrani
    public String odobri(UserEntity u)
    {
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        
        UserEntity u_kopija = (UserEntity) hibSession.get(UserEntity.class, u.getUsername());
        
        u_kopija.setOdobren(1);
        
        Transaction t = hibSession.beginTransaction();
        hibSession.save(u_kopija);
        t.commit();
        
        hibSession.close();
        
        list_user.remove(u);

        u.setOdobren(1);

        return null;
    }
    
    public String zabrani(UserEntity u)
    {
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        
        UserEntity u_kopija = (UserEntity) hibSession.get(UserEntity.class, u.getUsername());
        
        u_kopija.setOdobren(2);
        
        Transaction t = hibSession.beginTransaction();
        hibSession.save(u_kopija);
        t.commit();
        
        hibSession.close();
        
        list_user.remove(u);

        u.setOdobren(2);

        return null;
    }
   
    public List<UserEntity> getList_user() {
        return list_user;
    }

    public void setList_user(List<UserEntity> list_user) {
        this.list_user = list_user;
    }
    private boolean mod1=false;
    
    private int dodavanje_korak = 1;
    private Date dodavanje_datum = new Date();
    private Date danas = Calendar.getInstance().getTime();
    private AnagramEntity dodavanje_anagram;
    private List<AnagramEntity> anagrami = new ArrayList<AnagramEntity>();
    private FiveXFiveEntity dodavanje_5x5;
    private List<FiveXFiveEntity> fiveXfive = new ArrayList<FiveXFiveEntity>();
    private PeharEntity dodavanje_pehar;
    private List<PeharEntity> pehari = new ArrayList<PeharEntity>();
    private boolean prikaz=false;
    
    
    private List<PartijaEntity> partije_nepocete=new ArrayList<PartijaEntity>();
    private PartijaEntity nepoceta_partija;
    private boolean partija_prikaz=false;
    /*public String dodavanjeDalje(){
        
        if (dodavanje_korak == 1){
            Session hibSession = HibernateUtil.getSessionFactory().openSession();
            
            System.out.println("Datum: " + dodavanje_datum);
            
            if (hibSession.createCriteria(PartijaEntity.class).add(Restrictions.eq("dan", new java.sql.Date(dodavanje_datum.getTime()))).list().size() > 0){
                String poruka = "partija postoji za odabran dan!";
                FacesContext.getCurrentInstance().addMessage("dodavanje:error", new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, poruka));
                hibSession.close();
                return null;
            }
            
            hibSession.close();
        }
        
        if (dodavanje_korak == 4){
            PartijaEntity partija = new PartijaEntity();
            partija.setAnagram(dodavanje_anagram);
            partija.setDan(new java.sql.Date(dodavanje_datum.getTime()));
            partija.setFiveXfive(dodavanje_5x5);
            partija.setPehar(dodavanje_pehar);
            
            String slova = "abcdefghijklmnoprstuvfz";
            
            Random rnd = new Random();
            
            GeografijaEntity geos = new GeografijaEntity();
            geos.setSlovo("" + slova.charAt(rnd.nextInt(slova.length())));
            
            Session hibSession = HibernateUtil.getSessionFactory().openSession();
            
            Transaction t = hibSession.beginTransaction();
            hibSession.save(geos);
            hibSession.save(partija);
            t.commit();
            
            hibSession.close();
        }
        
        dodavanje_korak++;
        return null;
    }*/

    
    public String partija_datum()
    {
          Session hibSession = HibernateUtil.getSessionFactory().openSession();
            
          System.out.println("Datum: " + dodavanje_datum);
            
            if (hibSession.createCriteria(PartijaEntity.class).add(Restrictions.eq("dan", new java.sql.Date(dodavanje_datum.getTime()))).list().size() > 0){
                String poruka = "partija postoji za odabran dan!";
                FacesContext.getCurrentInstance().addMessage("form:error", new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, poruka));
                hibSession.close();
                return null;
            }
            
            hibSession.close();
            return "dodaj_anagram";
    }
    public String dodaj_anagram(AnagramEntity a)
    {
        dodavanje_anagram=a;
        partija_prikaz=false;
        return "dodaj_5x5";
    }
    public String dodaj_pet_puta_pet(FiveXFiveEntity f)
    {
        dodavanje_5x5=f;
        
        return "dodaj_pehar";
    }
    public String prikaz_pehar(PeharEntity p)
    {
        if(prikaz==true)
        {
            if(p==dodavanje_pehar)
            {
                dodavanje_pehar = null;
                prikaz = false;
                return null;
        
            }
            else
            {
                dodavanje_pehar = p;
                prikaz = true;
                return null;
            }
        }
        else
        {
            dodavanje_pehar = p;
            prikaz = true;
            return null;
        }
    }
    public String dodaj_pehar()
    {
        if(mod1==false)
        {
            
            PartijaEntity partija = new PartijaEntity();
            partija.setAnagram(dodavanje_anagram);
            partija.setDan(new java.sql.Date(dodavanje_datum.getTime()));
            partija.setFiveXfive(dodavanje_5x5);
            partija.setPehar(dodavanje_pehar);
            
            String slova = "abcdefghijklmnoprstuvfz";
            
            Random rnd = new Random();
            
            GeografijaEntity geos = new GeografijaEntity();
            geos.setSlovo("" + slova.charAt(rnd.nextInt(slova.length())));
            
            Session hibSession = HibernateUtil.getSessionFactory().openSession();
            
            Transaction t1 = hibSession.beginTransaction();
            hibSession.save(geos);
            
            //partija.setGeografija(geos);
           
           
            //hibSession.save(partija);
            t1.commit();
            String upit="select max(g2.idGeografija) from GeografijaEntity g2)";
            Query maxQuery = hibSession.createQuery(upit);
            int pom=(Integer)maxQuery.list().get(0);
            
            upit="from GeografijaEntity g2 where g2.idGeografija="+pom+")";
            maxQuery = hibSession.createQuery(upit);
            
            geos =(GeografijaEntity)maxQuery.list().get(0);
            Transaction t2 = hibSession.beginTransaction();
            partija.setGeografija(geos);
            hibSession.save(partija);
            t2.commit();
            hibSession.close();
            
            prikaz = false;
            dodavanje_pehar = null;
            nepoceta_partija=null;//
            
            return "dodaj_poruka";
        }
        else
        {
            mod1=false;
            nepoceta_partija.setAnagram(dodavanje_anagram);
            nepoceta_partija.setFiveXfive(dodavanje_5x5);
            nepoceta_partija.setPehar(dodavanje_pehar);
            Session hibSession = HibernateUtil.getSessionFactory().openSession();
            Transaction t1 = hibSession.beginTransaction();

            hibSession.saveOrUpdate(nepoceta_partija);

            t1.commit();
            hibSession.close();
            
            prikaz = false;//
            dodavanje_pehar = null;//
            nepoceta_partija=null;//
            
            return "dodaj_poruka_update";
        }
    }
    
    
    
    
    public String prikaz_partija(PartijaEntity p)
    {
        if(partija_prikaz==true)
        {
            if(nepoceta_partija==p)
            {
                partija_prikaz=false;
                nepoceta_partija=null;
                return null;
            }
            else
            {
                partija_prikaz=true;
                nepoceta_partija=p;
                return null;
            }
        }
        else
        {
            partija_prikaz = true;
            nepoceta_partija = p;
            return null;
        }
        
    }
    
    public String stanje_update()
    {
        mod1=true;
        
        return "dodaj_anagram";
    }
    
    public String logout()
    {
        HttpSession sesija =(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        sesija.invalidate();
        return "/index";
    }

    public boolean isMod1() {
        return mod1;
    }

    public void setMod1(boolean mod1) {
        this.mod1 = mod1;
    }
    
    public Date getDodavanje_datum() {
        return dodavanje_datum;
    }

    public void setDodavanje_datum(Date dodavanje_datum) {
        this.dodavanje_datum = dodavanje_datum;
    }

    public int getDodavanje_korak() {
        return dodavanje_korak;
    }

    public void setDodavanje_korak(int dodavanje_korak) {
        this.dodavanje_korak = dodavanje_korak;
    }

    public Date getDanas() {
        return danas;
    }

    public void setDanas(Date danas) {
        this.danas = danas;
    }

    public List<AnagramEntity> getAnagrami() {
        return anagrami;
    }

    public void setAnagrami(List<AnagramEntity> anagrami) {
        this.anagrami = anagrami;
    }

    public AnagramEntity getDodavanje_anagram() {
        return dodavanje_anagram;
    }

    public void setDodavanje_anagram(AnagramEntity dodavanje_anagram) {
        this.dodavanje_anagram = dodavanje_anagram;
    }

    public FiveXFiveEntity getDodavanje_5x5() {
        return dodavanje_5x5;
    }

    public void setDodavanje_5x5(FiveXFiveEntity dodavanje_5x5) {
        this.dodavanje_5x5 = dodavanje_5x5;
    }

    public List<FiveXFiveEntity> getFiveXfive() {
        return fiveXfive;
    }

    public void setFiveXfive(List<FiveXFiveEntity> fiveXfive) {
        this.fiveXfive = fiveXfive;
    }

    public PeharEntity getDodavanje_pehar() {
        return dodavanje_pehar;
    }

    public void setDodavanje_pehar(PeharEntity dodavanje_pehar) {
        this.dodavanje_pehar = dodavanje_pehar;
    }

    public List<PeharEntity> getPehari() {
        return pehari;
    }

    public void setPehari(List<PeharEntity> pehari) {
        this.pehari = pehari;
    }

    public boolean isPrikaz() {
        return prikaz;
    }

    public void setPrikaz(boolean prikaz) {
        this.prikaz = prikaz;
    }

    public List<PartijaEntity> getPartije_nepocete() {
        return partije_nepocete;
    }

    public void setPartije_nepocete(List<PartijaEntity> partije_nepocete) {
        this.partije_nepocete = partije_nepocete;
    }

    public PartijaEntity getNepoceta_partija() {
        return nepoceta_partija;
    }

    public void setNepoceta_partija(PartijaEntity nepoceta_partija) {
        this.nepoceta_partija = nepoceta_partija;
    }

    public boolean isPartija_prikaz() {
        return partija_prikaz;
    }

    public void setPartija_prikaz(boolean partija_prikaz) {
        this.partija_prikaz = partija_prikaz;
    }
    
}
