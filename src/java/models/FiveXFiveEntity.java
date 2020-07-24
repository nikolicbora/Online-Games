package models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "5puta5", schema = "igre", catalog = "")
public class FiveXFiveEntity implements Serializable {
    private int id5Puta5;
    private String rec1;
    private String rec2;
    private String rec3;
    private String rec4;
    private String rec5;

    @Transient
    private char pokusaj_karakter;

    public char getPokusaj_karakter() {
        return pokusaj_karakter;
    }

    public void setPokusaj_karakter(char pokusaj_karakter) {
        this.pokusaj_karakter = pokusaj_karakter;
    }
    
    @Transient
    private String pokusaj_rec;

    public String getPokusaj_rec() {
        return pokusaj_rec;
    }

    public void setPokusaj_rec(String pokusaj_rec) {
        this.pokusaj_rec = pokusaj_rec;
    }
    
    @Transient
    private String[] reci = new String[5];

    public String[] getReci() {
        return reci;
    }

    public void setReci(String[] reci) {
        this.reci = reci;
    }
    
    @Transient
    private String[] pokusaj = new String[]{
        "_____",
        "_____",
        "_____",
        "_____",
        "_____"
    };

    public String[] getPokusaj() {
        return pokusaj;
    }

    public void setPokusaj(String[] pokusaj) {
        this.pokusaj = pokusaj;
    }
    
    @Id
    @Column(name = "id_5puta5", nullable = false)
    public int getId5Puta5() {
        return id5Puta5;
    }

    public void setId5Puta5(int id5Puta5) {
        this.id5Puta5 = id5Puta5;
    }

    @Basic
    @Column(name = "rec1", nullable = false, length = 5)
    public String getRec1() {
        return rec1;
    }

    public void setRec1(String rec1) {
        this.rec1 = rec1;
    }

    @Basic
    @Column(name = "rec2", nullable = false, length = 5)
    public String getRec2() {
        return rec2;
    }

    public void setRec2(String rec2) {
        this.rec2 = rec2;
    }

    @Basic
    @Column(name = "rec3", nullable = false, length = 5)
    public String getRec3() {
        return rec3;
    }

    public void setRec3(String rec3) {
        this.rec3 = rec3;
    }

    @Basic
    @Column(name = "rec4", nullable = false, length = 5)
    public String getRec4() {
        return rec4;
    }

    public void setRec4(String rec4) {
        this.rec4 = rec4;
    }

    @Basic
    @Column(name = "rec5", nullable = false, length = 5)
    public String getRec5() {
        return rec5;
    }

    public void setRec5(String rec5) {
        this.rec5 = rec5;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FiveXFiveEntity that = (FiveXFiveEntity) o;
        return id5Puta5 == that.id5Puta5 &&
                Objects.equals(rec1, that.rec1) &&
                Objects.equals(rec2, that.rec2) &&
                Objects.equals(rec3, that.rec3) &&
                Objects.equals(rec4, that.rec4) &&
                Objects.equals(rec5, that.rec5);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id5Puta5, rec1, rec2, rec3, rec4, rec5);
    }
}
