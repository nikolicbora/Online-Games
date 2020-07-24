package models;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "kategorija_reci", schema = "igre", catalog = "")
public class KategorijaReciEntity {
    private int idKategorijaReci;
    private String kategorija;

    private Set<RecnikEntity> reci;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = RecnikEntity.class, mappedBy = "id_kategorija_reci")
    public Set<RecnikEntity> getReci() {
        return reci;
    }

    public void setReci(Set<RecnikEntity> reci) {
        this.reci = reci;
    }

    
    @Id
    @Column(name = "id_kategorija_reci", nullable = false)
    public int getIdKategorijaReci() {
        return idKategorijaReci;
    }

    public void setIdKategorijaReci(int idKategorijaReci) {
        this.idKategorijaReci = idKategorijaReci;
    }

    @Basic
    @Column(name = "kategorija", nullable = false, length = 45)
    public String getKategorija() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KategorijaReciEntity that = (KategorijaReciEntity) o;
        return idKategorijaReci == that.idKategorijaReci &&
                Objects.equals(kategorija, that.kategorija);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idKategorijaReci, kategorija);
    }
}
