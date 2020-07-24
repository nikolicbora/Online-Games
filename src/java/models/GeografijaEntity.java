package models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "geografija", schema = "igre", catalog = "")
public class GeografijaEntity {
    private int idGeografija;
    private String slovo;
    private String recDrzava;
    private String recGrad;
    private String recJezero;
    private String recPlanina;
    private String recReka;
    private String recZivotinja;
    private String recBiljka;
    private String recGrupa;

    @Id
    @Column(name = "id_geografija", nullable = false)
    public int getIdGeografija() {
        return idGeografija;
    }

    public void setIdGeografija(int idGeografija) {
        this.idGeografija = idGeografija;
    }

    @Basic
    @Column(name = "slovo", nullable = false, length = 1)
    public String getSlovo() {
        return slovo;
    }

    public void setSlovo(String slovo) {
        this.slovo = slovo;
    }

    @Basic
    @Column(name = "rec_drzava", nullable = true, length = 45)
    public String getRecDrzava() {
        return recDrzava;
    }

    public void setRecDrzava(String recDrzava) {
        this.recDrzava = recDrzava;
    }

    @Basic
    @Column(name = "rec_grad", nullable = true, length = 45)
    public String getRecGrad() {
        return recGrad;
    }

    public void setRecGrad(String recGrad) {
        this.recGrad = recGrad;
    }

    @Basic
    @Column(name = "rec_jezero", nullable = true, length = 45)
    public String getRecJezero() {
        return recJezero;
    }

    public void setRecJezero(String recJezero) {
        this.recJezero = recJezero;
    }

    @Basic
    @Column(name = "rec_planina", nullable = true, length = 45)
    public String getRecPlanina() {
        return recPlanina;
    }

    public void setRecPlanina(String recPlanina) {
        this.recPlanina = recPlanina;
    }

    @Basic
    @Column(name = "rec_reka", nullable = true, length = 45)
    public String getRecReka() {
        return recReka;
    }

    public void setRecReka(String recReka) {
        this.recReka = recReka;
    }

    @Basic
    @Column(name = "rec_zivotinja", nullable = true, length = 45)
    public String getRecZivotinja() {
        return recZivotinja;
    }

    public void setRecZivotinja(String recZivotinja) {
        this.recZivotinja = recZivotinja;
    }

    @Basic
    @Column(name = "rec_biljka", nullable = true, length = 45)
    public String getRecBiljka() {
        return recBiljka;
    }

    public void setRecBiljka(String recBiljka) {
        this.recBiljka = recBiljka;
    }

    @Basic
    @Column(name = "rec_grupa", nullable = true, length = 45)
    public String getRecGrupa() {
        return recGrupa;
    }

    public void setRecGrupa(String recGrupa) {
        this.recGrupa = recGrupa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeografijaEntity that = (GeografijaEntity) o;
        return idGeografija == that.idGeografija &&
                Objects.equals(slovo, that.slovo) &&
                Objects.equals(recDrzava, that.recDrzava) &&
                Objects.equals(recGrad, that.recGrad) &&
                Objects.equals(recJezero, that.recJezero) &&
                Objects.equals(recPlanina, that.recPlanina) &&
                Objects.equals(recReka, that.recReka) &&
                Objects.equals(recZivotinja, that.recZivotinja) &&
                Objects.equals(recBiljka, that.recBiljka) &&
                Objects.equals(recGrupa, that.recGrupa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGeografija, slovo, recDrzava, recGrad, recJezero, recPlanina, recReka, recZivotinja, recBiljka, recGrupa);
    }
}
