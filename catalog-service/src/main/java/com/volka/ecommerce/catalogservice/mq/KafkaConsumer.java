package com.volka.ecommerce.catalogservice.mq;

import com.volka.ecommerce.catalogservice.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaConsumer {

    private final CatalogRepository catalogRepository;


}
