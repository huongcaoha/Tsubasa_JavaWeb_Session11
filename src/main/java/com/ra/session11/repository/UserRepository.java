package com.ra.session11.repository;
import com.ra.session11.model.dto.UserRegisterDTO;
import com.ra.session11.model.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepository {
    @Autowired
    private SessionFactory sessionFactory;



    public User login(String username, String password) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from User u where u.username = :username and u.password = :password",User.class)
                    .setParameter("username",username)
                    .setParameter("password",password)
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public User register(User user) {

        try {
           Session session = sessionFactory.openSession();
           session.beginTransaction();
           User newUser = (User) session.merge(user);
           session.getTransaction().commit();
           return newUser;
        }catch (Exception e){
            return null;
        }
    }

    public User findUserByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select u from User u where u.username = :username",User.class)
                    .setParameter("username",username).getSingleResult();
        }catch (Exception e){
            return null;
        }
    }


}
