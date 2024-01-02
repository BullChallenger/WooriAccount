package io.woori.account.wooriaccount.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.woori.account.wooriaccount.dto.tx.FindAllDepositTxResponseDTO;
import io.woori.account.wooriaccount.dto.tx.QFindAllDepositTxResponseDTO;
import io.woori.account.wooriaccount.repository.querydsl.inter.QueryTransactionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static io.woori.account.wooriaccount.domain.entity.QAbstractTxHistory.abstractTxHistory;
import static io.woori.account.wooriaccount.domain.entity.QCustomer.customer;
import static io.woori.account.wooriaccount.domain.entity.QDepositTxHistory.depositTxHistory;

/*
* queryDsl을 사용한 레포지토리 입니다.
* @author yeom hwiju
* */
@Repository
@RequiredArgsConstructor
public class QueryTransactionHistoryRepositoryImpl implements QueryTransactionHistoryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<FindAllDepositTxResponseDTO> readDepositTxHistoryAll(Long accountId,
                                                                     Long lastTxHistoryId,
                                                                     Pageable pageable) {
        List<FindAllDepositTxResponseDTO> pageContent = jpaQueryFactory.select(
                new QFindAllDepositTxResponseDTO(
                    depositTxHistory.sender.customer.customerName,
                    depositTxHistory.amount,
                    depositTxHistory.balanceAfterTx,
                    depositTxHistory.description,
                    depositTxHistory.createdTime
                )
        ).from(depositTxHistory)
         .where(depositTxHistory.receiver.accountId.eq(accountId).and(ltDepositTxHistoryId(lastTxHistoryId)))
         .orderBy(depositTxHistory.createdTime.desc())
         .limit(pageable.getPageSize())
         .fetch();

        JPAQuery<Long> countDepositTxHistoryQuery = jpaQueryFactory
                .select(depositTxHistory.count())
                .from(depositTxHistory);

        return PageableExecutionUtils.getPage(pageContent, pageable, countDepositTxHistoryQuery::fetchOne);
    }

    private BooleanExpression ltDepositTxHistoryId(Long depositTxHistoryId) {
        if (depositTxHistoryId == null) {
            return null;
        }

        return depositTxHistory.txId.lt(depositTxHistoryId);
    }

}
