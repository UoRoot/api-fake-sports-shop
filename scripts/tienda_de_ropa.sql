CREATE DATABASE IF NOT EXISTS tienda_de_ropa;

USE tienda_de_ropa;

CREATE TABLE IF NOT EXISTS colores (
    `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS tallas (
    `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
    talla VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS categorias (
    `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL
);

-- Crear la tabla de clientes
CREATE TABLE IF NOT EXISTS clientes (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    direccion VARCHAR(255),
    codigo_postal VARCHAR(10),
    correo_electronico VARCHAR(255) UNIQUE NOT NULL,
    telefono VARCHAR(15),
    created_date DATETIME,
    updated_date DATETIME
);

-- Crear la tabla de productos
CREATE TABLE IF NOT EXISTS productos (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL CHECK (precio >= 0),
    cantidad_stock INTEGER NOT NULL CHECK (cantidad_stock >= 0),
    genero ENUM(
        "MASCULINO",
        "FEMENINO",
        "UNISEX"
    ) NOT NULL,
    categoria_id INTEGER,
    created_date DATETIME,
    updated_date DATETIME,
    FOREIGN KEY (categoria_id) REFERENCES categorias (`id`)
);

-- CREATE INDEX idx_genero ON productos (genero);

-- Crear la tabla de ventas
CREATE TABLE IF NOT EXISTS ventas (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    cliente_id BIGINT NOT NULL,
    fecha_venta DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_venta DECIMAL(10, 2) NOT NULL CHECK (total_venta >= 0),
    created_date DATETIME,
    updated_date DATETIME,
    FOREIGN KEY (cliente_id) REFERENCES clientes (`id`)
);

-- Crear la tabla de detalles de la venta
CREATE TABLE IF NOT EXISTS detalles_venta (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    venta_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad_vendida INTEGER NOT NULL CHECK (cantidad_vendida > 0),
    precio_unitario DECIMAL(10, 2) NOT NULL CHECK (precio_unitario >= 0),
    created_date DATETIME,
    updated_date DATETIME,
    FOREIGN KEY (venta_id) REFERENCES ventas (`id`),
    FOREIGN KEY (producto_id) REFERENCES productos (`id`)
);

-- Tabla intermedia producto_color
CREATE TABLE IF NOT EXISTS producto_color (
    producto_id BIGINT NOT NULL,
    color_id INTEGER NOT NULL,
    PRIMARY KEY (producto_id, color_id),
    FOREIGN KEY (producto_id) REFERENCES productos (`id`) ON DELETE CASCADE,
    FOREIGN KEY (color_id) REFERENCES colores (`id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS producto_talla (
    producto_id BIGINT NOT NULL,
    talla_id INTEGER NOT NULL,
    PRIMARY KEY (producto_id, talla_id),
    FOREIGN KEY (producto_id) REFERENCES productos (`id`) ON DELETE CASCADE,
    FOREIGN KEY (talla_id) REFERENCES tallas (`id`) ON DELETE CASCADE
);
-- DROP DATABASE tienda_de_ropa;