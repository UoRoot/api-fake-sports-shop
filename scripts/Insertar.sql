-- Active: 1720499821434@@127.0.0.1@3306@tienda_de_ropa
use tienda_de_ropa;

INSERT INTO
    categorias (nombre)
VALUES ('Ropa'),
    ('Accesorios'),
    ('Calzado');

INSERT INTO
    colores (nombre)
VALUES ('Rojo'),
    ('Azul'),
    ('Blanco'),
    ('Negro'),
    ('Negro');

INSERT INTO
    tallas (talla)
VALUES ('M'),
    ('L'),
    ('42'),
    ('Única');

INSERT INTO
    clientes (
        nombre,
        apellido,
        direccion,
        codigo_postal,
        correo_electronico,
        telefono,
        created_date,
        updated_date
    )
VALUES (
        'Juan',
        'Pérez',
        'Calle Falsa 123',
        '28080',
        'juan.perez@example.com',
        '600123456',
        NOW(),
        NOW()
    ),
    (
        'Ana',
        'Gómez',
        'Avenida Siempre Viva 742',
        '28010',
        'ana.gomez@example.com',
        '601234567',
        NOW(),
        NOW()
    ),
    (
        'Luis',
        'Martínez',
        'Calle Real 456',
        '28020',
        'luis.martinez@example.com',
        '602345678',
        NOW(),
        NOW()
    ),
    (
        'Marta',
        'Rodríguez',
        'Calle Mayor 789',
        '28030',
        'marta.rodriguez@example.com',
        '603456789',
        NOW(),
        NOW()
    ),
    (
        'Carlos',
        'López',
        'Plaza Mayor 321',
        '28040',
        'carlos.lopez@example.com',
        '604567890',
        NOW(),
        NOW()
    );

INSERT INTO
    productos (
        nombre,
        descripcion,
        precio,
        cantidad_stock,
        genero,
        categoria_id,
        created_date,
        updated_date
    )
VALUES (
        'Camiseta',
        'Camiseta de algodón',
        19.99,
        100,
        'MASCULINO',
        1,
        NOW(),
        NOW()
    ),
    (
        'Pantalón',
        'Pantalón de mezclilla',
        39.99,
        50,
        'FEMENINO',
        1,
        NOW(),
        NOW()
    ),
    (
        'Zapatillas',
        'Zapatillas deportivas',
        59.99,
        30,
        'UNISEX',
        3,
        NOW(),
        NOW()
    ),
    (
        'Chaqueta',
        'Chaqueta de cuero',
        99.99,
        20,
        'FEMENINO',
        1,
        NOW(),
        NOW()
    ),
    (
        'Gorra',
        'Gorra ajustable',
        14.99,
        150,
        'UNISEX',
        2,
        NOW(),
        NOW()
    );

INSERT INTO
    ventas (
        cliente_id,
        total_venta,
        created_date,
        updated_date
    )
VALUES (1, 59.99, NOW(), NOW()),
    (2, 99.99, NOW(), NOW()),
    (3, 39.99, NOW(), NOW()),
    (4, 19.99, NOW(), NOW()),
    (5, 14.99, NOW(), NOW());

INSERT INTO
    detalles_venta (
        venta_id,
        producto_id,
        cantidad_vendida,
        precio_unitario,
        created_date,
        updated_date
    )
VALUES (1, 1, 2, 19.99, NOW(), NOW()),
    (2, 2, 1, 99.99, NOW(), NOW()),
    (3, 3, 1, 39.99, NOW(), NOW()),
    (4, 1, 1, 19.99, NOW(), NOW()),
    (5, 5, 1, 14.99, NOW(), NOW());

INSERT INTO
    producto_color (producto_id, color_id)
VALUES (1, 2),
    (1, 5),
    (2, 1),
    (3, 3),
    (4, 4),
    (5, 5);

INSERT INTO
    producto_talla (producto_id, talla_id)
VALUES (1, 1),
    (2, 1),
    (2, 2),
    (3, 3),
    (4, 1),
    (5, 4);

-- ('M'), ('L'), ('42'), ('Única');