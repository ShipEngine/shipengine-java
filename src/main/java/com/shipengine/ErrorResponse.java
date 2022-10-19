package com.shipengine;

import java.util.ArrayList;
import java.util.Map;

public class ErrorResponse
{
        private String requestId;
        private ArrayList<Map<String, String>> errors;
        
        public String getRequestId() {
            return this.requestId;
        }
        
        public ArrayList<Map<String, String>> getErrors() {
            return this.errors;
        }
        
        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }
        
        public void setErrors(ArrayList<Map<String, String>> errors)
        {
            this.errors = errors;
        }
        
        public ErrorResponse()
        {}
}