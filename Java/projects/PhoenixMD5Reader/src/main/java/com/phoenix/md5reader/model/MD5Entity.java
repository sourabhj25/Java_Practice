package com.phoenix.md5reader.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author Vishal Arora
 *
 */
@Entity
@Table(name = "md5")
@Data
@ToString(includeFieldNames = true)
@RequiredArgsConstructor
public class MD5Entity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "hashcode", unique = true)
	@NonNull
	private String md5HashCode;

}
