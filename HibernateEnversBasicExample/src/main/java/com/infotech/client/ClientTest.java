package com.infotech.client;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.infotech.entities.Customer;
import com.infotech.util.HibernateUtil;

public class ClientTest {

	public static void main(String[] args) {
		Transaction tx = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			tx = session.beginTransaction();

			Customer customer1 = new Customer();
			customer1.setFirstName("Sean");
			customer1.setLastName("Murphy");
			customer1.setCreatedOn(new Date());
			
			Customer customer2 = new Customer();
			customer2.setFirstName("Martin");
			customer2.setLastName("Bingel");
			customer2.setCreatedOn(new Date());

			session.save(customer1);
			session.save(customer2);

			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive())
				tx.rollback();
			throw e;
		}

		System.out.println("----------Update a Record--------------------");

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			long customerId = 1L;

			Customer customer = session.get(Customer.class, customerId);
			if (customer != null) {
				tx = session.beginTransaction();
				customer.setLastName("Murphy 2");
				session.update(customer);

				tx.commit();
			} else {
				System.out.println("Customer details not found in database with ID:" + customerId);
			}

		} catch (Exception e) {
			if (tx != null && tx.isActive())
				tx.rollback();
			throw e;
		}

		System.out.println("----------Delete a Record--------------------");

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			long customerId = 2L;

			Customer customer = session.get(Customer.class, customerId);
			if (customer != null) {
				tx = session.beginTransaction();
				session.delete(customer);
				tx.commit();
			} else {
				System.out.println("Customer details not found in database with ID:" + customerId);
			}

			System.out.println("----------Delete a Record--------------------");

		} catch (Exception e) {
			if (tx != null && tx.isActive())
				tx.rollback();
			throw e;
		}
	}
}