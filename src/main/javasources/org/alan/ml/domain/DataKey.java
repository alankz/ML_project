package org.alan.ml.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class DataKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3226671811462241560L;

	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false)
	private Date date;
	
	@Column(name = "website", nullable = false)
	private String website;
	
	public DataKey(Date date,String website){
		super();
		this.date = date;
		this.website = website;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataKey)) return false;
        DataKey that = (DataKey) o;
        return Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getWebsite(), that.getWebsite());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getWebsite());
    }

	@Override
	public String toString() {
		return "DataKey [date=" + date + ", website=" + website + "]";
	} 
	
}
