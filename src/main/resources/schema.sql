--------------------------------------------
-- CLIENTS
--------------------------------------------
INSERT INTO client (first_name, last_name, phone, email, status, document_number, document_type, created_at, updated_at)
VALUES
('João', 'da Silva', '11999999999', 'joao@email.com', 'ACTIVE', '12345678900', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Maria', 'Souza', '11888888888', 'maria@email.com', 'ACTIVE', '98765432100', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Carlos', 'Pereira', '11777777777', 'carlos@email.com', 'INACTIVE', '11122233344', 'CNPJ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Ana', 'Oliveira', '11912345678', 'ana.oliveira@email.com', 'ACTIVE', '44455566677', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Ricardo', 'Santos', '21987654321', 'ricardo.santos@email.com', 'ACTIVE', '88899900011', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Luciana', 'Mendes', '31998877665', 'luciana.mendes@email.com', 'INACTIVE', '22233344455', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Marcos', 'Almeida', '1133445566', 'contato@almeidame.com', 'ACTIVE', '12345678000199', 'CNPJ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Fernanda', 'Costa', '41995544332', 'fer.costa@email.com', 'ACTIVE', '77788899922', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Roberto', 'Ferreira', '51988772211', 'roberto.f@email.com', 'ACTIVE', '55566677788', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Juliana', 'Lima', '61991122334', 'juliana.lima@email.com', 'ACTIVE', '99900011122', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Bruno', 'Rocha', '1144556677', 'financeiro@rochacorp.com', 'ACTIVE', '98765432000188', 'CNPJ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Camila', 'Barbosa', '71992233445', 'camila.b@email.com', 'INACTIVE', '33344455566', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tiago', 'Nunes', '81987651234', 'tiago.nunes@email.com', 'ACTIVE', '11100099988', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Gabriel', 'Duarte', '11911112222', 'gabriel.duarte@email.com', 'ACTIVE', '10120230344', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Beatriz', 'Pinto', '21922223333', 'beatriz.p@email.com', 'ACTIVE', '50560670788', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('André', 'Teixeira', '31933334444', 'andre.tex@email.com', 'INACTIVE', '90980870766', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Larissa', 'Cavalcanti', '41944445555', 'lari.cav@email.com', 'ACTIVE', '11223344000155', 'CNPJ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Felipe', 'Cardoso', '51955556666', 'felipe.c@email.com', 'ACTIVE', '30340450599', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Patrícia', 'Gomes', '61966667777', 'patri.gomes@email.com', 'ACTIVE', '60670780811', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Henrique', 'Souza', '71977778888', 'h.souza@email.com', 'ACTIVE', '80890910122', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Daniela', 'Moreira', '81988889999', 'dani.moreira@email.com', 'INACTIVE', '44332211000100', 'CNPJ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Leonardo', 'Freitas', '11912123434', 'leo.freitas@email.com', 'ACTIVE', '12132343455', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Vanessa', 'Lopes', '21923234545', 'vanessa.l@email.com', 'ACTIVE', '54565676788', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Gustavo', 'Batista', '31934345656', 'gustavo.b@email.com', 'ACTIVE', '98978767655', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Letícia', 'Assis', '41945456767', 'leticia.assis@email.com', 'ACTIVE', '10190980877', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Sérgio', 'Marques', '51956567878', 'sergio.m@email.com', 'ACTIVE', '55443322000111', 'CNPJ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Aline', 'Vieira', '61967678989', 'aline.v@email.com', 'INACTIVE', '70780890933', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Maurício', 'Moraes', '71978789090', 'mau.moraes@email.com', 'ACTIVE', '20230340455', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tatiane', 'Ribeiro', '81989890101', 'tati.rib@email.com', 'ACTIVE', '40450560677', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Renato', 'Carvalho', '11990901212', 'renato.c@email.com', 'ACTIVE', '80870760644', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Sabrina', 'Gonçalves', '21901012323', 'sabrina.g@email.com', 'ACTIVE', '66554433000122', 'CNPJ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Igor', 'Fernandes', '31912123434', 'igor.f@email.com', 'ACTIVE', '90910120233', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Priscila', 'Monteiro', '41923234545', 'pri.monteiro@email.com', 'ACTIVE', '30320210144', 'CPF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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
