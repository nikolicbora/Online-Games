package models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "recnik", schema = "igre", catalog = "")
public class RecnikEntity {
    private int idReci;
    private String rec;

    private KategorijaReciEntity kategorija;
    
    @ManyToOne(targetEntity = KategorijaReciEntity.class, fetch = FetchType.EAGER)
    @Column(name = "id_kategorija_reci")
    public KategorijaReciEntity getKategorija() {
        return kategorija;
    }

    public void setKategorija(KategorijaReciEntity kategorija) {
        this.kategorija = kategorija;
    }
    
    @Id
    @Column(name = "id_reci", nullable = false)
    public int getIdReci() {
        return idReci;
    }

    public void setIdReci(int idReci) {
        this.idReci = idReci;
    }

    @Basic
    @Column(name = "rec", nullable = false, length = 30)
    public String getRec() {
        return rec;
    }

    public void setRec(String rec) {
        this.rec = rec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecnikEntity that = (RecnikEntity) o;
        return idReci == that.idReci &&
                Objects.equals(rec, that.rec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReci, rec);
    }
}
