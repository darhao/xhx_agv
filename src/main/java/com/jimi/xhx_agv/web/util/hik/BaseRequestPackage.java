   package com.jimi.xhx_agv.web.util.hik;
   
   
   public class BaseRequestPackage {
     private String reqCode;
     
     private String reqTime;
     
     private String clientCode;
     
     private String tokenCode;
     
   
     BaseRequestPackage(String reqCode) {
       this.reqCode = reqCode;
     }
     
     BaseRequestPackage(String reqCode, String reqTime, String clientCode, String tokenCode) {
       this.reqCode = reqCode;
       this.reqTime = reqTime;
       this.clientCode = clientCode;
       this.tokenCode = tokenCode;
     }
     
     public String getReqCode() {
       return this.reqCode;
     }
     
     public void setReqCode(String reqCode) {
       this.reqCode = reqCode;
     }
     
     public String getReqTime() {
       return this.reqTime;
     }
     
     public void setReqTime(String reqTime) {
       this.reqTime = reqTime;
     }
     
     public String getClientCode() {
       return this.clientCode;
     }
     
     public void setClientCode(String clientCode) {
       this.clientCode = clientCode;
     }
     
     public String getTokenCode() {
       return this.tokenCode;
     }
     
     public void setTokenCode(String tokenCode) {
       this.tokenCode = tokenCode;
     }
   }


  