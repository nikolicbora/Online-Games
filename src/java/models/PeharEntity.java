package models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "pehar", schema = "igre", catalog = "")
public class PeharEntity implements Serializable {
    private int idPehar;
    private String p1;
    private String o1;
    private String p2;
    private String o2;
    private String p3;
    private String o3;
    private String p4;
    private String o4;
    private String p5;
    private String o5;
    private String p6;
    private String o6;
    private String p7;
    private String o7;
    private String p8;
    private String o8;
    private String p9;
    private String o9;
    private String p10;
    private String o10;
    private String p11;
    private String o11;
    private String p12;
    private String o12;
    private String p13;
    private String o13;

    @Transient
    private String[] pitanja;
    
    @Transient
    private String[] odgovori;
    
    @Transient
    private String[] pokusaj = new String[13];
    
    @Transient
    private String[] prikaz = new String[13];
    
    @Id
    @Column(name = "id_pehar", nullable = false)
    public int getIdPehar() {
        return idPehar;
    }

    public void setIdPehar(int idPehar) {
        this.idPehar = idPehar;
    }

    @Basic
    @Column(name = "p1", nullable = true, length = 45)
    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    @Basic
    @Column(name = "o1", nullable = true, length = 9)
    public String getO1() {
        return o1;
    }

    public void setO1(String o1) {
        this.o1 = o1;
    }

    @Basic
    @Column(name = "p2", nullable = true, length = 45)
    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

    @Basic
    @Column(name = "o2", nullable = true, length = 8)
    public String getO2() {
        return o2;
    }

    public void setO2(String o2) {
        this.o2 = o2;
    }

    @Basic
    @Column(name = "p3", nullable = true, length = 45)
    public String getP3() {
        return p3;
    }

    public void setP3(String p3) {
        this.p3 = p3;
    }

    @Basic
    @Column(name = "o3", nullable = true, length = 7)
    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    @Basic
    @Column(name = "p4", nullable = true, length = 45)
    public String getP4() {
        return p4;
    }

    public void setP4(String p4) {
        this.p4 = p4;
    }

    @Basic
    @Column(name = "o4", nullable = true, length = 6)
    public String getO4() {
        return o4;
    }

    public void setO4(String o4) {
        this.o4 = o4;
    }

    @Basic
    @Column(name = "p5", nullable = true, length = 45)
    public String getP5() {
        return p5;
    }

    public void setP5(String p5) {
        this.p5 = p5;
    }

    @Basic
    @Column(name = "o5", nullable = true, length = 5)
    public String getO5() {
        return o5;
    }

    public void setO5(String o5) {
        this.o5 = o5;
    }

    @Basic
    @Column(name = "p6", nullable = true, length = 45)
    public String getP6() {
        return p6;
    }

    public void setP6(String p6) {
        this.p6 = p6;
    }

    @Basic
    @Column(name = "o6", nullable = true, length = 4)
    public String getO6() {
        return o6;
    }

    public void setO6(String o6) {
        this.o6 = o6;
    }

    @Basic
    @Column(name = "p7", nullable = true, length = 45)
    public String getP7() {
        return p7;
    }

    public void setP7(String p7) {
        this.p7 = p7;
    }

    @Basic
    @Column(name = "o7", nullable = true, length = 3)
    public String getO7() {
        return o7;
    }

    public void setO7(String o7) {
        this.o7 = o7;
    }

    @Basic
    @Column(name = "p8", nullable = true, length = 45)
    public String getP8() {
        return p8;
    }

    public void setP8(String p8) {
        this.p8 = p8;
    }

    @Basic
    @Column(name = "o8", nullable = true, length = 4)
    public String getO8() {
        return o8;
    }

    public void setO8(String o8) {
        this.o8 = o8;
    }

    @Basic
    @Column(name = "p9", nullable = true, length = 45)
    public String getP9() {
        return p9;
    }

