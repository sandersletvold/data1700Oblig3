package oslomet.data1700.oblig3;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "bruker")
public class Kunde {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty("nyttBrukernavn")
    private String brukernavn;

    @JsonProperty("nyttBrukernavn")
    private String passord;

    public Kunde(Integer id, String brukernavn, String passord) {
        this.id = id;
        this.brukernavn = brukernavn;
        this.passord = passord;
    }

    public Kunde(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrukernavn() {
        return brukernavn;
    }

    public void setBrukernavn(String brukernavn) {
        this.brukernavn = brukernavn;
    }

    public String getPassord() {
        return passord;
    }

    public void setPassord(String passord) {
        this.passord = passord;
    }
}