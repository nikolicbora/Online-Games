/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import models.PartijaEntity;
import models.VezaEntity;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtil;

/**
 *
 * @author nb150
 */
@ManagedBean
@ViewScoped
public class ControlRanglist implements Serializable {
    
    private List<VezaEntity> ranglist = new ArrayList<>();
    private List<VezaEntity> korisnik_rang = new ArrayList<>();
    private boolean u_prvih10;
    
    public ControlRanglist(){
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        
        ArrayList<VezaEntity> svi = new ArrayList<>();
        
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            
        for (Object rez : hibSession.createCriteria(VezaEntity.class)
                .add(Restrictions.eq("partija", hibSession.get(PartijaEntity.class, new java.sql.Date(Calendar.getInstance().getTimeInMillis()))))
                .add(Restrictions.eq("status", 0))
                .list())
        {
            if (((VezaEntity)rez).getUser().getUsername().equals(session.getAttribute("username").toString())){
                korisnik_rang.add((VezaEntity)rez);
            }
            svi.add((VezaEntity) rez);
        }
        
        Collections.sort(svi, (VezaEntity rez1, VezaEntity rez2) -> {
            int ukupno1 = rez1.getPoeni1() + rez1.getPoeni2() + rez1.getPoeni3() + rez1.getPoeni4() + rez1.getPoeni5();
            int ukupno2 = rez2.getPoeni1() + rez2.getPoeni2() + rez2.getPoeni3() + rez2.getPoeni4() + rez2.getPoeni5();
            
            if (ukupno1 > ukupno2){
                return -1;
            } else if (ukupno1 < ukupno2) {
                return 1;
            } else{
                return 0;
            }
        });
        
        u_prvih10 = false;
        
        if (svi.size() > 10){
            for (int i = 0; i < 10; i++){
                ranglist.add(svi.get(i));
                
                if (svi.get(i).getUser().getUsername().equals(session.getAttribute("username").toString())){
                    u_prvih10 = true;
                }
            }
        } else {
            ranglist = svi;
        }
        
        
        hibSession.close();
    }

    public List<VezaEntity> getKorisnik_rang() {
        return korisnik_rang;
    }

    public void setKorisnik_rang(List<VezaEntity> korisnik_rang) {
        this.korisnik_rang = korisnik_rang;
    }

    public List<VezaEntity> getRanglist() {
        return ranglist;
    }

    public void setRanglist(List<VezaEntity> ranglist) {
        this.ranglist = ranglist;
    }

    public boolean isU_prvih10() {
        return u_prvih10;
    }

    public void setU_prvih10(boolean u_prvih10) {
        this.u_prvih10 = u_prvih10;
    }
}
