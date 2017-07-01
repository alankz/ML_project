package org.alan.ml.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "VISIT_INFO")
public class Data implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4953495504443307826L;

	@EmbeddedId
	private DataKey id;

	@Column(name = "visits", nullable = false)
	private int visits;

	public DataKey getId() {
		return id;
	}

	public void setId(DataKey id) {
		this.id = id;
	}

	public int getVisits() {
		return visits;
	}

	public void setVisits(int visits) {
		this.visits = visits;
	}

	@Override
	public String toString() {
		return "Data [id=" + id + ", visits=" + visits + "]";
	}

}
