package server.database;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Created by dronloko on 02.03.14.
 */

@Entity
@Table(name="users")
public class UserDataSet implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="login")
    private String login;

    @Column(name="password")
    private String password;

    public UserDataSet() {
    }

    public UserDataSet(String _login, String _password){
        this.setId(-1);
        this.setLogin(_login);
        this.setPassword(_password);
    }

    public long getId() {
        return id;
    }
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}