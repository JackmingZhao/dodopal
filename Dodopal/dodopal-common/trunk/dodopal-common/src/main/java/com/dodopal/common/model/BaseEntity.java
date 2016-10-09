package com.dodopal.common.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2633028000208180824L;
    
	//唯一标识(UUID):串联各个系统
    private String uniqueIdentification;  

    private String id;

    private Date createDate;

    private Date updateDate;

    private String createUser;

    private String updateUser;    

    public String getUniqueIdentification() {
		return uniqueIdentification;
	}

	public void setUniqueIdentification(String uniqueIdentification) {
		this.uniqueIdentification = uniqueIdentification;
	}

	public Date getCreateDate() {
        return createDate;
    }
	public String getCreateDateStr() {
	    if (this.createDate == null) {
	        return null;
        } else {
            return new SimpleDateFormat("yyyy-MM-dd").format(this.createDate);
        }
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
