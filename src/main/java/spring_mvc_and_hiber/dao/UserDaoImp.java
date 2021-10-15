package spring_mvc_and_hiber.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import spring_mvc_and_hiber.models.User;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    //условная БД
    private int COUNT = 0;

    private List<User> userList;

    {
        userList = new ArrayList<>();
        userList.add(new User(++COUNT, "Jack", "35"));
        userList.add(new User(++COUNT, "Katty", "19"));
        userList.add(new User(++COUNT, "Vampire", "1020"));
        userList.add(new User(++COUNT, "JOHN_SINA", "42"));
    }


    @Override
    public List<User> index() {
        return userList;
    }

    @Override
    public User show(int id) {
        return userList.stream().filter(user -> user.getId() == id).findAny().orElse(null);
    }

    @Override
    public void save(User user) {
        user.setId(++COUNT);
        userList.add(user);
    }

    @Override
    public void update(int id, User updatedUser) {
        User userToBeUpdate = show(id);

        userToBeUpdate.setName(updatedUser.getName());
        userToBeUpdate.setAge(updatedUser.getAge());
    }

    @Override
    public void delete(int id) {
        userList.removeIf(user -> user.getId() == id);
    }
}
