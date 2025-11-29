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
    final_budget
) VALUES (
    15000.00,
    0.00,
    TRUE
);

--------------------------------------------
-- MATERIALS
--------------------------------------------
INSERT INTO material(name, metric)
VALUES
('Painel Solar 550W', 'unit'),
('Inversor On-Grid 5kW', 'unit'),
('Cabo Solar 6mm', 'meter'),
('Bateria 5kWh', 'unit');

--------------------------------------------
-- MATERIAL URL
--------------------------------------------
INSERT INTO material_url (url, fk_material, price)
VALUES
( 'https://solarcenter.com/fichas/painel550w.pdf', 1, 1500.0),
( 'https://painelforte.com.br/manual/inversor5kw.pdf', 2, 2042.5),
('https://solarcenter.com/fichas/cabo6mm.pdf', 3, 500.),
('https://ecosolar.com.br/docs/bateria5kwh.pdf', 4, 500.0);

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
    fk_budget,
    fk_address,
    created_at,
    deadline,
    system_type,
    project_from,
    name,
    description
) VALUES
('NEW', 3, NULL, TRUE, 1, 1, 1, 1, NOW(), DATEADD('DAY', 10, NOW()), 'ON_GRID', 'SITE', 'Projeto Solar — Cliente João', 'Projeto de instalação 5kWp'),
('PRE_BUDGET', 4, NULL, TRUE, 2, 1, 1, 1, NOW(), DATEADD('DAY', 20, NOW()), 'OFF_GRID', 'SITE', 'Projeto Solar — Cliente Maria', 'Sistema híbrido 3kWp'),
('COMPLETED', 13, NULL, TRUE, 3, 1, 1, 1, NOW(), DATEADD('DAY', 30, NOW()), 'ON_GRID', 'BOT', 'Projeto Solar — Cliente Carlos', 'Instalação residencial padrão');
