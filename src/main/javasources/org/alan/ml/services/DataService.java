package org.alan.ml.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.alan.ml.dbConnection.HibernateUtil;
import org.alan.ml.domain.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;;

public class DataService {

	final static Logger logger = LogManager.getLogger(DataService.class);

	public List<Data> getDataTable() {

		try {
			Session session = HibernateUtil.getCurrentSession();
			Transaction tx = session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Data> query = builder.createQuery(Data.class);
			Root<Data> dataRoot = query.from(Data.class);
			query.select(dataRoot);

			return session.createQuery(query).getResultList();

		} catch (Exception ex) {
			logger.error("Failed to getDataTable: " + ex);
			return new ArrayList<Data>();
		}

	}

	public void importDataList(List<Data> dataList) throws Exception {

		Session session = HibernateUtil.openSession();

		try {
			Transaction tx = session.beginTransaction();
			dataList.forEach(data -> session.save(data));
			tx.commit();
		} finally {
			session.close();
		}

	}
	
	public void clearDataTable() {

		Session session = HibernateUtil.openSession();

		try {
			Transaction tx = session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaDelete<Data> deleteQuery= builder.createCriteriaDelete(Data.class);
			Root<Data> dataRoot = deleteQuery.from(Data.class);
			int deletedRecords = session.createQuery(deleteQuery).executeUpdate();
			tx.commit();
			logger.info(deletedRecords + " numbers of records delete from Data table");
		} finally {
			session.close();
		}
	}
	
	
}
