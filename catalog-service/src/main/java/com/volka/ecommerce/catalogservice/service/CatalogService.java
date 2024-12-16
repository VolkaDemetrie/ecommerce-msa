package com.volka.ecommerce.catalogservice.service;

import com.volka.ecommerce.catalogservice.repository.CatalogRepository;
import com.volka.ecommerce.catalogservice.dto.CatalogDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CatalogService {

    private final CatalogRepository catalogRepository;

    public List<CatalogDto> getAll() {
        return catalogRepository.findAll().stream().map(CatalogDto::of).toList();
    }
}
