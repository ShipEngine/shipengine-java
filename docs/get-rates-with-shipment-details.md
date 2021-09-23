Get Rates With Shipment Details
===============================
Given some shipment details and rate options, this method returns a list of rate quotes. Please see
[our docs](https://www.shipengine.com/docs/rates/) to learn more about calculating rates.

Input Parameters
----------------

The `getRatesWithShipmentDetails` method accepts shipment details as a `HashMap/Map<String,Object>`.

Output
------
The `getRatesWithShipmentDetails` method returns the rates that were calculated for the given shipment params.

Example
-------

```java
import com.shipengine.ShipEngine;

public class ShipEngineDemo {
    public static void main() {
        ShipEngine shipengine = new ShipEngine("<YOUR_API_KEY_HERE>");

        Map<String, Object> shipmentDetails = Map.ofEntries(
                Map.entry("shipment", Map.of(
                                "carrier_id", "se-1234",
                                "service_code", "usps_first_class_mail",
                                "external_order_id", "string",
                                "item", List.of(),
                                "tax_identifiers", List.of(
                                        Map.of(
                                                "taxable_entity_type", "shipper",
                                                "identifier_type", "vat",
                                                "issuing_authority", "string",
                                                "value", "string"
                                        )
                                ),
                                "external_shipment_id", "string",
                                "ship_date", "2018-09-23T00:00:00.000Z",
                                "ship_to", Map.ofEntries(
                                        Map.entry("name", "John Doe"),
                                        Map.entry("phone", "1-123-456-7894"),
                                        Map.entry("company_name", "The Home Depot"),
                                        Map.entry("address_line1", "1999 Bishop Grandin Blvd."),
                                        Map.entry("address_line2", "Unit 408"),
                                        Map.entry("address_line3", "Building #7"),
                                        Map.entry("city_locality", "Winnipeg"),
                                        Map.entry("state_province", "Manitoba"),
                                        Map.entry("postal_code", "78756"),
                                        Map.entry("country_code", "CA"),
                                        Map.entry("address_residential_indicator", "no")
                                ),
                                "ship_from", Map.ofEntries(
                                        Map.entry("name", "John Doe"),
                                        Map.entry("phone", "1-123-456-7894"),
                                        Map.entry("company_name", "The Home Depot"),
                                        Map.entry("address_line1", "1999 Bishop Grandin Blvd."),
                                        Map.entry("address_line2", "Unit 408"),
                                        Map.entry("address_line3", "Building #7"),
                                        Map.entry("city_locality", "Winnipeg"),
                                        Map.entry("state_province", "Manitoba"),
                                        Map.entry("postal_code", "78756"),
                                        Map.entry("country_code", "CA"),
                                        Map.entry("address_residential_indicator", "no")
                                )
                        )
                ));

        Map<String, String> result = shipengine.createLabelFromShipmentDetails(shipmentDetails);
        System.out.println("result = " + result);
    }
}
```

Example Output
--------------

### Successful Get Rates Result

```java
{serviceCode=usps_first_class_mail,modifiedAt=2021-07-28T16:56:40.223Z,advancedOptions={billToAccount=null,billToCountryCode=null,billToParty=null,billToPostalCode=null,containsAlcohol=null,deliveryDutyPaid=null,dryIce=null,dryIceWeight=null,nonMachinable=null,saturdayDelivery=null,useUPSGroundFreightPricing=null,freightClass=null,customField1=null,customField2=null,customField3=null,originType=null,shipperRelease=null,collectOnDelivery=null},createdAt=2021-07-28T16:56:40.257Z,customs={contents=merchandise,nonDelivery=return_to_sender,customsItems=[]},orderSourceCode=null,rateResponse={rates=[{rateId=se-784001113,rateType=shipment,carrierId=se-161650,shippingAmount={currency=usd,amount=3.12},insuranceAmount={currency=usd,amount=0.0},confirmationAmount={currency=usd,amount=0.0},otherAmount={currency=usd,amount=0.0},taxAmount=null,zone=5.0,packageType=package,deliveryDays=3.0,guaranteedService=false,estimatedDeliveryDate=2021-07-31T00:00:00Z,carrierDeliveryDays=3,shipDate=2021-07-28T00:00:00Z,negotiatedRate=false,serviceType=USPS First Class Mail,serviceCode=usps_first_class_mail,trackable=true,carrierCode=usps,carrierNickname=USPS,carrierFriendlyName=USPS,validationStatus=valid,warningMessages=[],errorMessages=[]}],invalidRates=[],rateRequestId=se-85117731,shipmentId=se-141694059,createdAt=2021-07-28T16:56:40.6148892Z,status=completed,errors=[]},taxIdentifiers=null,shipFrom={name=Medals of America,phone=800-308-0849,companyName=null,addressLine1=114Southchase Blvd,addressLine2=null,addressLine3=null,cityLocality=Fountain Inn,stateProvince=SC,postalCode=29644,countryCode=US,addressResidentialIndicator=unknown},externalShipmentId=null,externalOrderId=null,confirmation=none,shipDate=2021-07-28T00:00:00Z,packages=[{packageCode=package,weight={value=2.9,unit=ounce},dimensions={unit=inch,length=0.0,width=0.0,height=0.0},insuredValue={currency=usd,amount=0.0},trackingNumber=null,labelMessages={reference1=4051492,reference2=null,reference3=null},externalPackageId=null}],tags=[],originType=null,insuranceProvider=none,warehouseId=null,shipmentId=se-141694059,returnTo={name=Medals of America,phone=800-308-0849,companyName=null,addressLine1=114Southchase Blvd,addressLine2=null,addressLine3=null,cityLocality=Fountain Inn,stateProvince=SC,postalCode=29644,countryCode=US,addressResidentialIndicator=unknown},totalWeight={value=2.9,unit=ounce},carrierId=se-161650,items=[],shipmentStatus=pending,shipTo={name=James Atkinson,phone=null,companyName=null,addressLine1=28793Fox Fire Lane,addressLine2=null,addressLine3=null,cityLocality=Shell Knob,stateProvince=MO,postalCode=65747,countryCode=US,addressResidentialIndicator=yes}}
```

### *JSON Output for reference:*

```json5
{
  "shipmentId": "se-141694059",
  "carrierId": "se-161650",
  "serviceCode": "usps_first_class_mail",
  "externalOrderId": null,
  "items": [],
  "taxIdentifiers": null,
  "externalShipmentId": null,
  "shipDate": "2021-07-28T00:00:00Z",
  "createdAt": "2021-07-28T16:56:40.257Z",
  "modifiedAt": "2021-07-28T16:56:40.223Z",
  "shipmentStatus": "pending",
  "shipTo": {
    "name": "James Atkinson",
    "phone": null,
    "companyName": null,
    "addressLine1": "28793 Fox Fire Lane",
    "addressLine2": null,
    "addressLine3": null,
    "cityLocality": "Shell Knob",
    "stateProvince": "MO",
    "postalCode": "65747",
    "countryCode": "US",
    "addressResidentialIndicator": "yes"
  },
  "shipFrom": {
    "name": "Medals of America",
    "phone": "800-308-0849",
    "companyName": null,
    "addressLine1": "114 Southchase Blvd",
    "addressLine2": null,
    "addressLine3": null,
    "cityLocality": "Fountain Inn",
    "stateProvince": "SC",
    "postalCode": "29644",
    "countryCode": "US",
    "addressResidentialIndicator": "unknown"
  },
  "warehouseId": null,
  "returnTo": {
    "name": "Medals of America",
    "phone": "800-308-0849",
    "companyName": null,
    "addressLine1": "114 Southchase Blvd",
    "addressLine2": null,
    "addressLine3": null,
    "cityLocality": "Fountain Inn",
    "stateProvince": "SC",
    "postalCode": "29644",
    "countryCode": "US",
    "addressResidentialIndicator": "unknown"
  },
  "confirmation": "none",
  "customs": {
    "contents": "merchandise",
    "nonDelivery": "return_to_sender",
    "customsItems": []
  },
  "advancedOptions": {
    "billToAccount": null,
    "billToCountryCode": null,
    "billToParty": null,
    "billToPostalCode": null,
    "containsAlcohol": null,
    "deliveryDutyPaid": null,
    "dryIce": null,
    "dryIceWeight": null,
    "nonMachinable": null,
    "saturdayDelivery": null,
    "useUPSGroundFreightPricing": null,
    "freightClass": null,
    "customField1": null,
    "customField2": null,
    "customField3": null,
    "originType": null,
    "shipperRelease": null,
    "collectOnDelivery": null
  },
  "originType": null,
  "insuranceProvider": "none",
  "tags": [],
  "orderSourceCode": null,
  "packages": [
    {
      "packageCode": "package",
      "weight": {
        "value": 2.9,
        "unit": "ounce"
      },
      "dimensions": {
        "unit": "inch",
        "length": 0,
        "width": 0,
        "height": 0
      },
      "insuredValue": {
        "currency": "usd",
        "amount": 0
      },
      "trackingNumber": null,
      "labelMessages": {
        "reference1": "4051492",
        "reference2": null,
        "reference3": null
      },
      "externalPackageId": null
    }
  ],
  "totalWeight": {
    "value": 2.9,
    "unit": "ounce"
  },
  "rateResponse": {
    "rates": [
      {
        "rateId": "se-784001113",
        "rateType": "shipment",
        "carrierId": "se-161650",
        "shippingAmount": {
          "currency": "usd",
          "amount": 3.12
        },
        "insuranceAmount": {
          "currency": "usd",
          "amount": 0
        },
        "confirmationAmount": {
          "currency": "usd",
          "amount": 0
        },
        "otherAmount": {
          "currency": "usd",
          "amount": 0
        },
        "taxAmount": null,
        "zone": 5,
        "packageType": "package",
        "deliveryDays": 3,
        "guaranteedService": false,
        "estimatedDeliveryDate": "2021-07-31T00:00:00Z",
        "carrierDeliveryDays": "3",
        "shipDate": "2021-07-28T00:00:00Z",
        "negotiatedRate": false,
        "serviceType": "USPS First Class Mail",
        "serviceCode": "usps_first_class_mail",
        "trackable": true,
        "carrierCode": "usps",
        "carrierNickname": "USPS",
        "carrierFriendlyName": "USPS",
        "validationStatus": "valid",
        "warningMessages": [],
        "errorMessages": []
      }
    ],
    "invalidRates": [],
    "rateRequestId": "se-85117731",
    "shipmentId": "se-141694059",
    "createdAt": "2021-07-28T16:56:40.6148892Z",
    "status": "completed",
    "errors": []
  }
}
```

Exceptions
----------

- This method will only throw an exception that is an instance/extension of **ShipEngineException** if there is a
  problem if a problem occurs, such as a network error or an error response from the API.