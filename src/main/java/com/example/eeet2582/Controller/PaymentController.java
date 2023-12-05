package com.example.eeet2582.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.eeet2582.Service.StripeService;
import com.example.eeet2582.Model.Response;

@Controller
public class PaymentController {

	@Value("${stripe.key.public}")
	private String API_PUBLIC_KEY;

	private StripeService stripeService;

	public PaymentController(StripeService stripeService) {
		this.stripeService = stripeService;
	}

	@GetMapping("/subscription")
	public String subscriptionPage(Model model) {
		model.addAttribute("stripePublicKey", API_PUBLIC_KEY);
		return "subscription";
	}

	@GetMapping("/charge")
	public String chargePage(Model model) {
		model.addAttribute("stripePublicKey", API_PUBLIC_KEY);
		return "charge";
	}

	@PostMapping("/create-subscription")
	public @ResponseBody Response createSubscription(String email, String token, String plan, String coupon) {

		if (token == null || plan.isEmpty()) {
			return new Response(false, "Stripe payment token is missing. Please try again later.");
		}

		String customerId = stripeService.createCustomer(email, token);

		if (customerId == null) {
			return new Response(false, "An error accurred while trying to create customer");
		}

		String subscriptionId = stripeService.createSubscription(customerId, plan, coupon);

		if (subscriptionId == null) {
			return new Response(false, "An error accurred while trying to create subscription");
		}

		return new Response(true, "Success! your subscription id is " + subscriptionId);
	}

	@PostMapping("/cancel-subscription")
	public @ResponseBody Response cancelSubscription(String subscriptionId) {

		boolean subscriptionStatus = stripeService.cancelSubscription(subscriptionId);

		if (!subscriptionStatus) {
			return new Response(false, "Faild to cancel subscription. Please try again later");
		}

		return new Response(true, "Subscription cancelled successfully");
	}

	@PostMapping("/create-charge")
	public @ResponseBody Response createCharge(String email, String token) {

		if (token == null) {
			return new Response(false, "Stripe payment token is missing. please try again later.");
		}

		String chargeId = stripeService.createCharge(email, token, 999);// 9.99 usd

		if (chargeId == null) {
			return new Response(false, "An error accurred while trying to charge.");
		}

		// You may want to store charge id along with order information

		return new Response(true, "Success your charge id is " + chargeId);
	}
}
