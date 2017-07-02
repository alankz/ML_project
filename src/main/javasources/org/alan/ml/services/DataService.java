package org.alan.ml.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.alan.ml.dbConnection.HibernateUtil;
import org.alan.ml.domain.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;;

public class DataService {

	final static Logger logger = LogManager.getLogger(DataService.class);

	public List<Data> getDataTableOrderByVisit(String queryDateString, int numberOfRecords) {

		try {
			Session session = HibernateUtil.getCurrentSession();
			Transaction tx = session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Data> query = builder.createQuery(Data.class);
			Root<Data> dataRoot = query.from(Data.class);
			
			query.select(dataRoot);
			query.orderBy(builder.desc(dataRoot.get("visits")));
			
			TypedQuery<Data> typedQuery;
			
			if (queryDateString!=null && !queryDateString.isEmpty()){
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date queryDate = dateFormat.parse(queryDateString);
				ParameterExpression<java.util.Date> parameter = builder.parameter(java.util.Date.class);
				Predicate queryDatePredicate = builder.equal(dataRoot.get("id").get("date").as(java.sql.Date.class), parameter);
				query.where(queryDatePredicate);
				typedQuery = session.createQuery(query);
				typedQuery.setParameter(parameter, queryDate , TemporalType.DATE);
			} else {
				typedQuery = session.createQuery(query);
			}
			
			if (numberOfRecords!=0) {
				typedQuery = typedQuery.setMaxResults(numberOfRecords);
			}
				
			return typedQuery.getResultList();

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
