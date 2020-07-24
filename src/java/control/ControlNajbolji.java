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
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import models.VezaEntity;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtil;

/**
 *
 * @author nb150
 */
@ManagedBean
@RequestScoped
public class ControlNajbolji implements Serializable {
    public class Score {
        private String username;
        private String slika;
        private int poeni;

        public String getSlika() {
            return slika;
        }

        public void setSlika(String slika) {
            this.slika = slika;
        }

        public int getPoeni() {
            return poeni;
        }

        public String getUsername() {
            return username;
        }

        public void setPoeni(int poeni) {
            this.poeni = poeni;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    private String title;
    private List<Score> lista = new ArrayList<Score>();

    public List<Score> getLista() {
        return lista;
    }

    public void setLista(List<Score> lista) {
        this.lista = lista;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String najbolji(String period){
        lista = new ArrayList<Score>();
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        if (period.equals("20")){
            title = "Najbolji igraci u poslednjih 20 dana";
            
            Calendar c = Calendar.getInstance();
            c.roll(Calendar.DATE, -20);
            
            for (Object oVeza : hibSession.createCriteria(VezaEntity.class).add(Restrictions.eq("status", 0)).add(Restrictions.ge("partija.dan", c.getTime())).list()){
                VezaEntity veza = (VezaEntity)oVeza;
                
                boolean postoji = false;
                
                for (Score s : lista){
                    if (s.username.equals(veza.getUser().getUsername())){
                        s.poeni += (veza.getPoeni1() + veza.getPoeni2() + veza.getPoeni3() + veza.getPoeni4() + veza.getPoeni5());
                        postoji = true;
                    }
                }
                
                if (postoji == false){
                    Score s = new Score();
                    s.setPoeni(veza.getPoeni1() + veza.getPoeni2() + veza.getPoeni3() + veza.getPoeni4() + veza.getPoeni5());
                    s.setUsername(veza.getUser().getUsername());
                    s.setSlika(veza.getUser().getSlikaPath());
                    
                    System.out.println(veza.getUser().getSlikaPath());
                    
                    lista.add(s);
                }
            }
        } else {
            title = "Najbolji igraci u poseldnjih mesec dana";
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DATE, 1);
            
            for (Object oVeza : hibSession.createCriteria(VezaEntity.class).add(Restrictions.eq("status", 0)).add(Restrictions.ge("partija.dan", c.getTime())).list()){
                VezaEntity veza = (VezaEntity)oVeza;
                
                boolean postoji = false;
                
                for (Score s : lista){
                    if (s.username.equals(veza.getUser().getUsername())){
                        s.poeni += (veza.getPoeni1() + veza.getPoeni2() + veza.getPoeni3() + veza.getPoeni4() + veza.getPoeni5());
                        postoji = true;
                    }
                }
                
                if (postoji == false){
                    Score s = new Score();
                    s.setPoeni(veza.getPoeni1() + veza.getPoeni2() + veza.getPoeni3() + veza.getPoeni4() + veza.getPoeni5());
                    s.setUsername(veza.getUser().getUsername());
                    s.setSlika(veza.getUser().getSlikaPath());
                    
                    System.out.println(veza.getUser().getSlikaPath());
                    
                    lista.add(s);
                }
            }
        }
        
        Collections.sort(lista, (Score o1, Score o2) -> {
            if (o1.poeni > o2.poeni){
                return -1;
            } else if (o1.poeni < o2.poeni){
                return 1;
            } else {
                return 0;
            }
        });
        
        hibSession.close();
        return "najbolji";
    }
}
