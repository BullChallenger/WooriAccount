package io.woori.account.wooriaccount.controller;

import io.swagger.models.Response;
import io.woori.account.wooriaccount.dto.account.AccountAllDTO;
import io.woori.account.wooriaccount.dto.account.AccountDTO;
import io.woori.account.wooriaccount.dto.account.AccountRemittanceDTO;
import io.woori.account.wooriaccount.service.AccountServiceImpl;
import io.woori.account.wooriaccount.service.inter.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {


    private final AccountServiceImpl accountService;


    @PostMapping("/create/{customerId}")
    public ResponseEntity<AccountDTO> createAccount(@PathVariable("customerId") Long customerId){

        return ResponseEntity.ok(accountService.accountCreate(customerId));
    }

    @PostMapping("/delete/{accountNumber}")
    public ResponseEntity<AccountDTO> deletedAccount(@PathVariable("accountNumber") String accountNumber){

        return ResponseEntity.ok(accountService.accountDelete(accountNumber));

    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDTO> getAccountByAccountNumber(@RequestParam("accountNumber") String accountNumber){

        return ResponseEntity.ok(accountService.accountInquiry(accountNumber));

    }

    @PostMapping("/remittance")
    public ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountRemittanceDTO dto){

        return ResponseEntity.ok(accountService.accountRemittance(dto));

    }

    @GetMapping("/find")
    public ResponseEntity<List<AccountAllDTO>> findAllAccount(Long id){
        return ResponseEntity.ok(accountService.findAllByCustomerId(id));
    }

}
