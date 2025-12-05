--------------------------------------------
-- CLIENTS
--------------------------------------------
INSERT INTO client (first_name, last_name, phone, email)
VALUES
    ('João', 'da Silva', '11999999999', 'joao@email.com'),
    ('Maria', 'Souza', '11888888888', 'maria@email.com'),
    ('Carlos', 'Pereira', '11777777777', 'carlos@email.com');

--------------------------------------------
-- PERMISSION GROUP
--------------------------------------------
INSERT INTO permission_group (
    role,
    main_module,
    access_client,
    access_project,
    access_budget,
    access_schedule
) VALUES (
             'ADMIN',
             'PROJECT_LIST',
             0xF,
             0xF,
             0xF,
             0xF
         );

--------------------------------------------
-- COWORKER (RESPONSÁVEL)
--------------------------------------------
INSERT INTO coworker (
    first_name,
    last_name,
    email,
    phone,
    password,
    fk_permission_group,
    is_active
) VALUES (
             'Bryan',
             'Rocha',
             'bryangomesrocha@gmail.com',
             '11964275054',
             '$2a$12$dUlemf8rtZhoMu/nH.5XtOmerR.uxfLp5vmVbYVrzduguD.d/jhWG',
             1,
             TRUE
         );

--------------------------------------------
-- BUDGET
--------------------------------------------
INSERT INTO budget (
    total_cost,
    discount,
    material_cost,
    service_cost,
    final_budget
) VALUES (
             15000.00,
             0.00,
             7000.00,
             8000.00,
             TRUE
         );

--------------------------------------------
-- MATERIALS
--------------------------------------------
INSERT INTO material_catalog (name, metric, price)
VALUES
    ('Painel Solar 550W', 'unit', 900.0),
    ('Inversor On-Grid 5kW', 'unit', 349.99),
    ('Cabo Solar 6mm', 'meter', 100.0),
    ('Bateria 5kWh', 'unit', 1500.0);

--------------------------------------------
-- MATERIAL URL
--------------------------------------------
INSERT INTO material_url (description, url, fk_material)
VALUES
    ('Ficha técnica Painel 550W', 'https://solarcenter.com/fichas/painel550w.pdf', 1),
    ('Manual Inversor 5kW', 'https://painelforte.com.br/manual/inversor5kw.pdf', 2),
    ('Ficha técnica Cabo 6mm', 'https://solarcenter.com/fichas/cabo6mm.pdf', 3),
    ('Ficha técnica Bateria 5kWh', 'https://ecosolar.com.br/docs/bateria5kwh.pdf', 4);

--------------------------------------------
-- ADDRESS
--------------------------------------------
INSERT INTO address (
    postal_code,
    street_name,
    number,
    neighborhood,
    city,
    state,
    type
) VALUES (
             '01001-000',
             'Rua das Palmeiras',
             '123',
             'Centro',
             'São Paulo',
             'SP',
             'RESIDENTIAL'
         );

