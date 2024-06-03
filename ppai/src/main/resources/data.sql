-- Insert sample data into RegionVitivinicola
INSERT INTO region_vitivinicola (descripcion, nombre, provincia_id) VALUES 
('Región vitivinícola de Mendoza', 'Mendoza Central', 1), 
('Región vitivinícola de San Juan', 'San Juan Norte', 2),
('Región vitivinícola del Valle Central', 'Valle Central', 3);

-- Insert sample data into Bodega
INSERT INTO bodega (descripcion, historia, nombre, coordenadas_ubicacion, periodo_actualizacion, region_id) VALUES 
('Bodega histórica de Mendoza', 'Historia de la bodega de Mendoza', 'Bodega Mendoza', 12345, 'Anual', 1),
('Bodega histórica de San Juan', 'Historia de la bodega de San Juan', 'Bodega San Juan', 67890, 'Anual', 2);

-- Insert sample data into Varietal
INSERT INTO varietal (descripcion, porcentaje_composicion) VALUES 
('Malbec', 100),
('Cabernet Sauvignon', 100);

-- Insert sample data into Vino
INSERT INTO vino (nombre, aniada, fecha_actualizacion, imagen, precio, nota_de_cata_de_bodega, bodega_id, varietal_id) VALUES 
('Vino Malbec', '2022-01-01', '2024-06-01', 'imagen1.jpg', 20.5, 4.5, 1, 1),
('Vino Cabernet', '2021-01-01', '2024-06-01', 'imagen2.jpg', 25.0, 4.7, 2, 2);

-- Insert sample data into Resena
INSERT INTO resena (vino_id, comentario, es_premium, fecha_resenia, puntaje) VALUES 
(1, 'Excelente vino Malbec', true, '2024-06-01', 5),
(2, 'Muy buen Cabernet Sauvignon', false, '2024-06-02', 4);

-- Insert sample data into Sommelier
INSERT INTO sommelier (nombre) VALUES 
('John Doe'),
('Jane Doe');

-- Insert more sample data into Varietal
INSERT INTO varietal (descripcion, porcentaje_composicion) VALUES 
('Malbec', 100),
('Cabernet Sauvignon', 100),
('Chardonnay', 100),
('Merlot', 100),
('Syrah', 100),
('Pinot Noir', 100),
('Sauvignon Blanc', 100),
('Tempranillo', 100),
('Zinfandel', 100),
('Grenache', 100);

-- Insert more sample data into Vino
INSERT INTO vino (nombre, aniada, fecha_actualizacion, imagen, precio, nota_de_cata_de_bodega, bodega_id, varietal_id) VALUES 
('Vino Malbec', '2022-01-01', '2024-06-01', 'imagen1.jpg', 20.5, 4.5, 1, 1),
('Vino Cabernet', '2021-01-01', '2024-06-01', 'imagen2.jpg', 25.0, 4.7, 2, 2),
('Vino Chardonnay', '2020-01-01', '2024-06-01', 'imagen3.jpg', 18.0, 4.6, 1, 3),
('Vino Merlot', '2019-01-01', '2024-06-01', 'imagen4.jpg', 22.0, 4.8, 2, 4),
('Vino Syrah', '2022-01-01', '2024-06-01', 'imagen5.jpg', 19.5, 4.7, 3, 5),
('Vino Pinot Noir', '2021-01-01', '2024-06-01', 'imagen6.jpg', 26.0, 4.9, 1, 6),
('Vino Sauvignon Blanc', '2020-01-01', '2024-06-01', 'imagen7.jpg', 23.5, 4.6, 2, 7),
('Vino Tempranillo', '2019-01-01', '2024-06-01', 'imagen8.jpg', 21.0, 4.8, 3, 8),
('Vino Zinfandel', '2022-01-01', '2024-06-01', 'imagen9.jpg', 24.5, 4.7, 1, 9),
('Vino Grenache', '2021-01-01', '2024-06-01', 'imagen10.jpg', 27.0, 4.9, 2, 10);

-- Insert more sample data into Resena
INSERT INTO resena (vino_id, comentario, es_premium, fecha_resenia, puntaje) VALUES 
(1, 'Excelente vino Malbec', true, '2024-06-01', 5),
(1, 'Buen vino Malbec', false, '2024-06-02', 4),
(1, 'Regular vino Malbec', true, '2024-06-03', 3),
(2, 'Muy buen Cabernet Sauvignon', true, '2024-06-01', 5),
(2, 'Buen Cabernet Sauvignon', false, '2024-06-02', 4),
(3, 'Excelente Chardonnay', true, '2024-06-01', 5),
(3, 'Buen Chardonnay', false, '2024-06-02', 4),
(4, 'Excelente Merlot', true, '2024-06-01', 5),
(4, 'Buen Merlot', false, '2024-06-02', 4),
(5, 'Excelente Syrah', true, '2024-06-01', 5),
(5, 'Buen Syrah', false, '2024-06-02', 4),
(6, 'Excelente Pinot Noir', true, '2024-06-01', 5),
(6, 'Buen Pinot Noir', false, '2024-06-02', 4),
(7, 'Excelente Sauvignon Blanc', true, '2024-06-01', 5),
(7, 'Buen Sauvignon Blanc', false, '2024-06-02', 4),
(8, 'Excelente Tempranillo', true, '2024-06-01', 5),
(8, 'Buen Tempranillo', false, '2024-06-02', 4),
(9, 'Excelente Zinfandel', true, '2024-06-01', 5),
(9, 'Buen Zinfandel', false, '2024-06-02', 4),
(10, 'Excelente Grenache', true, '2024-06-01', 5),
(10, 'Buen Grenache', false, '2024-06-02', 4);

-- Insert sample data into Sommelier
INSERT INTO sommelier (nombre) VALUES 
('John Doe'),
('Jane Doe');
