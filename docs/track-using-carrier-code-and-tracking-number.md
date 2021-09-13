Track package by carrier_code and tracking_number Documentation
===============================================================
[ShipEngine](www.shipengine.com) allows you to track a package for a given carrier and tracking number.

Please see [our docs](https://www.shipengine.com/docs/tracking/) to learn more about tracking shipments.


Input Parameters
----------------
The `track_pacakge_by_carrier_code_and_tracking_number` method requires the carrier code
and tracking number of the shipment being tracked.

Output
------
The `track_pacakge_by_carrier_code_and_tracking_number` method returns tracking information associated with the shipment for the carrier code and tracking number.


Example
=======
```java

```

Example Output
==============
Tracking data for the shipment that corresponds to the tracking number provided.
```java

```

Exceptions
==========

- This method will only throw an exception that is an instance/extension of
  ([ShipEngineError](../shipengine/errors/__init__.py)) if there is a problem if a problem occurs, such as a network
  error or an error response from the API.
