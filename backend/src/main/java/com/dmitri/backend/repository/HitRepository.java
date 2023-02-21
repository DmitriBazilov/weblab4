package com.dmitri.backend.repository;

import com.dmitri.backend.dto.HitDTO;
import com.dmitri.backend.dto.UserDTO;
import com.dmitri.backend.model.Hit;
import com.dmitri.backend.model.UserPrincipal;
import com.dmitri.backend.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import java.util.List;

@Stateless
@TransactionManagement(javax.ejb.TransactionManagementType.BEAN)
public class HitRepository {

    public void saveHit(HitDTO hit) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            session.save(hit);

            session.getTransaction().commit();
        } catch ( Exception e ) {
            if ( session.getTransaction().isActive() ) {
                session.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
        }
        finally {
            if ( session != null && session.isOpen() ) {
                session.close();
            }
        }
    }

    public List<HitDTO> getHits(UserDTO user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session
                    .createQuery("select x from HitDTO x where x.user = :user", HitDTO.class)
                    .setParameter("user", user)
                    .getResultList();
        } catch (Exception e) {
            if (session.getTransaction().isActive())
                session.getTransaction().rollback();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return null;
    }

    public int clearHits(UserDTO user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            int clearHits = session.createQuery("delete from HitDTO x where x.user = :user")
                    .setParameter("user", user)
                    .executeUpdate();
            transaction.commit();
            return clearHits;
        } catch (Exception e) {
            if (session.getTransaction().isActive())
                session.getTransaction().rollback();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return -1;
    }

}
