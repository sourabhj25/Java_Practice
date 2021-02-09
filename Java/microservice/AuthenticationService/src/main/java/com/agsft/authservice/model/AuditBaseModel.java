package com.agsft.authservice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * 
 * @author nilesh
 * This class used commonly for auditing purpose
 */
@Data
@ToString(includeFieldNames = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditBaseModel{
 	
	/** The created dt. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_dt")
	@CreatedDate
	private Date createdDt;
}