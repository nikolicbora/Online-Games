package models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "anagram", schema = "igre", catalog = "")
public class AnagramEntity {
    private int idAnagrama;
    private String pitanje1;
    private String odgovor1;

    @Id
    @Column(name = "id_anagrama", nullable = false)
    public int getIdAnagrama() {
        return idAnagrama;
    }

    public void setIdAnagrama(int idAnagrama) {
        this.idAnagrama = idAnagrama;
    }

    @Basic
    @Column(name = "pitanje1", nullable = false, length = 50)
    public String getPitanje1() {
        return pitanje1;
    }

    public void setPitanje1(String pitanje1) {
        this.pitanje1 = pitanje1;
    }

    @Basic
    @Column(name = "odgovor1", nullable = false, length = 50)
    public String getOdgovor1() {
        return odgovor1;
    }

    public void setOdgovor1(String odgovor1) {
        this.odgovor1 = odgovor1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnagramEntity that = (AnagramEntity) o;
        return idAnagrama == that.idAnagrama &&
                Objects.equals(pitanje1, that.pitanje1) &&
                Objects.equals(odgovor1, that.odgovor1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAnagrama, pitanje1, odgovor1);
    }
    
    private String pokusaj;

    @Transient
    public String getPokusaj() {
        return pokusaj;
    }

    public void setPokusaj(String pokusaj) {
        this.pokusaj = pokusaj;
    }
}
