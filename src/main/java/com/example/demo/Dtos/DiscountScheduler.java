package com.example.demo.Dtos;

import com.example.demo.Services.GiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DiscountScheduler {
    @Autowired
    private GiamGiaService giamGiaService;

    // Cron job chạy vào 0h mỗi ngày
//    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(fixedRate = 10000)

    public void checkAndUpdateExpiredDiscounts() {
        giamGiaService.updateExpiredDiscounts();
        System.out.println("Đã cập nhật trạng thái giảm giá hết hạn!");
    }
}
