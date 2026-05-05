package ra.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.edu.dto.request.OrderCreateDTO;
import ra.edu.entity.OrderStatus;
import ra.edu.service.OrderService;

import java.security.Principal;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // CUSTOMER đặt hàng
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderCreateDTO dto, Principal principal) {
        Long userId = Long.valueOf(principal.getName());
        return ResponseEntity.status(201).body(orderService.createOrder(userId, dto));
    }

    // CUSTOMER xem đơn của mình
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/my")
    public ResponseEntity<?> getMyOrders(Principal principal) {
        Long userId = Long.valueOf(principal.getName());
        return ResponseEntity.ok(orderService.getMyOrders(userId));
    }

    // STAFF + ADMIN xem tất cả đơn
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // STAFF cập nhật trạng thái
    @PreAuthorize("hasRole('STAFF')")
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateStatus(id, status));
    }
}
