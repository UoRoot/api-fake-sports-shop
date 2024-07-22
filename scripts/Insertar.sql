-- Active: 1720499821434@@127.0.0.1@3306@tienda_de_ropa
use api_fake;

INSERT INTO
    categories (`name`)
VALUES ('Ropa'),
    ('Accesorios'),
    ('Calzado');

INSERT INTO
    brands (`brand`)
VALUES ('Adidas'),
    ('Puma'),
    ('Umbro');

INSERT INTO
    colors (`name`)
VALUES ('Rojo'),
    ('Azul'),
    ('Blanco'),
    ('Negro'),
    ('Negro');

INSERT INTO
    sizes (size)
VALUES ('M'),
    ('L'),
    ('42'),
    ('Única');

INSERT INTO
    products (
        `name`,
        `description`,
        price,
        stock_quantity,
        gender,
        brand_id,
        category_id,
        created_date,
        updated_date
    )
VALUES (
        'Camiseta',
        'Camiseta de algodón',
        19.99,
        100,
        'MALE',
        1,
        1,
        NOW(),
        NOW()
    ),
    (
        'Pantalón',
        'Pantalón de mezclilla',
        39.99,
        50,
        'FEMALE',
        2,
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
        2,
        3,
        NOW(),
        NOW()
    ),
    (
        'Chaqueta',
        'Chaqueta de cuero',
        99.99,
        20,
        'FEMALE',
        3,
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
        3,
        2,
        NOW(),
        NOW()
    );

INSERT INTO
    product_color (product_id, color_id)
VALUES (1, 2),
    (1, 5),
    (2, 1),
    (3, 3),
    (4, 4),
    (5, 5);

INSERT INTO
    product_size (product_id, size_id)
VALUES (1, 1),
    (2, 1),
    (2, 2),
    (3, 3),
    (4, 1),
    (5, 4);