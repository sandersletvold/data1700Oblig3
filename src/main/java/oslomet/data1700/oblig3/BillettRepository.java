package oslomet.data1700.oblig3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BillettRepository {
    @Autowired
    private JdbcTemplate db;

    private Logger logger = LoggerFactory.getLogger(BillettRepository.class);

    class BillettRowMapper implements RowMapper< Billett > {
        @Override
        public Billett mapRow(ResultSet rs, int rowNum) throws SQLException {
            Billett billett = new Billett();
            billett.setBillettNr(rs.getLong("billettNr"));
            billett.setFilm(rs.getString("film"));
            billett.setAntall(rs.getString("antall"));
            billett.setFornavn(rs.getString("fornavn"));
            billett.setEtternavn(rs.getString("etternavn"));
            billett.setTelefonnr(rs.getString("telefonnr"));
            billett.setEpost(rs.getString("epost"));
            return billett;
        }
    }

    public boolean sjekkNavnOgPassord (Kunde kunde) {
        String sql = "SELECT * FROM bruker WHERE id=?";
        try{
            Kunde dbKunde = db.queryForObject(sql,
                    BeanPropertyRowMapper.newInstance(Kunde.class),new Object[]{kunde.getId()});
            return sjekkPassord(dbKunde.getPassord(),kunde.getPassord());
        }
        catch(Exception e) {
            logger.error("Feil i sjekkNavnOgPassord : " + e);
            return false;
        }
    }

    private String krypterPassord(String passord){
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder(14);
        String hashedPassord = bCrypt.encode(passord);
        return hashedPassord;
    }

    private boolean sjekkPassord(String passord, String hashPassord){
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        if(bCrypt.matches(passord,hashPassord)){
            return true;
        }
        return false;
    }

    public boolean signUp(Kunde kunde) {
        String hash = krypterPassord(kunde.getPassord());
        String sql = "INSERT INTO bruker (brukernavn,passord) VALUES(?,?)";
        try{
            db.update(sql,kunde.getBrukernavn(),kunde.getPassord(),hash);
            return true;
        }
        catch(Exception e){
            logger.error("Feil i lagreKunde : "+e);
            return false;
        }
    }


    public void tilServer(Billett billett) {
        String sql = "INSERT INTO billett (film, antall, fornavn, etternavn, telefonnr, epost) VALUES(?,?,?,?,?,?);";
        db.update(sql, billett.getFilm(), billett.getAntall(), billett.getFornavn(), billett.getEtternavn(), billett.getTelefonnr(), billett.getEpost());
    }

    public List<Billett> tilKlient(){
        List<Billett> alleBilletter = db.query("SELECT * FROM billett ORDER BY etternavn", new BillettRowMapper());
        return alleBilletter;
    }

    public void slettLagring() {
        String sql = "DELETE FROM billett;";
        db.update(sql);
    }

    public int slettEnBillett(Long billettNr) {
        String sql = "DELETE FROM billett WHERE billettNr = ?";
        return db.update(sql, new Object[]{
                billettNr
        });
    }

    public Billett hentEnBillett(Long billettNr) {
        return db.queryForObject("SELECT * FROM billett WHERE billettNr = ?", new BillettRowMapper(), billettNr);
    }

    public int endreEnBillett(Billett billett) {
        String sql = "UPDATE billett SET film = ?, antall =?, fornavn =?, etternavn =?, telefonnr =?, epost =? WHERE billettNr= ?";
        return db.update(sql, billett.getFilm(), billett.getAntall(), billett.getFornavn(), billett.getEtternavn(), billett.getTelefonnr(), billett.getEpost(), billett.getBillettNr());
    }

    public boolean loggInn(Kunde kunde) {
        String sql = "SELECT COUNT(*) FROM billett WHERE brukernavn = ? AND passord = ?";
        try {
            int funnetBruker = db.queryForObject(sql, Integer.class, kunde);
            if (funnetBruker > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
