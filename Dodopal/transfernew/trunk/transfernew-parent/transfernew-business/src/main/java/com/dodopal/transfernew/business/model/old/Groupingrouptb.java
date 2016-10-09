package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;


/**
 * The persistent class for the GROUPINGROUPTB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Groupingrouptb.findAll", query="SELECT g FROM Groupingrouptb g")
public class Groupingrouptb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
//	private GroupingrouptbPK id;
//  @Column(name="FIRST_GROUPID")
    private long firstGroupid;

//  @Column(name="SECOND_GROUPID")
    private long secondGroupid;
    
    
    
	public long getFirstGroupid() {
        return firstGroupid;
    }

    public void setFirstGroupid(long firstGroupid) {
        this.firstGroupid = firstGroupid;
    }

    public long getSecondGroupid() {
        return secondGroupid;
    }

    public void setSecondGroupid(long secondGroupid) {
        this.secondGroupid = secondGroupid;
    }

    public Groupingrouptb() {
	}



}