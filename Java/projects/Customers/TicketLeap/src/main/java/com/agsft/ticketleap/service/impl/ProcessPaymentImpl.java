package com.agsft.ticketleap.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.agsft.ticketleap.model.Transaction;
import com.agsft.ticketleap.model.User;
import com.agsft.ticketleap.model.req.PaymentRequestDTO;
import com.agsft.ticketleap.repo.TransactionRepository;
import com.agsft.ticketleap.services.ProcessPayment;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

import lombok.extern.java.Log;

/**
 * @author Vishal
 *
 */
@Log
@Component
public class ProcessPaymentImpl implements ProcessPayment {

	@Autowired
	private MongoOperations mongoOps;

	@Autowired
	private TransactionRepository transRepo;

	@Autowired
	BCryptPasswordEncoder bCrypt;

	@Value("${stripe.secret.key}")
	private String secret_key;

	@PostConstruct
	public void init() {
		Stripe.apiKey = secret_key;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.agsft.ticketleap.services.ProcessPayment#generatePayment(double,
	 * java.lang.String)
	 */
	@Override
	public Charge generatePayment(PaymentRequestDTO paymentRequestDTO) {
		/**
		 * payment info
		 */
		Map<String, Object> paymentMap = new HashMap<>();
		paymentMap.put("amount", paymentRequestDTO.getAmount());
		paymentMap.put("currency", "usd");
		paymentMap.put("description", "Charge generated at : " + new Date());
		paymentMap.put("source", paymentRequestDTO.getToken_id());

		Charge charge = null;
		try {
			charge = Charge.create(paymentMap);
		} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException
				| APIException e) {
			log.log(Level.SEVERE, "Transaction failed due to : " + e.getMessage());
		}

		/**
		 * save for user
		 */
		if (paymentRequestDTO.getEmail() != null && paymentRequestDTO.getEmail().isPresent()) {
			Query query = new Query();
			query.addCriteria(Criteria.where("email").is(paymentRequestDTO.getEmail().get()));

			Update update = new Update();
			update.set("cc_number", bCrypt.encode(paymentRequestDTO.getCc_number()));

			mongoOps.upsert(query, update, User.class);
		}

		/**
		 * save this transaction
		 */
		Transaction transaction = new Transaction();
		transaction.setAmount(paymentRequestDTO.getAmount());
		transaction.setCurrency("USD");
		transaction.setTimestamp(new Date());

		if (charge != null) {
			transaction.setTaxation_id(charge.getBalanceTransaction());
			transaction.setTransaction_id(charge.getId());
			transaction.setToken_id(paymentRequestDTO.getToken_id());
			transaction.setSuccessful(true);
		} else {
			transaction.setTaxation_id("");
			transaction.setTransaction_id("");
			transaction.setToken_id("");
			transaction.setSuccessful(false);
		}

		if (paymentRequestDTO.getEmail() != null && paymentRequestDTO.getEmail().isPresent()) {
			transaction.setEmail(paymentRequestDTO.getEmail().get());
		} else {
			transaction.setEmail("anonymous");
		}

		transRepo.save(transaction);

		return charge;
	}

}
