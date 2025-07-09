package com.example.StockBrokingPlatform.repository;

import com.example.StockBrokingPlatform.model.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, Long> {
    List<Instrument> findBySymbolContainingIgnoreCaseOrCompanyNameContainingIgnoreCase(String symbol, String companyName);
    List<Instrument> findByExchangeTypeIgnoreCase(String exchangeType);
}
