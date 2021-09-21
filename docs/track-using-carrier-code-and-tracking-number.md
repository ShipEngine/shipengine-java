Track Using Carrier Code and Tracking Number
============================================
[ShipEngine](www.shipengine.com) allows you to track a package for a given carrier and tracking number. Please
see [our docs](https://www.shipengine.com/docs/tracking/) to learn more about tracking shipments.

Input Parameters
----------------

The `trackUsingCarrierCodeAndTrackingNumber` method requires the carrier code and tracking number of the shipment being
tracked.

Output
------
The `trackUsingCarrierCodeAndTrackingNumber` method returns tracking information associated with the shipment for the
carrier code and tracking number.

Example
-------

```java
import com.shipengine.ShipEngine;

public class ShipEngineDemo {
    public static void main() {
        ShipEngine shipengine = new ShipEngine("<YOUR_API_KEY_HERE>");

        Map<String, Object> trackingData = Map.of(
                "carrierCode", "se-1234",
                "trackingNumber", "abc123"
        );

        Map<String, String> result = shipengine.trackUsingCarrierCodeAndTrackingNumber(trackingData);
        System.out.println("result = " + result);
    }
} 
```

Example Output
--------------

### Tracking Result

```java
{carrier_status_code=1.0,status_description=Delivered,status_code=DE,exception_description=string,tracking_number=1Z932R800392060079,actual_delivery_date=2018-09-23T15:00:00.000Z,carrier_status_description=Your item was delivered in or at the mailbox at 9:10am on March,estimated_delivery_date=2018-09-23T15:00:00.000Z,events=[{occurred_at=2018-09-23T15:00:00.000Z,carrier_occurred_at=2018-09-23T15:00:00.000Z,description=Delivered,In/At Mailbox,city_locality=AUSTIN,state_province=TX,postal_code=78756.0,country_code=CA,company_name=Stamps.com,signer=string,event_code=string,latitude=-90.0,longitude=-180.0}],ship_date=2018-09-23T15:00:00.000Z}
```

### *JSON Output for reference:*

```json5
{
  "tracking_number": "1Z932R800392060079",
  "status_code": "DE",
  "status_description": "Delivered",
  "carrier_status_code": 1,
  "carrier_status_description": "Your item was delivered in or at the mailbox at 9:10 am on March",
  "ship_date": "2018-09-23T15:00:00.000Z",
  "estimated_delivery_date": "2018-09-23T15:00:00.000Z",
  "actual_delivery_date": "2018-09-23T15:00:00.000Z",
  "exception_description": "string",
  "events": [
    {
      "occurred_at": "2018-09-23T15:00:00.000Z",
      "carrier_occurred_at": "2018-09-23T15:00:00.000Z",
      "description": "Delivered, In/At Mailbox",
      "city_locality": "AUSTIN",
      "state_province": "TX",
      "postal_code": 78756,
      "country_code": "CA",
      "company_name": "Stamps.com",
      "signer": "string",
      "event_code": "string",
      "latitude": -90,
      "longitude": -180
    }
  ]
}
```

Exceptions
----------

- This method will only throw an exception that is an instance/extension of **ShipEngineException** if there is a
  problem if a problem occurs, such as a network error or an error response from the API.