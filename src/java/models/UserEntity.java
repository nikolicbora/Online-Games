package models;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.*;
import java.util.Objects;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

@Entity
@Table(name = "user", schema = "igre", catalog = "")
public class UserEntity implements Serializable {
    private String username;
    private String password;
    private String ime;
    private String prezime;
    private String mail;
    private String zanimanje;
    private String pol;
    private String jmbg;
    private String pitanje;
    private String odgovor;
    private String tip;
    private int odobren;
    
    public String getSlikaPath(){
        String putanja = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "images/users/";
        String relativna = "images/users/";
        File folder = new File(putanja);
        
        ArrayList<String> moguca_imena = new ArrayList<String>() {{
            add(username + ".jpg");
            add(username + ".jpeg");
            add(username + ".png");
            add(username + ".gif");
        }};
        
        for (File f : folder.listFiles()){
            if (moguca_imena.contains(f.getName())){
                relativna += f.getName();
                return relativna;
            }
        }
        
        return "null";
    }
    
    @Transient
    private UploadedFile slika;

    public UploadedFile getSlika() {
        return slika;
    }

    public void setSlika(UploadedFile slika) {
        this.slika = slika;
    }
    
    @Id
    @Column(name = "username", nullable = false, length = 50)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 50)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "ime", nullable = false, length = 50)
    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    @Basic
    @Column(name = "prezime", nullable = false, length = 50)
    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    @Basic
    @Column(name = "mail", nullable = false, length = 50)
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Basic
    @Column(name = "zanimanje", nullable = false, length = 50)
    public String getZanimanje() {
        return zanimanje;
    }

    public void setZanimanje(String zanimanje) {
        this.zanimanje = zanimanje;
    }

    @Basic
    @Column(name = "pol", nullable = false, length = 50)
    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    @Basic
    @Column(name = "jmbg", nullable = false, length = 50)
    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    @Basic
    @Column(name = "pitanje", nullable = false, length = 50)
    public String getPitanje() {
        return pitanje;
    }

    public void setPitanje(String pitanje) {
        this.pitanje = pitanje;
    }

    @Basic
    @Column(name = "odgovor", nullable = false, length = 50)
    public String getOdgovor() {
        return odgovor;
    }

    public void setOdgovor(String odgovor) {
        this.odgovor = odgovor;
    }

    @Basic
    @Column(name = "tip", nullable = false, length = 50)
    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    @Basic
    @Column(name = "odobren", nullable = false)
    public int getOdobren() {
        return odobren;
    }

    public void setOdobren(int odobren) {
        this.odobren = odobren;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return odobren == that.odobren &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(ime, that.ime) &&
                Objects.equals(prezime, that.prezime) &&
                Objects.equals(mail, that.mail) &&
                Objects.equals(zanimanje, that.zanimanje) &&
                Objects.equals(pol, that.pol) &&
                Objects.equals(jmbg, that.jmbg) &&
                Objects.equals(pitanje, that.pitanje) &&
                Objects.equals(odgovor, that.odgovor) &&
                Objects.equals(tip, that.tip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, ime, prezime, mail, zanimanje, pol, jmbg, pitanje, odgovor, tip, odobren);
    }
}
