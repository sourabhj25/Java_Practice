package com.agsft.ticketleap.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.agsft.ticketleap.model.Transaction;

/**
 * @author Vishal
 *
 */
@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {

}
