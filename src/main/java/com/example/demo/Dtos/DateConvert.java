package com.example.demo.Dtos;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateConvert {
        public static LocalDateTime convertToLocalDateTime(Date date) {
            return date.toInstant()   // Chuyển đổi Date thành Instant
                    .atZone(ZoneId.systemDefault())  // Áp dụng múi giờ hệ thống
                    .toLocalDateTime();  // Chuyển Instant thành LocalDateTime
        }


}
