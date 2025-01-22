-- insert into catalogs(product_id, product_name, stock, unit_price) values('CATALOG-0001', 'Berlin', 100, 1500);
-- insert into catalogs(product_id, product_name, stock, unit_price) values('CATALOG-0002', 'Tokyo', 100, 900);
-- insert into catalogs(product_id, product_name, stock, unit_price) values('CATALOG-0003', 'Stockholm', 100, 1200);


INSERT INTO ecommerce.catalogs (stock, unit_price, created_at, product_id, product_name) VALUES (100, 1500, '2025-01-22 22:12:09.000000', 'CATALOG-0001', 'Berlin');
INSERT INTO ecommerce.catalogs (stock, unit_price, created_at, product_id, product_name) VALUES (100, 900, '2025-01-22 22:12:09.000000', 'CATALOG-0002', 'Tokyo');
INSERT INTO ecommerce.catalogs (stock, unit_price, created_at, product_id, product_name) VALUES (100, 1200, '2025-01-22 22:12:09.000000', 'CATALOG-0003', 'Stockholm');