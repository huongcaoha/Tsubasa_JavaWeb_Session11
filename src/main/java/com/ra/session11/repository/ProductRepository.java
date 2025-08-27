package com.ra.session11.repository;

import com.ra.session11.model.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public Product save(Product product) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
          Product product1 = (Product) session.merge(product);
            session.getTransaction().commit();
            return product1;
        }catch (Exception e) {
            return null ;
        }
    }

    public List<Product> products(String searchNameProduct) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Product> products ;
            if (searchNameProduct == null || searchNameProduct.isEmpty()) {
                products = session.createQuery("from Product", Product.class).getResultList();
            }else {
               products = session.createQuery("from Product p where p.productName like concat('%',:search,'%') ", Product.class)
                       .setParameter("search",searchNameProduct)
                       .getResultList();
            }

            session.getTransaction().commit();
            return products;
        }catch (Exception e) {
            return null;
        }
    }

    public Product getProductById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Product product = session.createQuery("from Product where id = :id", Product.class)
                    .setParameter("id", id).uniqueResult();
            session.getTransaction().commit();
            return product;
        }catch (Exception e) {
            return null;
        }
    }

    public boolean deleteProductById(Product product) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(product);
            session.getTransaction().commit();
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
