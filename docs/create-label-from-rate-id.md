Create Label From Rate ID Documentation
=======================================
When retrieving rates for shipments using the `createLabelFromRateId` method, the returned information contains
a `rateId` property that can be used to purchase a label without having to refill in the shipment information
repeatedly.

Please see [our docs](https://www.shipengine.com/docs/labels/create-from-rate/) to learn more about creating shipping
labels from rates.

Input Parameters
----------------
The `createLabelFromRateId` method accepts a valid `rateId` string and a `HashMap/Map<String, Object>` of label params
that will dictate the label display and level of verification.

Example
-------

```java
import com.shipengine.ShipEngine;

public class ShipEngineDemo {
    public static void main() {
        ShipEngine shipengine = new ShipEngine("<YOUR_API_KEY_HERE>");

        String labelId = "se-1234";

        Map<String, Object> labelParams = Map.of(
                "label_layout", "4x6",
                "label_format", "pdf",
                "label_download_type", "url"
        );

        Map<String, String> result = shipengine.createLabelFromRateId(labelId, labelParams);
        System.out.println("result = " + result);
    }
} 
```

Output
------
The `createLabelFromRateId` method returns a shipping label that corresponds to the shipping and rate details
encapsulated in the `rateId` passed in.

Example Output:
---------------

- Successful `createLabelFromRateId()` result.

```java
{insurance_claim=null,voided_at=null,batch_id=,tracking_status=in_transit,created_at=2021-08-05T16:47:47.8768838Z,is_return_label=false,carrier_id=se-656171,rma_number=null,package_code=package,insurance_cost={amount=0.0,currency=usd},label_layout=4x6,service_code=usps_first_class_mail,tracking_number=9400111899560334651289,voided=false,label_download={href=https://api.shipengine.com/v1/downloads/10/_EKGeA4yuEuLzLq81iOzew/label-75693596.pdf, pdf=https://api.shipengine.com/v1/downloads/10/_EKGeA4yuEuLzLq81iOzew/label-75693596.pdf, png=https://api.shipengine.com/v1/downloads/10/_EKGeA4yuEuLzLq81iOzew/label-75693596.png, zpl=https://api.shipengine.com/v1/downloads/10/_EKGeA4yuEuLzLq81iOzew/label-75693596.zpl}, trackable=true, display_scheme=label, label_format=pdf, shipment_cost={amount=3.35, currency=usd}, packages=[{dimensions={height=0.0, length=0.0, unit=inch, width=0.0}, external_package_id=null, insured_value={amount=0.0, currency=usd}, label_messages={reference1=null, reference2=null, reference3=null}, package_code=package, package_id=8.0328023E7, sequence=1.0, tracking_number=9400111899560334651289, weight={unit=ounce, value=1.0}}], shipment_id=se-144794216, charge_event=carrier_default, form_download=null, label_image_id=null, is_international=false, label_id=se-1234, carrier_code=stamps_com, ship_date=2021-08-05T00:00:00Z, status=completed}
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
  "label_id": "se-1234",
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

- This method will only throw an exception that is an instance/extension of **ShipEngineException** if there is a
  problem if a problem occurs, such as a network error or an error response from the API.
