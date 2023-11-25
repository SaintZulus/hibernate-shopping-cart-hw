package mate.academy.dao.impl;

import java.util.Optional;
import mate.academy.dao.ShoppingCartDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.ShoppingCart;
import mate.academy.model.User;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class ShoppingCartDaoImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(shoppingCart);
            transaction.commit();
            return shoppingCart;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can`t add in DB Schopping cart= " + shoppingCart,e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<ShoppingCart> getByUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM ShoppingCart s LEFT JOIN FETCH s.tickets" +
                            " WHERE s.user =:user", ShoppingCart.class).setParameter("user",user)
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can`t get User by Name = " + user,e);
        }
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
       Session session = null;
       Transaction transaction = null;
       try {
           session = HibernateUtil.getSessionFactory().openSession();
           transaction = session.beginTransaction();
           session.merge(shoppingCart);
           transaction.commit();
       } catch (Exception e) {
           if (transaction != null) {
               transaction.commit();
           }
           throw new DataProcessingException("Can`t update in DB Shopping cart = " + shoppingCart,e);
       } finally {
           if (session != null) {
               session.close();
           }
       }
    }
}
