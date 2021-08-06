package com.shipengine;

public class Config
{
    /*
      Your ShipEngine API key.
      This can be a production or sandbox key. Sandbox keys start with "TEST_".
    * */
    String apiKey;

    /*
      The URL of the ShipEngine API. You can usually leave this unset and it will
      default to our public API.
    * */
    String baseURL;

    /*
      Some ShipEngine API endpoints return paged data. This lets you control the
      number of items returned per request. Larger numbers will use more memory
      but will require fewer HTTP requests.

      Defaults to 50.
    * */
    int pageSize;

    /*
      If the ShipEngine client receives a rate limit error it can automatically
      retry the request after a few seconds. This setting lets you control how
      many times it will retry before giving up.

      Defaults to 1, which means up to 2 attempts will be made (the original
      attempt, plus one retry).
    * */
    int retries;

    /*
      The maximum amount of time (in milliseconds) to wait for a response from
      the ShipEngine server.

      Defaults to 5000 (5 seconds).
    * */
    int timeout;

    public Config()
    {
      timeout = 5000;
      retries = 1;
      pageSize = 50;
    }

    public String sayHi() {
      return "Hello World from CONFIG!";
    }
}
