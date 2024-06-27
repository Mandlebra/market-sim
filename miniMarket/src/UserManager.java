import java.util.*;

public class UserManager {
    private static UserManager singleton = null;
    private static HashMap<String, User> users = new HashMap<>();
    private UserManager()
    {

    }
    public static UserManager getInstance()
    {
        if (singleton == null)
            singleton = new UserManager();

        return singleton;
    }
    public void init(String[] usersIn) throws DataValidationException {
        for (int i = 0; i < usersIn.length; i++)
        {
            String newUserId = usersIn[i];
            User newUser = new User(newUserId);
            users.put(newUserId, newUser);
        }
    }

    public User getRandomUser()
    {
        List<User> values = new ArrayList<User>(users.values());
        Collections.shuffle(values);

        return values.get(0);
    }

    public void addToUser(String userId, OrderDTO o) throws DataValidationException {
        if (o == null)
        {
            throw new DataValidationException("Order cannot be null");
        }
        users.get(userId).addOrder(o);
    }

    public String toString()
    {
        StringBuilder str = new StringBuilder();
        for (User u : users.values())
        {
            str.append(u.toString()).append("\n\n");
        }
        return str.toString();
    }
}
