package com.example.data.controller;

import com.example.data.exception.ResourceNotFoundException;
import com.example.data.model.Data;
import com.example.data.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {

    @Autowired
    DataRepository dataRepository;

    @GetMapping("/data")
    public List<Data> getAllNotes() {
        return dataRepository.findAll();
    }

    @PostMapping("/data")
    public Data createNote(@Valid @RequestBody Data data) {
        return dataRepository.save(data);
    }

    @GetMapping("/data/{id}")
    public Data getDataById(@PathVariable(value = "id") Long dataId) {
        return dataRepository.findById(dataId)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "id", dataId));
    }

    @PutMapping("/data/{id}")
    public Data updateData(@PathVariable(value = "id") Long dataId,
                           @Valid @RequestBody Data dataDetails) {

        Data data = dataRepository.findById(dataId)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "id", dataId));

        data.setTitle(dataDetails.getTitle());
        data.setContent(dataDetails.getContent());

        Data updatedData = dataRepository.save(data);
        return updatedData;
    }

    @DeleteMapping("/data/{id}")
    public ResponseEntity<?> deleteData(@PathVariable(value = "id") Long dataId) {
        Data data = dataRepository.findById(dataId)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "id", dataId));

        dataRepository.delete(data);

        return ResponseEntity.ok().build();
    }
}
