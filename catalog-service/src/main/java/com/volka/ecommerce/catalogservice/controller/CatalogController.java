package com.volka.ecommerce.catalogservice.controller;

import com.volka.ecommerce.catalogservice.service.CatalogService;
import com.volka.ecommerce.catalogservice.dto.ResponseCatalog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/catalogs")
@RestController
public class CatalogController {
    private final Environment env;
    private final CatalogService catalogService;

    @GetMapping
    public ResponseEntity<List<ResponseCatalog>> getAll() {
        return ResponseEntity.ok(catalogService.getAll().stream().map(ResponseCatalog::of).toList());
    }
}
