package com.example.StockBrokingPlatform.service;

import com.example.StockBrokingPlatform.DTO.WatchListDTO;
import com.example.StockBrokingPlatform.DTO.WatchListItemDTO;
import com.example.StockBrokingPlatform.exception.ResourceNotFoundException;
import com.example.StockBrokingPlatform.mapper.WatchListItemMapper;
import com.example.StockBrokingPlatform.mapper.WatchListMapper;
import com.example.StockBrokingPlatform.model.*;
import com.example.StockBrokingPlatform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchListService {

    @Autowired
    private WatchListRepository watchListRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private WatchListItemRepository watchListItemRepository;

    public List<WatchListDTO> getWatchListsByClientId(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id " + clientId));
        return client.getWatchLists().stream()
                .map(WatchListMapper::toDTO)
                .collect(Collectors.toList());
    }

    public WatchListDTO getWatchListById(Long id) {
        WatchList watchList = watchListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Watchlist not found with id " + id));
        return WatchListMapper.toDTO(watchList);
    }

    public WatchListDTO createWatchList(WatchListDTO dto) {
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id " + dto.getClientId()));
        WatchList watchList = WatchListMapper.toEntity(dto, client);
        return WatchListMapper.toDTO(watchListRepository.save(watchList));
    }

    public WatchListDTO updateWatchList(Long id, WatchListDTO dto) {
        WatchList watchList = watchListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Watchlist not found with id " + id));
        watchList.setName(dto.getName());
        return WatchListMapper.toDTO(watchListRepository.save(watchList));
    }

    public void deleteWatchList(Long id) {
        WatchList watchList = watchListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Watchlist not found with id " + id));
        watchListRepository.delete(watchList);
    }

    public WatchListItemDTO addInstrumentToWatchList(Long watchListId, WatchListItemDTO dto) {
        WatchList watchList = watchListRepository.findById(watchListId)
                .orElseThrow(() -> new ResourceNotFoundException("Watchlist not found with id " + watchListId));
        Instrument instrument = instrumentRepository.findById(dto.getInstrumentId())
                .orElseThrow(() -> new ResourceNotFoundException("Instrument not found with id " + dto.getInstrumentId()));

        WatchListItem item = new WatchListItem();
        item.setInstrument(instrument);
        item.setWatchList(watchList);
        item.setAddedDate(dto.getAddedDate() != null ? dto.getAddedDate() : java.time.LocalDateTime.now());

        return WatchListItemMapper.toDTO(watchListItemRepository.save(item));
    }

    public void removeInstrumentFromWatchList(Long watchListId, Long instrumentId) {
        WatchListItem item = watchListItemRepository.findByWatchListIdAndInstrumentId(watchListId, instrumentId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found in watchlist"));
        watchListItemRepository.delete(item);
    }

    public String getWatchListSummary(Long id) {
        WatchList watchList = watchListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Watchlist not found with id " + id));

        double totalValue = watchList.getItems().stream()
                .map(item -> item.getInstrument().getCurrentPrice())
                .filter(price -> price != null)
                .reduce(0.0, Double::sum);

        return "Watchlist '" + watchList.getName() + "' contains " + watchList.getItems().size()
                + " instruments. Total Market Value: ₹" + totalValue;
    }
}
