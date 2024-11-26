package com.ddogdog.controller;

import com.ddogdog.model.dto.Pet;
import com.ddogdog.model.dto.Together;
import com.ddogdog.model.dto.Trade;
import com.ddogdog.service.TogetherService;
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
    private final TogetherService togetherService;

    public TradeController(TradeService tradeService, TogetherService togetherService) {
        this.tradeService = tradeService;
        this.togetherService = togetherService;
    }

    @GetMapping("")
    public ResponseEntity<List<Trade>> getAllTrades() {
        List<Trade> trades = tradeService.getAllTrades();
        return ResponseEntity.ok(trades);
    }
    
    @GetMapping("/will")
    public ResponseEntity<List<Trade>> getAllWillTrades() {
        List<Trade> trades = tradeService.getAllTrades();
        return ResponseEntity.ok(trades);
    }
    
    @GetMapping("/please/{region}")
    public ResponseEntity<?> getAllPlease(@PathVariable String region) {
    	List<Map<String, Object>> result = tradeService.getAllPleaseTrades(region);
    	if (result == null) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trade not found");
    	}
    	return ResponseEntity.ok(result);
    }
    
    @GetMapping("/pets/{tradeId}")
    public ResponseEntity<?> getPetsByTradeId(@PathVariable Long tradeId) {
    	List<Pet> pets = tradeService.getPetsByTradeId(tradeId);
    	return ResponseEntity.ok(pets);
    }

    @GetMapping("/{tradeId}")
    public ResponseEntity<?> getTradeById(@PathVariable Long tradeId) {
        Trade trade = tradeService.getTradeById(tradeId);
        if (trade == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trade not found");
        }
        return ResponseEntity.ok(trade);
    }
    
    @GetMapping("/cand/{candId}")
    public ResponseEntity<?> getTradesByCandId(@PathVariable String candId) {
    	List<Trade> trades = tradeService.getTradesByCandId(candId);
    	return ResponseEntity.ok(trades);
    }

    @PostMapping("")
    public ResponseEntity<Map<String, String>> createTrade(@RequestBody Trade trade) {
        boolean result = tradeService.createTrade(trade);
        Trade curr = tradeService.getLatestTradeById(trade);
        if(trade.getPets() != null)
	        for(Integer petId : trade.getPets()) {
	        	Together together = new Together();
	        	together.setTradeId(curr.getTradeId());
	        	together.setPetId(petId);
	        	togetherService.createTogether(together);
	        }
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
