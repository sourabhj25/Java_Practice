package com.agsft.ticketleap.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agsft.ticketleap.constants.HttpStatusCodes;
import com.agsft.ticketleap.model.req.PaymentRequestDTO;
import com.agsft.ticketleap.services.ProcessPayment;
import com.agsft.ticketleap.util.TicketLeapUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.stripe.model.Charge;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;

@RestController
@Api(value = "Payment Controller")
@CrossOrigin
public class PaymentController {

	@Autowired
	ProcessPayment processPayment;

	@Autowired
	TicketLeapUtils ticketLeapUtils;

	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	@ApiOperation(value = "payment", notes = "{ \"email\" : \"abcd@gmail.com\", \"token_id\" : \"12345678\", \"amount\" : 2000, \"cc_number\" : \"4242424242424242\" }")
	@SneakyThrows
	public ResponseEntity<?> purhcaseTickets(
			@ApiParam("payment information as explained in above JSON format") @Valid @RequestParam("paymentRequest") String paymentRequest) {
		System.out.println("Inside method");

		PaymentRequestDTO paymentRequestDTO = new ObjectMapper().registerModule(new Jdk8Module())
				.readValue(paymentRequest, PaymentRequestDTO.class);

		DataBinder binder = new DataBinder(paymentRequestDTO);
		BindingResult bindingResult = binder.getBindingResult();
		ticketLeapUtils.validateEntity(paymentRequestDTO, bindingResult);

		if (bindingResult.hasErrors()) {
			return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.VALIDATION_ERROR,
					bindingResult.getAllErrors().get(0).getDefaultMessage(), null));
		} else {
			Charge charge = processPayment.generatePayment(paymentRequestDTO);
			if (charge != null)
				return ResponseEntity
						.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.OK, "Payment Succeeded", null));
			else
				return ResponseEntity.ok(ticketLeapUtils.createResponseEntityDTO(HttpStatusCodes.PAYMENT_FAILED,
						"Payment failed", null));
		}
	}

}
