// package com.example.eeet2582.Controller;

// import com.example.eeet2582.Controller.CreatePayment;
// import com.example.eeet2582.Controller.CreatePaymentResponse;
// import com.stripe.exception.StripeException;
// import com.stripe.model.PaymentIntent;
// import com.stripe.param.PaymentIntentCreateParams;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;

// import javax.validation.Valid;

// @RestController
// public class PaymentController {
//     @PostMapping("/create-payment-intent")
//     public CreatePaymentResponse createPaymentIntent(@RequestBody @Valid  CreatePayment createPayment)throws StripeException {
//         PaymentIntentCreateParams createParams = new
//                 PaymentIntentCreateParams.Builder()
//                 .setCurrency("usd")
//                 .putMetadata("featureRequest", createPayment.getFeatureRequest())
//                 .setAmount(createPayment.getAmount() * 100L)
//                 .build();

//         PaymentIntent intent = PaymentIntent.create(createParams);
//         return new CreatePaymentResponse(intent.getClientSecret());
//     }
// }