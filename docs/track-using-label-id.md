Track package by Label ID Documentation
=======================================
[ShipEngine](www.shipengine.com) allows you to track a package by its ShipEngine `label_id`. This is the recommended
way to track shipments whose labels have been crated using ShipEngine API.

Please see [our docs](https://www.shipengine.com/docs/tracking/track-by-label-id/) to learn
more about tracking shipments.

Input
------
The `track_by_label_id` method requires the ID of the label associated with the shipment you are trying to track.
```java
```

Output
------
The `track_by_label_id` method returns tracking information associated with the shipment for the given label ID.

Example
=======
```java

```

Example Output
==============
```java

```

Exceptions
==========

- This method will only throw an exception that is an instance/extension of
  ([ShipEngineError]()) if there is a problem if a problem occurs, such as a network
  error or an error response from the API.
