import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class jsontest {
    public static void main(String[] args) {
        Account[] account = new Account[2];
        for (int i = 0; i < account.length; i++) {
            account[i] = new Account();
        }
        ObjectMapper mapper = new ObjectMapper();
        List<Account> objectList = null;
        try {
            objectList = Arrays.asList(mapper.readValue(new File("Account.json"), Account[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int i = 0;
        for (Account accountt : objectList) {
            account[i].setID(accountt.getID());
            account[i].setPW(accountt.getPW());
            System.out.println(account[i].getID());
            System.out.println(account[i].getPW());
            System.out.println(Arrays.toString(account[i].getGenre()));
            i++;
        }
    }
}