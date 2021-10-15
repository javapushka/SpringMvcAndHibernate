package spring_mvc_and_hiber.dao;

import spring_mvc_and_hiber.models.User;

import java.util.List;

public interface UserDao {

    List<User> index();

    User show(int id);

    void save(User user);

    void update(int id, User user);

    void delete(int id);
}
