/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import models.AnagramEntity;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author nb150
 */
@FacesConverter("anagramConverter")
public class AnagramConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Session hibSession = HibernateUtil.getSessionFactory().openSession();
        
        Object o = hibSession.createCriteria(AnagramEntity.class).add(Restrictions.eq("pitanje1", value.split("-")[0].trim())).list().get(0);
        
        hibSession.close();
        
        return o;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        AnagramEntity anagram = (AnagramEntity) value;
        
        return anagram.getPitanje1() + " - " + anagram.getOdgovor1();
    }

 }