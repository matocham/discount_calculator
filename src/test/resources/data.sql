delete from discount_policies;
delete from products;

insert into products(id, name, unit_price)
values ('1107c751-820d-423d-b100-3a3890ae2b3d', 'Wireless Mouse', 30),
       ('77b69433-d28e-4339-bd92-1c67c08d1be8', 'Mechanical Keyboard', 90),
       ('ab092fb5-0f36-4f18-9a3e-b51c6495431d', 'USB-C Cable', 12.5),
       ('7f13e0ed-77fa-451a-9420-f90c31560d2a', 'Laptop Stand', 35),
       ('da4f892e-8262-45f2-a432-144ea3d8e5b2', 'External SSD 1TB', 120),
       ('d2e626a7-e5d2-4453-8b63-5e89d77a6b49', 'Bluetooth Headphones', 75),
       ('e5143789-a9c7-4546-9ab7-d53d4d2defdb', 'Webcam HD', 50),
       ('1945f2a3-47cf-421f-bb21-fe213de7a484', 'Gaming Mouse Pad', 20),
       ('e638a1e3-d97d-4223-b415-d2dc2c348bbc', 'USB Hub', 25),
       ('c0d725af-96c5-45f6-86b8-43a45fe2836c', 'Portable Charger', 40);

insert into discount_policies(id, discount_value, minimum_product_amount, discount_type, product_id)
values (101, 10, 5, 'ABSOLUTE', '1107c751-820d-423d-b100-3a3890ae2b3d'),
       (102, 15, 8, 'ABSOLUTE', '1107c751-820d-423d-b100-3a3890ae2b3d'),
       (201, 15, 3, 'PERCENTAGE', '77b69433-d28e-4339-bd92-1c67c08d1be8'),
       (301, 5, 2, 'PERCENTAGE', 'e638a1e3-d97d-4223-b415-d2dc2c348bbc'),
       (302, 5, 2, 'ABSOLUTE', 'e638a1e3-d97d-4223-b415-d2dc2c348bbc');










