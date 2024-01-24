package io.woori.account.wooriaccount.common.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import io.woori.account.wooriaccount.common.dto.notification.FindAllNotificationResponseDTO;
import io.woori.account.wooriaccount.common.service.inter.NotificationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

	private final NotificationService notificationService;

	@GetMapping(value = "/read/{customerId}")
	public ResponseEntity<Page<FindAllNotificationResponseDTO>> notification(
		Pageable pageable,
		@PathVariable(value = "customerId") Long customerId
	) {
		return ResponseEntity.ok(notificationService.readNotifications(pageable, customerId));
	}

	@GetMapping(value = "/subscribe/{id}")
	public SseEmitter subscribe(@PathVariable(value = "id") Long customerId) {
		return notificationService.subscribe(customerId);
	}

}
