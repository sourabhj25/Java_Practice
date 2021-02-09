package com.phoenix.md5reader.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.phoenix.md5reader.model.MD5Entity;

/**
 * @author Vishal Arora
 *
 */
@Repository
public interface MD5Repository extends JpaRepository<MD5Entity, Long> {

	@Query( value = "select count(*) from md5 m where m.hashcode=:hashcode", nativeQuery=true)
	int existing(@Param("hashcode") String hashcode);
	
}
