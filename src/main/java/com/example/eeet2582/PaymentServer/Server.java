// Server.java
//
// Use this sample code to handle webhook events in your integration.
//
// Using Maven:
// 1) Paste this code into a new file (src/main/java/com/stripe/sample/Server.java)
//
// 2) Create a pom.xml file. You can quickly copy one from an official Stripe Sample.
//   curl https://raw.githubusercontent.com/stripe-samples/accept-a-payment/8e94e50e68072c344f12b02f65a6240e1c656d4a/custom-payment-flow/server/java/pom.xml -o pom.xml
//
// 3) Build and run the server on http://localhost:4242
//   mvn compile
//   mvn exec:java -Dexec.mainClass=com.stripe.sample.Server

package com.example.eeet2582.PaymentServer;

import static spark.Spark.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.port;
import com.google.gson.JsonSyntaxException;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.net.Webhook;

public class Server {
    public static void server(String[] args) {
        // The library needs to be configured with your account's secret key.
        // Ensure the key is kept out of any version control system you might be using.
        Stripe.apiKey = "sk_test_51ODSQ5HGJ8Z2uMgtjhAWo6iZmlHzYkPBY0nAxoqooS1coUPj5Un4cnoWwnh4qnmCBgpPdf26sqoFMHQvrt9kMtEP00blXc4da1";

        // This is your Stripe CLI webhook secret for testing your endpoint locally.
        String endpointSecret = "whsec_b7d78f89abe52cda86cb30778d3b42d5dd79bbc82423b058eb367144ec0da103";

        List<Object> lineItems = new ArrayList<>();
        Map<String, Object> lineItem1 = new HashMap<>();
        lineItem1.put(
                "price",
                "price_1OENIeHGJ8Z2uMgtGSNm5Rxq");
        lineItem1.put("quantity", 1);
        lineItems.add(lineItem1);
        Map<String, Object> params = new HashMap<>();
        params.put("line_items", lineItems);

        try {
            PaymentLink paymentLink = PaymentLink.create(params);
        } catch (StripeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        port(4242);

        post("/webhook", (request, response) -> {
            String payload = request.body();
            String sigHeader = request.headers("Stripe-Signature");
            Event event = null;

            try {
                event = Webhook.constructEvent(
                        payload, sigHeader, endpointSecret);
            } catch (JsonSyntaxException e) {
                // Invalid payload
                response.status(400);
                return "";
            } catch (SignatureVerificationException e) {
                // Invalid signature
                response.status(400);
                return "";
            }

            // Deserialize the nested object inside the event
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            StripeObject stripeObject = null;
            if (dataObjectDeserializer.getObject().isPresent()) {
                stripeObject = dataObjectDeserializer.getObject().get();
            } else {
                // Deserialization failed, probably due to an API version mismatch.
                // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
                // instructions on how to handle this case, or return an error here.
            }
            // Handle the event
            switch (event.getType()) {
                case "payment_intent.succeeded": {
                    // Then define and call a function to handle the event payment_intent.succeeded
                    break;
                }
                // ... handle other event types
                default:
                    System.out.println("Unhandled event type: " + event.getType());
            }

            response.status(200);
            return "";
        });
    }
}