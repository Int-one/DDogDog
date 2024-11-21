package com.ddogdog.controller;

import com.ddogdog.model.dto.Trade;
import com.ddogdog.service.TradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("api/trade")
@CrossOrigin("*")
public class TradeController {

    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping("")
    public ResponseEntity<List<Trade>> getAllTrades() {
        List<Trade> trades = tradeService.getAllTrades();
        return ResponseEntity.ok(trades);
    }

    @GetMapping("/{tradeId}")
    public ResponseEntity<?> getTradeById(@PathVariable Long tradeId) {
        Trade trade = tradeService.getTradeById(tradeId);
        if (trade == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trade not found");
        }
        return ResponseEntity.ok(trade);
    }

    @PostMapping("")
    public ResponseEntity<Map<String, String>> createTrade(@RequestBody Trade trade) {
        boolean result = tradeService.createTrade(trade);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "Trade created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        response.put("error", "Failed to create trade");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("")
    public ResponseEntity<Map<String, String>> updateTrade(@RequestBody Trade trade) {
        boolean result = tradeService.updateTrade(trade);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "Trade updated successfully");
            return ResponseEntity.ok(response);
        }
        response.put("error", "Failed to update trade");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{tradeId}")
    public ResponseEntity<Map<String, String>> deleteTrade(@PathVariable Long tradeId) {
        boolean result = tradeService.deleteTrade(tradeId);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "Trade deleted successfully");
            return ResponseEntity.ok(response);
        }
        response.put("error", "Failed to delete trade");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
