package com.example.StockBrokingPlatform.controller;

import com.example.StockBrokingPlatform.DTO.InstrumentDTO;
import com.example.StockBrokingPlatform.service.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instruments")
public class InstrumentController {

    @Autowired
    private InstrumentService instrumentService;

    @GetMapping
    public List<InstrumentDTO> getAllInstruments(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        return instrumentService.getAllInstruments(page, size);
    }

    @GetMapping("/{id}")
    public InstrumentDTO getInstrumentById(@PathVariable Long id) {
        return instrumentService.getInstrumentById(id);
    }

    @PostMapping
    public InstrumentDTO addInstrument(@RequestBody InstrumentDTO dto) {
        return instrumentService.addInstrument(dto);
    }

    @PutMapping("/{id}")
    public InstrumentDTO updateInstrument(@PathVariable Long id, @RequestBody InstrumentDTO dto) {
        return instrumentService.updateInstrument(id, dto);
    }

    @GetMapping("/search")
    public List<InstrumentDTO> searchInstrument(@RequestParam String query) {
        return instrumentService.searchInstrument(query);
    }

    @GetMapping("/exchange-type/{exchangeType}")
    public List<InstrumentDTO> getByExchangeType(@PathVariable String exchangeType) {
        return instrumentService.getInstrumentsByExchangeType(exchangeType);
    }
}
