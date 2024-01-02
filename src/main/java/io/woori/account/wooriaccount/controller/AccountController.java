package io.woori.account.wooriaccount.controller;


import io.woori.account.wooriaccount.dto.account.AccountDTO;
import io.woori.account.wooriaccount.service.inter.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {


    private final AccountService accountService;


    @PostMapping("/create/{customerId}")
    public ResponseEntity<AccountDTO> createAccount(@PathVariable("customerId") Long customerId){

        return ResponseEntity.ok(accountService.accountCreate(customerId));
    }


}
