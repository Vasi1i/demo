package com.example.demo.controller;

import com.example.demo.model.db.CpeEntity;
import com.example.demo.repository.CpeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class Controller {

    private final CpeRepository cpeRepository;

    public Controller(CpeRepository cpeRepository) {
        this.cpeRepository = cpeRepository;
    }

    @GetMapping("/uuid/")
    public ResponseEntity<CpeEntity> findById(@RequestParam String uuid) {
        try {
            UUID cpeUuid = UUID.fromString(uuid);
            Optional<CpeEntity> cpeEntityOptional = cpeRepository.findById(cpeUuid);
            if (cpeEntityOptional.isPresent()) {
                CpeEntity cpeEntity = cpeEntityOptional.get();
                return new ResponseEntity<>(cpeEntity, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/uuids/")
    public ResponseEntity<List<CpeEntity>> findAllById(@RequestParam List<String> uuids) {
        try {
            List<UUID> uuidList = uuids.stream()
                    .map(UUID::fromString)
                    .toList();
            List<CpeEntity> cpeEntities = cpeRepository.findAllById(uuidList);
            if (!cpeEntities.isEmpty()) {
                return new ResponseEntity<>(cpeEntities, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/names/")
    public ResponseEntity<List<CpeEntity>> findByCpeNames(@RequestParam List<String> names) {
        try {
            List<CpeEntity> cpeEntities = cpeRepository.findByCpeNameIn(names);
            if (!cpeEntities.isEmpty()) {
                return new ResponseEntity<>(cpeEntities, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/partial/")
    public Page<CpeEntity> findByCpeNameContaining(
            @RequestParam("cpeName") String cpeName,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        PageRequest pageable = PageRequest.of(page, pageSize);
        return cpeRepository.findByCpeNameContaining(cpeName, pageable);
    }

}
