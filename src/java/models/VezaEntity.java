package models;

import java.sql.Date;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "veza", schema = "igre", catalog = "")
public class VezaEntity {
    private int poeni1;
    private int poeni2;
    private int poeni3;
    private int poeni4;
    private int poeni5;
    private String provera;
    private int status;
    private int idVeza;
    private PartijaEntity partija;
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = PartijaEntity.class)
    @Column(name = "dan")
    public PartijaEntity getPartija() {
        return partija;
    }

    public void setPartija(PartijaEntity partija) {
        this.partija = partija;
    }
    
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = UserEntity.class)
    @Column(name = "username")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
    
    @Basic
    @Column(name = "poeni1", nullable = false)
    public int getPoeni1() {
        return poeni1;
    }

    public void setPoeni1(int poeni1) {
        this.poeni1 = poeni1;
    }
    
    @Basic
    @Column(name = "poeni2", nullable = false)
    public int getPoeni2() {
        return poeni2;
    }

    public void setPoeni2(int poeni2) {
        this.poeni2 = poeni2;
    }

    @Basic
    @Column(name = "poeni3", nullable = false)
    public int getPoeni3() {
        return poeni3;
    }

    public void setPoeni3(int poeni3) {
        this.poeni3 = poeni3;
    }

    @Basic
    @Column(name = "poeni4", nullable = false)
    public int getPoeni4() {
        return poeni4;
    }

    public void setPoeni4(int poeni4) {
        this.poeni4 = poeni4;
    }

    @Basic
    @Column(name = "poeni5", nullable = false)
    public int getPoeni5() {
        return poeni5;
    }

    public void setPoeni5(int poeni5) {
        this.poeni5 = poeni5;
    }

    @Basic
    @Column(name = "provera", nullable = false)
    public String getProvera() {
        return provera;
    }

    public void setProvera(String provera) {
        this.provera = provera;
    }
   
    @Basic
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Id
    @Column(name = "id_veza", nullable = false)
    public int getIdVeza() {
        return idVeza;
    }

    public void setIdVeza(int idVeza) {
        this.idVeza = idVeza;
    }
}
