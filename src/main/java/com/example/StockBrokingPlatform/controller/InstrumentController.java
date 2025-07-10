package com.example.StockBrokingPlatform.controller;

import com.example.StockBrokingPlatform.DTO.ClientDTO;
import com.example.StockBrokingPlatform.DTO.InstrumentDTO;
import com.example.StockBrokingPlatform.service.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instruments")
public class InstrumentController {

    @Autowired
    private InstrumentService instrumentService;

    //Get All
    @GetMapping
    public List<InstrumentDTO> getAllInstruments(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return instrumentService.getAllInstruments(page, size);
    }

    //Get by ID
    @GetMapping("/{id}")
    public InstrumentDTO getInstrumentById(@PathVariable Long id) {
        return instrumentService.getInstrumentById(id);
    }

    //Add
    @PostMapping
    public InstrumentDTO addInstrument(@RequestBody InstrumentDTO dto) {
        return instrumentService.addInstrument(dto);
    }

    //Update
    @PutMapping("/{id}")
    public InstrumentDTO updateInstrument(@PathVariable Long id, @RequestBody InstrumentDTO dto) {
        return instrumentService.updateInstrument(id, dto);
    }

    //Search by Symbol or Company Name
    @GetMapping("/search")
    @Query("""
    SELECT i FROM Instrument i
    WHERE (:symbol IS NULL OR LOWER(i.symbol) LIKE LOWER(CONCAT('%', :symbol, '%')))
    AND (:companyName IS NULL OR LOWER(i.companyName) LIKE LOWER(CONCAT('%', :companyName, '%')))
    """)
    public ResponseEntity<List<InstrumentDTO>> searchBySymbolOrCompanyName(@RequestParam(required = false) String symbol, @RequestParam(required = false) String companyName) {
        return ResponseEntity.ok(instrumentService.searchBySymbolOrCompanyName(symbol, companyName));
    }

    @GetMapping("/exchange-type/{exchangeType}")
    @Query("""
    SELECT i FROM Client i
    WHERE (:exchangeType IS NULL OR LOWER(i.exchangeType) LIKE LOWER(CONCAT('%', :exchangeType, '%')))
    """)
    public List<InstrumentDTO> getByExchangeType(@PathVariable String exchangeType) {
        return instrumentService.getInstrumentsByExchangeType(exchangeType);
    }
}