    public void setP9(String p9) {
        this.p9 = p9;
    }

    @Basic
    @Column(name = "o9", nullable = true, length = 5)
    public String getO9() {
        return o9;
    }

    public void setO9(String o9) {
        this.o9 = o9;
    }

    @Basic
    @Column(name = "p10", nullable = true, length = 45)
    public String getP10() {
        return p10;
    }

    public void setP10(String p10) {
        this.p10 = p10;
    }

    @Basic
    @Column(name = "o10", nullable = true, length = 6)
    public String getO10() {
        return o10;
    }

    public void setO10(String o10) {
        this.o10 = o10;
    }

    @Basic
    @Column(name = "p11", nullable = true, length = 45)
    public String getP11() {
        return p11;
    }

    public void setP11(String p11) {
        this.p11 = p11;
    }

    @Basic
    @Column(name = "o11", nullable = true, length = 7)
    public String getO11() {
        return o11;
    }

    public void setO11(String o11) {
        this.o11 = o11;
    }

    @Basic
    @Column(name = "p12", nullable = true, length = 45)
    public String getP12() {
        return p12;
    }

    public void setP12(String p12) {
        this.p12 = p12;
    }

    @Basic
    @Column(name = "o12", nullable = true, length = 8)
    public String getO12() {
        return o12;
    }

    public void setO12(String o12) {
        this.o12 = o12;
    }

    @Basic
    @Column(name = "p13", nullable = true, length = 45)
    public String getP13() {
        return p13;
    }

    public void setP13(String p13) {
        this.p13 = p13;
    }

    @Basic
    @Column(name = "o13", nullable = true, length = 9)
    public String getO13() {
        return o13;
    }

    public void setO13(String o13) {
        this.o13 = o13;
    }

    public String[] getOdgovori() {
        return odgovori;
    }

    public void setOdgovori(String[] odgovori) {
        this.odgovori = odgovori;
    }

    public String[] getPitanja() {
        return pitanja;
    }

    public void setPitanja(String[] pitanja) {
        this.pitanja = pitanja;
    }

    public String[] getPokusaj() {
        return pokusaj;
    }

    public void setPokusaj(String[] pokusaj) {
        this.pokusaj = pokusaj;
    }

    public String[] getPrikaz() {
        return prikaz;
    }

    public void setPrikaz(String[] prikaz) {
        this.prikaz = prikaz;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeharEntity that = (PeharEntity) o;
        return idPehar == that.idPehar &&
                Objects.equals(p1, that.p1) &&
                Objects.equals(o1, that.o1) &&
                Objects.equals(p2, that.p2) &&
                Objects.equals(o2, that.o2) &&
                Objects.equals(p3, that.p3) &&
                Objects.equals(o3, that.o3) &&
                Objects.equals(p4, that.p4) &&
                Objects.equals(o4, that.o4) &&
                Objects.equals(p5, that.p5) &&
                Objects.equals(o5, that.o5) &&
                Objects.equals(p6, that.p6) &&
                Objects.equals(o6, that.o6) &&
                Objects.equals(p7, that.p7) &&
                Objects.equals(o7, that.o7) &&
                Objects.equals(p8, that.p8) &&
                Objects.equals(o8, that.o8) &&
                Objects.equals(p9, that.p9) &&
                Objects.equals(o9, that.o9) &&
                Objects.equals(p10, that.p10) &&
                Objects.equals(o10, that.o10) &&
                Objects.equals(p11, that.p11) &&
                Objects.equals(o11, that.o11) &&
                Objects.equals(p12, that.p12) &&
                Objects.equals(o12, that.o12) &&
                Objects.equals(p13, that.p13) &&
                Objects.equals(o13, that.o13);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPehar, p1, o1, p2, o2, p3, o3, p4, o4, p5, o5, p6, o6, p7, o7, p8, o8, p9, o9, p10, o10, p11, o11, p12, o12, p13, o13);
    }
}
