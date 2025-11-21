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

INSERT INTO coworker (first_name, last_name, email, phone, password, fk_permission_group, is_active)
VALUES (
        'Bryan',
        'Rocha',
        'bryangomesrocha@gmail.com',
        '11964275054',
        '$2a$12$dUlemf8rtZhoMu/nH.5XtOmerR.uxfLp5vmVbYVrzduguD.d/jhWG',
        1,
        TRUE
       );

INSERT INTO material_catalog (name, metric, price)
VALUES
    ('Painel Solar 550W', 'unit', 900.0),
    ('Inversor On-Grid 5kW', 'unit', 349.99),
    ('Cabo Solar 6mm', 'meter', 100.0),
    ('Bateria 5kWh', 'unit', 1500.0);

INSERT INTO material_url (description, url, fk_material)
VALUES
    ('Ficha técnica Painel 550W', 'https://solarcenter.com/fichas/painel550w.pdf', 1),
    ('Manual Inversor 5kW', 'https://painelforte.com.br/manual/inversor5kw.pdf', 2),
    ('Ficha técnica Cabo 6mm', 'https://solarcenter.com/fichas/cabo6mm.pdf', 3),
    ('Ficha técnica Bateria 5kWh', 'https://ecosolar.com.br/docs/bateria5kwh.pdf', 4);


INSERT INTO client (id_client, first_name, email, phone)
VALUES (1, 'Cliente Teste', 'cliente@teste.com', '11999999999');

INSERT INTO address (id_address, street_name, number, city, state, postal_code)
VALUES (1, 'Rua Teste', '123', 'São Paulo', 'SP', '00000000');

INSERT INTO budget (id_budget, total_cost)
VALUES (1, 1000.00);

INSERT INTO project (
    id_project,
    status,
    status_weight,
    preview_status,
    is_active,
    fk_client,
    fk_budget,
    fk_address,
    created_at,
    deadline,
    system_type,
    project_from,
    name,
    description
) VALUES (
             1,
             'AWAITING_RETRY',
             1,
             'PRE_BUDGET',
             TRUE,
             1,
             1,
             1,
             NOW(),
             NULL,
             'OFF_GRID',       -- ajuste conforme seu enum
             'BOT',      -- ajuste conforme seu enum
             'Projeto Teste',
             'Projeto criado para testar o Queue Processor'
         );


INSERT INTO retry_queue (
    id,
    scheduled_date,
    retrying,
    fk_project
) VALUES (
             1,
             NOW(),      -- para disparar imediatamente
             FALSE,
             1           -- project id
         );
