package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Table(name = "partija", schema = "igre", catalog = "")
public class PartijaEntity {
    private Date dan;

    private AnagramEntity anagram;
    private FiveXFiveEntity fiveXfive;
    private GeografijaEntity geografija;
    private PeharEntity pehar;
    
    private Set<VezaEntity> igrali;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = VezaEntity.class, mappedBy = "dan")
    public Set<VezaEntity> getIgrali() {
        return igrali;
    }

    public void setIgrali(Set<VezaEntity> igrali) {
        this.igrali = igrali;
    }

    @ManyToOne(targetEntity = PeharEntity.class, fetch = FetchType.EAGER)
    @Column(name = "id_pehar")
    public PeharEntity getPehar() {
        return pehar;
    }

    public void setPehar(PeharEntity pehar) {
        this.pehar = pehar;
    }

    @ManyToOne(targetEntity = GeografijaEntity.class, fetch = FetchType.EAGER)
    @Column(name = "id_geografija")
    public GeografijaEntity getGeografija() {
        return geografija;
    }

    public void setGeografija(GeografijaEntity geografija) {
        this.geografija = geografija;
    }
    
    @ManyToOne(targetEntity = FiveXFiveEntity.class, fetch = FetchType.EAGER)
    @Column(name = "id_5puta5")
    public FiveXFiveEntity getFiveXfive() {
        return fiveXfive;
    }

    public void setFiveXfive(FiveXFiveEntity fiveXfive) {
        this.fiveXfive = fiveXfive;
    }
    
    @ManyToOne(targetEntity = AnagramEntity.class, fetch = FetchType.EAGER)
    @Column(name = "id_anagrama")
    public AnagramEntity getAnagram() {
        return anagram;
    }

    public void setAnagram(AnagramEntity anagram) {
        this.anagram = anagram;
    }
    
    @Id
    @Column(name = "dan", nullable = false)
    public Date getDan() {
        return dan;
    }

    public void setDan(Date dan) {
        this.dan = dan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartijaEntity that = (PartijaEntity) o;
        return Objects.equals(dan, that.dan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dan);
    }
}
