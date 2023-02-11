package com.bms.util;

public class Constants {
    public static enum Status{
        BOOKED("BOOKED"),FAILED("FAILED");
        public String status;
        private Status(String status){
            this.status=status;
        }
    }
}
