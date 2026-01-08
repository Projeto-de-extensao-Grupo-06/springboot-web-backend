--------------------------------------------
-- CLIENTS
--------------------------------------------
INSERT INTO client (first_name, last_name, phone, email, status, document_number, document_type, created_at, updated_at)
VALUES
('João', 'da Silva', '11999999999', 'joao@email.com', 'ACTIVE', '12345678900', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Maria', 'Souza', '11888888888', 'maria@email.com', 'ACTIVE', '98765432100', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Carlos', 'Pereira', '11777777777', 'carlos@email.com', 'INACTIVE', '11122233344', 'CNPJ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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
-- INSERT INTO budget (
--     total_cost,
--     discount,
--     final_budget
-- ) VALUES (
--     15000.00,
--     0.00,
--     TRUE
-- );

--------------------------------------------
-- MATERIALS
--------------------------------------------
INSERT INTO material(name, metric, hidden)
VALUES
    ('Painel Solar 550W', 'unit', false),
    ('Inversor On-Grid 5kW', 'unit', false),
    ('Cabo Solar 6mm', 'meter', false),
    ('Bateria 5kWh', 'unit', false);

--------------------------------------------
-- MATERIAL URL
--------------------------------------------
INSERT INTO material_url (url, fk_material, price, hidden)
VALUES
    ( 'https://solarcenter.com/fichas/painel550w.pdf', 1, 1500.0, false),
    ( 'https://painelforte.com.br/manual/inversor5kw.pdf', 2, 2042.5, false),
    ('https://solarcenter.com/fichas/cabo6mm.pdf', 3, 500.0, false),
    ('https://ecosolar.com.br/docs/bateria5kwh.pdf', 4, 500.0, false);

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

--------------------------------------------
-- PROJECTS
--------------------------------------------
INSERT INTO project (
    status,
    status_weight,
    preview_status,
    is_active,
    fk_client,
    fk_responsible,
    fk_address,
    created_at,
    deadline,
    system_type,
    project_from,
    name,
    description
) VALUES
('NEW', 3, NULL, TRUE, 1, 1, 1, NOW(), DATEADD('DAY', 10, NOW()), 'ON_GRID', 'INTERNAL_MANUAL_ENTRY', 'Projeto Solar — Cliente João', 'Projeto de instalação 5kWp');
