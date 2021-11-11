package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String model, int series) {
      //String HQL = "From User u LEFT OUTER JOIN FETCH u.car WHERE u.car.model=:model and u.car.series=:series";
      String HQL = "From User u WHERE u.car.model=:model and u.car.series=:series";
      User user = (User) sessionFactory.getCurrentSession()
              .createQuery(HQL)
              .setParameter("model", model)
              .setParameter("series", series)
              .uniqueResult();
      return user;
   }

}
