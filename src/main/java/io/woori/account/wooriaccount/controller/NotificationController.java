package io.woori.account.wooriaccount.controller;

import io.woori.account.wooriaccount.domain.entity.Customer;
import io.woori.account.wooriaccount.dto.notification.FindAllNotificationResponseDTO;
import io.woori.account.wooriaccount.service.inter.CustomerService;
import java.security.Principal;

import io.woori.account.wooriaccount.service.inter.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping(value = "/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(value = "/read")
    public ResponseEntity<Page<FindAllNotificationResponseDTO>> notification(Pageable pageable,
                                                                             @AuthenticationPrincipal Principal principal)
    {
        return ResponseEntity.ok(notificationService.readNotifications(pageable, 1L));
    }

    @GetMapping(value = "/subscribe")
    public SseEmitter subscribe(@AuthenticationPrincipal Principal principal) {
        notificationService.subscribe(1L);
    }

}
