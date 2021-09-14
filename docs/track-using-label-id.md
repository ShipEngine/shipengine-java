Track By Label ID
=================
[ShipEngine](www.shipengine.com) allows you to track a package by its ShipEngine label ID. Please 
see [our docs](https://www.shipengine.com/docs/tracking/track-by-label-id/) to learn more about tracking shipments.

Input Parameters
----------------

The `trackUsingLabelId` method requires the ID of the label associated with the shipment you are trying to track.

Output
------
The `trackUsingLabelId` method returns tracking information associated with the shipment for the given label ID.

Example
-------

```java
import com.shipengine.ShipEngine;

public class ShipEngineDemo {
  public static void main() {
    ShipEngine shipengine = new ShipEngine("<YOUR_API_KEY_HERE>");

    Map<String, String> result = shipengine.trackUsingLabelId("se-1234");
    System.out.println("result = " + result);
  }
} 
```

Example Output
---------------

Example Output
--------------

```java
{carrier_status_code=1.0, status_description=Delivered, status_code=DE, exception_description=string, tracking_number=1Z932R800392060079, actual_delivery_date=2018-09-23T15:00:00.000Z, carrier_status_description=Your item was delivered in or at the mailbox at 9:10 am on March, estimated_delivery_date=2018-09-23T15:00:00.000Z, events=[{occurred_at=2018-09-23T15:00:00.000Z, carrier_occurred_at=2018-09-23T15:00:00.000Z, description=Delivered, In/At Mailbox, city_locality=AUSTIN, state_province=TX, postal_code=78756.0, country_code=CA, company_name=Stamps.com, signer=string, event_code=string, latitude=-90.0, longitude=-180.0}], ship_date=2018-09-23T15:00:00.000Z}
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

- This method will only throw an exception that is an instance/extension of
  ([ShipEngineError]()) if there is a problem if a problem occurs, such as a network
  error or an error response from the API.
