package com.robotrader.spring.service.log;

import com.robotrader.spring.dto.livetrade.TradeTransactionLogDTO;
import com.robotrader.spring.model.log.TradeTransactionLog;
import com.robotrader.spring.repository.log.TradeTransactionLogRepository;
import com.robotrader.spring.service.interfaces.ITradeTransactionLogService;
import com.robotrader.spring.trading.dto.TradeTransaction;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeTransactionLogService implements ITradeTransactionLogService {
    private final TradeTransactionLogRepository tradeTransactionLogRepository;

    @Autowired
    public TradeTransactionLogService(TradeTransactionLogRepository tradeTransactionLogRepository) {
        this.tradeTransactionLogRepository = tradeTransactionLogRepository;
    }

    @Transactional
    @Override
    public void save(TradeTransactionLog tradeTransactionLog) {
        tradeTransactionLogRepository.save(tradeTransactionLog);
    }

    @Transactional
    @Override
    public void log(TradeTransaction tradeTransaction) {
        TradeTransactionLog logEntry = new TradeTransactionLog();
        logEntry.setTransactionId(tradeTransaction.getTransactionId());
        logEntry.setAction(tradeTransaction.getAction());
        logEntry.setTicker(tradeTransaction.getTicker());
        logEntry.setTransactionPrice(tradeTransaction.getTransactionPrice());
        logEntry.setTransactionQuantity(tradeTransaction.getTransactionQuantity());
        logEntry.setPortfolioType(tradeTransaction.getPortfolioType());
        logEntry.setTransactionDateTime(tradeTransaction.getTransactionDateTime());
        logEntry.setTransactionAmount(tradeTransaction.getTransactionAmount());

        save(logEntry);
    }

    @Override
    public Page<TradeTransactionLogDTO> getTradeTransactionLogs(Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "transactionDateTime")
        );
        Page<TradeTransactionLog> logs = tradeTransactionLogRepository.findAll(sortedPageable);
        return logs.map(log -> new TradeTransactionLogDTO(
                log.getTransactionId(),
                log.getTicker(),
                log.getAction(),
                log.getTransactionDateTime(),
                log.getTransactionQuantity(),
                log.getTransactionPrice(),
                log.getTransactionAmount(),
                log.getPortfolioType()
        ));
    }

    @Override
    public List<TradeTransactionLog> getAllTradeTransactionLogs() {
        return tradeTransactionLogRepository.findAll();
    }
}
