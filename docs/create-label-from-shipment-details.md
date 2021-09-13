Create Label From Shipment Details
===================================
[ShipEngine](www.shipengine.com) allows you programmatically create shipping labels. Please see 
[our docs](https://www.shipengine.com/docs/labels/create-a-label/) to learn more about creating shipping labels.

Input Parameters
----------------

The `createLabelFromShipmentDetails` method accepts shipment related params as a `HashMap/Map<String, Object>`.

Output
------
The `createLabelFromShipmentDetails` method returns the label that was created.

Example
-------

```java
import com.shipengine.ShipEngine;

public class ShipEngineDemo {
  public static void main() {
    ShipEngine shipengine = new ShipEngine("<YOUR_API_KEY_HERE>");

      Map<String, Object> shipmentDetails = new HashMap<>() {{
          put("shipment", new HashMap<>() {{
              put("carrier_id", "se-1234");
              put("service_code", "usps_first_class_mail");
              put("external_order_id", "string");
              put("items", new ArrayList<>());
              put("tax_identifiers", new ArrayList<>() {{
                  add(new HashMap<>() {{
                      put("taxable_entity_type", "shipper");
                      put("identifier_type", "vat");
                      put("issuing_authority", "string");
                      put("value", "string");
                  }});
              }});
              put("external_shipment_id", "string");
              put("ship_date", "2018-09-23T00:00:00.000Z");
              put("ship_to", new HashMap<>() {{
                  put("name", "John Doe");
                  put("phone", "1-123-456-7894");
                  put("company_name", "The Home Depot");
                  put("address_line1", "1999 Bishop Grandin Blvd.");
                  put("address_line2", "Unit 408");
                  put("address_line3", "Building #7");
                  put("city_locality", "Winnipeg");
                  put("state_province", "Manitoba");
                  put("postal_code", "78756");
                  put("country_code", "CA");
                  put("address_residential_indicator", "no");
              }});
              put("ship_from", new HashMap<>() {{
                  put("name", "John Doe");
                  put("phone", "1-123-456-7894");
                  put("company_name", "The Home Depot");
                  put("address_line1", "1999 Bishop Grandin Blvd.");
                  put("address_line2", "Unit 408");
                  put("address_line3", "Building #7");
                  put("city_locality", "Winnipeg");
                  put("state_province", "Manitoba");
                  put("postal_code", "78756");
                  put("country_code", "CA");
                  put("address_residential_indicator", "no");
              }});
          }});
      }};
    
    Map<String, String> result = shipengine.createLabelFromShipmentDetails(shipmentDetails);
    System.out.println("result = " + result);
  }
} 
```

Example Output
--------------

### Successful Create Label Result
```java
{insurance_claim=null, voided_at=null, batch_id=, tracking_status=in_transit, created_at=2021-08-05T16:47:47.8768838Z, is_return_label=false, carrier_id=se-656171, rma_number=null, package_code=package, insurance_cost={amount=0.0, currency=usd}, label_layout=4x6, service_code=usps_first_class_mail, tracking_number=9400111899560334651289, voided=false, label_download={href=https://api.shipengine.com/v1/downloads/10/_EKGeA4yuEuLzLq81iOzew/label-75693596.pdf, pdf=https://api.shipengine.com/v1/downloads/10/_EKGeA4yuEuLzLq81iOzew/label-75693596.pdf, png=https://api.shipengine.com/v1/downloads/10/_EKGeA4yuEuLzLq81iOzew/label-75693596.png, zpl=https://api.shipengine.com/v1/downloads/10/_EKGeA4yuEuLzLq81iOzew/label-75693596.zpl}, trackable=true, display_scheme=label, label_format=pdf, shipment_cost={amount=3.35, currency=usd}, packages=[{dimensions={height=0.0, length=0.0, unit=inch, width=0.0}, external_package_id=null, insured_value={amount=0.0, currency=usd}, label_messages={reference1=null, reference2=null, reference3=null}, package_code=package, package_id=8.0328023E7, sequence=1.0, tracking_number=9400111899560334651289, weight={unit=ounce, value=1.0}}], shipment_id=se-144794216, charge_event=carrier_default, form_download=null, label_image_id=null, is_international=false, label_id=se-799373193, carrier_code=stamps_com, ship_date=2021-08-05T00:00:00Z, status=completed}
```

### *JSON Output for reference:*
```json5
{
  "batch_id": "",
  "carrier_code": "stamps_com",
  "carrier_id": "se-656171",
  "charge_event": "carrier_default",
  "created_at": "2021-08-05T16:47:47.8768838Z",
  "display_scheme": "label",
  "form_download": null,
  "insurance_claim": null,
  "insurance_cost": {
    "amount": 0.0,
    "currency": "usd"
  },
  "is_international": false,
  "is_return_label": false,
  "label_download": {
    "href": "https://api.shipengine.com/v1/downloads/10/_EKGeA4yuEuLzLq81iOzew/label-75693596.pdf",
    "pdf": "https://api.shipengine.com/v1/downloads/10/_EKGeA4yuEuLzLq81iOzew/label-75693596.pdf",
    "png": "https://api.shipengine.com/v1/downloads/10/_EKGeA4yuEuLzLq81iOzew/label-75693596.png",
    "zpl": "https://api.shipengine.com/v1/downloads/10/_EKGeA4yuEuLzLq81iOzew/label-75693596.zpl"
  },
  "label_format": "pdf",
  "label_id": "se-799373193",
  "label_image_id": null,
  "label_layout": "4x6",
  "package_code": "package",
  "packages": [
    {
      "dimensions": {
        "height": 0.0,
        "length": 0.0,
        "unit": "inch",
        "width": 0.0
      },
      "external_package_id": null,
      "insured_value": {
        "amount": 0.0,
        "currency": "usd"
      },
      "label_messages": {
        "reference1": null,
        "reference2": null,
        "reference3": null
      },
      "package_code": "package",
      "package_id": 80328023,
      "sequence": 1,
      "tracking_number": "9400111899560334651289",
      "weight": {
        "unit": "ounce",
        "value": 1.0
      }
    }
  ],
  "rma_number": null,
  "service_code": "usps_first_class_mail",
  "ship_date": "2021-08-05T00:00:00Z",
  "shipment_cost": {
    "amount": 3.35,
    "currency": "usd"
  },
  "shipment_id": "se-144794216",
  "status": "completed",
  "trackable": true,
  "tracking_number": "9400111899560334651289",
  "tracking_status": "in_transit",
  "voided": false,
  "voided_at": null
}
```

Exceptions
----------

- This method will only throw an exception that is an instance/extension of **ShipEngineException** if there is
  a problem if a problem occurs, such as a network error or an error response from the API.