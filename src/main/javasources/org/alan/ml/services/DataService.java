package org.alan.ml.services;

import org.alan.ml.dbConnection.HibernateUtil;
import org.alan.ml.domain.Data;
import org.hibernate.Session;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;;

public class DataService {
	public List<Data> getDataTable(){
		
		Session session = HibernateUtil.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Data> query = builder.createQuery(Data.class);
		Root<Data> dataRoot = query.from(Data.class);
		query.select(dataRoot);
		
		return session.createQuery(query).getResultList();
		
	}
}
