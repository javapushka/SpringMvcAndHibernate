package spring_mvc_and_hiber.dao;

import org.springframework.stereotype.Repository;
import spring_mvc_and_hiber.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<User> index() {
        return entityManager.createQuery("from User").getResultList();
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
    }

    @Override
    public void delete(int id) {
        entityManager.remove(show(id));
    }
}
