package io.woori.account.wooriaccount.controller;

import io.woori.account.wooriaccount.dto.tx.FindAllDepositTxResponseDTO;
import io.woori.account.wooriaccount.dto.tx.FindAllTxResponseDTO;
import io.woori.account.wooriaccount.dto.tx.FindAllWithdrawTxResponseDTO;
import io.woori.account.wooriaccount.service.DepositTxServiceImpl;
import io.woori.account.wooriaccount.service.TxServiceImpl;
import io.woori.account.wooriaccount.service.WithdrawTxServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/tx")
public class TxController {

    private final DepositTxServiceImpl depositTxService;
    private final WithdrawTxServiceImpl withdrawTxService;
    private final TxServiceImpl txService;

    @GetMapping(value = "/deposit/{accountId}/{lastTxHistoryId}")
    public ResponseEntity<Page<FindAllDepositTxResponseDTO>> readDepositTxHistory(@PathVariable(value = "accountId") Long accountId,
                                                                                  @PathVariable(value = "lastTxHistoryId") Long lastTxHistoryId,
                                                                                  Pageable pageable)
    {
        return ResponseEntity.ok(depositTxService.findTxHistoryAll(accountId, lastTxHistoryId, pageable));
    }

    @GetMapping(value = "/withdraw/{accountId}/{lastTxHistoryId}")
    public ResponseEntity<Page<FindAllWithdrawTxResponseDTO>> readWithdrawTxHistory(@PathVariable(value = "accountId") Long accountId,
                                                                                    @PathVariable(value = "lastTxHistoryId") Long lastTxHistoryId,
                                                                                    Pageable pageable)
    {
        return ResponseEntity.ok(withdrawTxService.findTxHistoryAll(accountId, lastTxHistoryId, pageable));
    }

    @GetMapping(value = "/all/{accountId}")
    public ResponseEntity<Page<FindAllTxResponseDTO>> readAllTxHistory(@PathVariable Long accountId,
                                                                       Pageable pageable)
    {
        return ResponseEntity.ok(txService.findBySenderIdOrReceiverId(accountId, pageable));
    }

}
