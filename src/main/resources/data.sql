INSERT INTO Category (id, name) values ('1', 'Produit frais');
INSERT INTO Category (id, name) values ('2', 'Fruit');
INSERT INTO Category (id, name) values ('3', 'Légume');
INSERT INTO Category (id, name) values ('4', 'Accessoires');
INSERT INTO Category (id, name) values ('5', 'Soupe');

INSERT INTO Article (ean13, price, vat, name, img) values ('1278651251702', 212, 5, '1 Kg Pomme', 'pomme.png');
INSERT INTO Article (ean13, price, vat, name, img) values ('1278651251703', 521, 5, 'Chou rouge bio', 'monImg');
INSERT INTO Article (ean13, price, vat, name, img) values ('1278651251704', 221, 5, 'Soupe veloute de legume', 'monImg');
INSERT INTO Article (ean13, price, vat, name, img) values ('1278651251705', 212, 5, 'Chou blanc', 'monImg');
INSERT INTO Article (ean13, price, vat, name, img) values ('1278651251706', 123, 5, 'Tomate cerise', 'monImg');
INSERT INTO Article (ean13, price, vat, name, img) values ('1278651251707', 321, 5, 'Melon', 'monImg');
INSERT INTO Article (ean13, price, vat, name, img) values ('1278651251708', 1999, 20, 'Casque velo', 'monImg');
INSERT INTO Article (ean13, price, vat, name, img) values ('1278651251709', 1199, 20, 'Reveil', 'monImg');

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
