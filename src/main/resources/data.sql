INSERT INTO Category (id, name) values ('1', 'Produit frais');
INSERT INTO Category (id, name) values ('2', 'Fruit');
INSERT INTO Category (id, name) values ('3', 'Légume');
INSERT INTO Category (id, name) values ('4', 'Accessoires');
INSERT INTO Category (id, name) values ('5', 'Soupe');

INSERT INTO Article (ean13, price, vat, name, img, isPerishable) values ('1278651251702', 212, 5, '1 Kg Pomme', 'pomme.png', true);
INSERT INTO Article (ean13, price, vat, name, img, isPerishable) values ('1278651251703', 521, 5, 'Chou rouge bio', 'monImg', true);
INSERT INTO Article (ean13, price, vat, name, img, isPerishable) values ('1278651251704', 221, 5, 'Soupe veloute de legume', 'monImg', true);
INSERT INTO Article (ean13, price, vat, name, img, isPerishable) values ('1278651251705', 212, 5, 'Chou blanc', 'monImg', true);
INSERT INTO Article (ean13, price, vat, name, img, isPerishable) values ('1278651251706', 123, 5, 'Tomate cerise', 'monImg', true);
INSERT INTO Article (ean13, price, vat, name, img, isPerishable) values ('1278651251707', 321, 5, 'Melon', 'monImg', true);
INSERT INTO Article (ean13, price, vat, name, img, isPerishable) values ('1278651251708', 1999, 20, 'Casque velo', 'monImg', false);
INSERT INTO Article (ean13, price, vat, name, img, isPerishable) values ('1278651251709', 1199, 20, 'Reveil', 'monImg', false);

INSERT INTO Article_Category(categories_id, Article_ean13) values ('1', '1278651251702'); -- pomme produit frais
INSERT INTO Article_Category(categories_id, Article_ean13) values ('2', '1278651251702'); -- pomme fruit

INSERT INTO Article_Category(categories_id, Article_ean13) values ('1', '1278651251703'); -- chou rouge bio produit frais
INSERT INTO Article_Category(categories_id, Article_ean13) values ('3', '1278651251703'); -- chou rouge bio legume

INSERT INTO Article_Category(categories_id, Article_ean13) values ('5', '1278651251704'); -- soupe veloute de legume soupe

INSERT INTO Article_Category(categories_id, Article_ean13) values ('1', '1278651251705'); -- chou blanc produit frais
INSERT INTO Article_Category(categories_id, Article_ean13) values ('3', '1278651251705'); -- chou blanc legume


INSERT INTO Article_Category(categories_id, Article_ean13) values ('1', '1278651251706'); -- tomate produit frais
INSERT INTO Article_Category(categories_id, Article_ean13) values ('3', '1278651251706'); -- tomate legume

INSERT INTO Article_Category(categories_id, Article_ean13) values ('1', '1278651251707'); -- melon produit frais
INSERT INTO Article_Category(categories_id, Article_ean13) values ('2', '1278651251707'); -- melon fruit

INSERT INTO Article_Category(categories_id, Article_ean13) values ('4', '1278651251708'); -- casque velo accessoire
INSERT INTO Article_Category(categories_id, Article_ean13) values ('4', '1278651251709'); -- reveil accessoire

INSERT INTO Stock (article_ean13, quantity, bestBefore, lot, DTYPE, id) values ('1278651251706', 10, DATE '2022-12-17', '123', 'Perishable', '4028818a83c80d2c0183c80efe770000'); -- ajout temporaire
INSERT INTO Stock (article_ean13, quantity, bestBefore, lot, DTYPE, id) values ('1278651251706', 12, DATE '2022-11-12', '124', 'Perishable', '4028818a83c80d2c0183c80efe770012'); -- ajout temporaire
INSERT INTO Stock (article_ean13, quantity, bestBefore, lot, DTYPE, id) values ('1278651251707', 5, DATE '2022-10-10', '451', 'Perishable', '4028818a83c80d2c0183c80efe770023'); -- ajout temporaire
INSERT INTO Stock (article_ean13, quantity, bestBefore, lot, DTYPE, id) values ('1278651251707', 25, DATE '2022-09-17', '452', 'Perishable', '4028818a83c80d2c0183c80efe770034'); -- ajout temporaire
INSERT INTO Stock (article_ean13, quantity, bestBefore, lot, DTYPE, id) values ('1278651251706', 5, DATE '2022-10-10', '451', 'Perishable', '4028818a83c80d2c0183c80efe770025'); -- ajout temporaire
INSERT INTO Stock (article_ean13, quantity, bestBefore, lot, DTYPE, id) values ('1278651251706', 25, DATE '2022-09-17', '452', 'Perishable', '4028818a83c80d2c0183c80efe770037'); -- ajout temporaire