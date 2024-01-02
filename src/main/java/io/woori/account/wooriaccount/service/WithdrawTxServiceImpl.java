package io.woori.account.wooriaccount.service;

import io.woori.account.wooriaccount.domain.entity.WithdrawTxHistory;
import io.woori.account.wooriaccount.dto.tx.SaveTxRequestDTO;
import io.woori.account.wooriaccount.exception.ErrorCode;
import io.woori.account.wooriaccount.exception.TxHistoryException;
import io.woori.account.wooriaccount.repository.jpa.TxHistoryRepository;
import io.woori.account.wooriaccount.service.inter.TxHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WithdrawTxServiceImpl implements TxHistoryService<WithdrawTxHistory, Long> {

    private final TxHistoryRepository<WithdrawTxHistory> txHistoryRepository;

    @Override
    public WithdrawTxHistory save(SaveTxRequestDTO dto) {
        return WithdrawTxHistory.of(dto.getSender(),
                dto.getReceiver(),
                dto.getAmount(),
                dto.getBalanceAfterTx(),
                dto.getDescription());
    }

    @Override
    @Transactional(readOnly = true)
    public WithdrawTxHistory findTxById(Long txId) {
        return txHistoryRepository.findById(txId).orElseThrow(
                () -> new TxHistoryException(ErrorCode.NOT_FOUND_TX)
        );
    }

}

