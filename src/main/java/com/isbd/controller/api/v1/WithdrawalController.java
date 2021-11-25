package com.isbd.controller.api.v1;

import com.isbd.model.Withdrawal;
import com.isbd.service.WithdrawalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/withdrawals",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class WithdrawalController {
    private final WithdrawalService withdrawalService;

    @GetMapping
    public ResponseEntity<List<Withdrawal>> getWithdrawals() {
        return ResponseEntity.ok(withdrawalService.getByPlayer());
    }

    @PostMapping()
    public void makeNewWithdrawal(@RequestBody final int villageId) {
        withdrawalService.createWithdrawal(villageId);
    }
}
