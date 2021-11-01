package spring_mvc_and_hiber.dao;

import org.springframework.stereotype.Repository;
import spring_mvc_and_hiber.models.Role;
import spring_mvc_and_hiber.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> index() {
        TypedQuery<User> query =
                entityManager.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    @Override
    public User show(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(int id, User updatedUser) {
        User userToBeUpdate = show(id);

        entityManager.refresh(userToBeUpdate);

        userToBeUpdate.setName(updatedUser.getName());
        userToBeUpdate.setAge(updatedUser.getAge());
        userToBeUpdate.setPassword(updatedUser.getPassword());
        userToBeUpdate.setRoles(updatedUser.getRoles());
    }

    @Override
    public void delete(int id) {
        entityManager.remove(show(id));
    }

    @Override
    public User getUserByName(String name) {
        TypedQuery<User> query = entityManager.createQuery(
                "select u from User u where u.name = :name", User.class);
        query.setParameter("name", name);
        User aUser = query.getResultList().stream().findAny().orElse(null);
        return aUser;
    }
}
