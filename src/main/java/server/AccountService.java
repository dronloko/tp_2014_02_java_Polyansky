package server;

import server.database.UserDataSet;
import server.database.UserDataSetDAO;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by dronloko on 01.03.14.
 */

public class AccountService {
    private static UserDataSetDAO dao = new UserDataSetDAO();

    public boolean authorize(final String username, final String password) {
        return (isRegistered(username) && dao.readByName(username).getPassword().equals(password));
    }

    public boolean isRegistered(final String username){
        return (dao.readByName(username) != null);
    }

    public boolean register(final String login, final String password){
        if (!isRegistered(login)) {
            UserDataSet userDataSet = new UserDataSet(login, password);
            dao.save(userDataSet);
            return true;
        }
        else
            return false;
    }

    public long getUserId(final String login){
        return isRegistered(login) ? dao.readByName(login).getId() : -1;
    }

    public String getAllUsers(){
        Collection users = dao.readAll();
        Iterator iter = users.iterator();
        String answer = "";
        while(iter.hasNext()){
            UserDataSet userDataSet = (UserDataSet) iter.next();
            answer += "Login : " + userDataSet.getLogin() + "   Password: " + userDataSet.getPassword() + "   ID: " + userDataSet.getId() + "\n";
        }
        System.out.println(answer);
        return answer;
    }
}