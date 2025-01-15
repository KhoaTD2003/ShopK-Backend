package com.example.demo.Dtos;

import com.example.demo.Repositories.HoaDonRepository;
import com.example.demo.Services.GiamGiaService;
import com.example.demo.Services.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DiscountScheduler {

    @Autowired
    private GiamGiaService giamGiaService;

    @Autowired
    private HoaDonService hoaDonService;

    // Cron job chạy vào 0h mỗi ngày
//    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(fixedRate = 10000)
    public void checkAndUpdateExpiredDiscounts() {
        giamGiaService.updateExpiredDiscounts();
        System.out.println("Đã cập nhật trạng thái giảm giá hết hạn!");
    }

    // Cron job cho hóa đơn
//    @Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(fixedRate = 10000)

    @Scheduled(cron = "0 0 0 * * ?") // Hoặc fixedRate để test
    public void checkAndUpdateUnpaidInvoices() {
        System.out.println("Scheduler đang chạy...");
        hoaDonService.updateUnpaidInvoices();
        System.out.println("Đã cập nhật trạng thái hóa đơn hết hạn!");

    }
}
