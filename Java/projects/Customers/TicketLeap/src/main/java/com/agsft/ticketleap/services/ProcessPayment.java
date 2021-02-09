package com.agsft.ticketleap.services;

import org.springframework.stereotype.Service;

import com.agsft.ticketleap.model.req.PaymentRequestDTO;
import com.stripe.model.Charge;

/**
 * @author Vishal Arora
 *
 */
@Service
public interface ProcessPayment {

	/**
	 * initiate payment
	 * 
	 * @param transaction_token
	 * @return
	 */
	Charge generatePayment(PaymentRequestDTO paymentRequestDTO);

}
