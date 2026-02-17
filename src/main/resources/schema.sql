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
) VALUES
      (
         'ADMIN',
         'PROJECT_LIST',
         0xF,
         0xF,
         0xF,
         0xF
      ),
      (
          'SECRETARIA',
          'CLIENT_LIST',
          0x3,
          0x1,
          0x0,
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
) VALUES
    (
        'Bryan',
        'Rocha',
        'bryangomesrocha@gmail.com',
        '11964275054',
        '$2a$12$dUlemf8rtZhoMu/nH.5XtOmerR.uxfLp5vmVbYVrzduguD.d/jhWG',
        1,
        TRUE
    ),
    (
        'Ranier',
        'Dalton',
        'ranierd.couto@gmail.com',
        '11949902159',
        '$2a$12$dUlemf8rtZhoMu/nH.5XtOmerR.uxfLp5vmVbYVrzduguD.d/jhWG',
        2,
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
INSERT INTO project (status, status_weight, preview_status, is_active, fk_client, fk_responsible, fk_address, created_at, deadline, system_type, project_from, name, description)
VALUES
    ('NEW', 3, NULL, TRUE, 1, 1, 1, NOW(), DATEADD('DAY', 10, NOW()), 'ON_GRID', 'WHATSAPP_BOT', 'Projeto Padrão', 'Instalação simples'),
    -- 2. [LEAD QUENTE 1] - Origem BOT (Deve aparecer no TOPO)
    ('CLIENT_AWAITING_CONTACT', 1, 'NEW', TRUE, 2, 1, 1, DATEADD('HOUR', -2, NOW()), DATEADD('DAY', 10, NOW()), 'ON_GRID', 'WHATSAPP_BOT', 'Lead Urgente Bot', 'Cliente interagiu com o bot e quer falar.'),
    -- 3. [LEAD QUENTE 2] - Origem SITE (Deve aparecer no TOPO)
    ('CLIENT_AWAITING_CONTACT', 1, 'NEW', TRUE, 3, 1, 1, DATEADD('HOUR', -5, NOW()), DATEADD('DAY', 10, NOW()), 'OFF_GRID', 'SITE_BUDGET_FORM', 'Lead Urgente Site', 'Formulário de contato do site.'),
    -- 4. [LEAD FRIO - MADURO] - Deve aparecer (Agendado para ONTEM)
    ('AWAITING_RETRY', 11, 'NEGOTIATION_FAILED', TRUE, 4, 1, 1, DATEADD('MONTH', -1, NOW()), DATEADD('DAY', 10, NOW()), 'OFF_GRID', 'SITE_BUDGET_FORM', 'Retorno de Lead Frio', 'Cliente pediu para ligar mês que vem.'),
    -- 5. [LEAD FRIO - VERDE] - NÃO deve aparecer (Agendado para AMANHÃ)
    ('AWAITING_RETRY', 11, 'NEGOTIATION_FAILED', TRUE, 5, 1, 1, DATEADD('MONTH', -1, NOW()), DATEADD('DAY', 10, NOW()), 'ON_GRID', 'WHATSAPP_BOT', 'Lead Futuro', 'Ainda não está na hora de ligar.'),
    -- 6. [RUÍDO] - Projeto já avançado (Status 5) - NÃO deve aparecer
    ('SCHEDULED_TECHNICAL_VISIT', 5, 'PRE_BUDGET', TRUE, 6, 1, 1, DATEADD('DAY', -2, NOW()), DATEADD('DAY', 10, NOW()), 'ON_GRID', 'WHATSAPP_BOT', 'Visita Técnica', 'Projeto em andamento normal.');

--------------------------------------------
-- RETRY QUEUE
--------------------------------------------
INSERT INTO retry_queue (scheduled_date, retrying, fk_project)
VALUES
(DATEADD('DAY', -1, NOW()), FALSE, (SELECT id_project FROM project WHERE name = 'Retorno de Lead Frio')),
(DATEADD('DAY', 1, NOW()), FALSE, (SELECT id_project FROM project WHERE name = 'Lead Futuro'));


--------------------------------------------
-- DASHBOARD VIEWS
--------------------------------------------


CREATE OR REPLACE VIEW VIEW_ANALYSIS_PROJECT_FINANCE AS
SELECT
    p.id_project,
    p.project_from AS acquisition_channel,
    p.created_at,
    p.status,
    COALESCE(b.total_cost, 0) AS total_revenue,
    COALESCE(b.material_cost, 0) + COALESCE(b.service_cost, 0) AS total_project_cost,
    COALESCE(b.total_cost, 0) - (COALESCE(b.material_cost, 0) + COALESCE(b.service_cost, 0)) AS profit_margin
FROM
    project p
LEFT JOIN
    budget b ON b.fk_project = p.id_project
WHERE
    p.is_active = true;

CREATE OR REPLACE VIEW VIEW_ANALYSIS_KPIS AS
WITH ProjectCounts AS (
    SELECT
        COUNT(id_project) AS total_projects,
        SUM(CASE WHEN status = 'COMPLETED' THEN 1 ELSE 0 END) AS completed_projects,
        SUM(CASE WHEN status = 'NEW' THEN 1 ELSE 0 END) AS new_projects,
        SUM(CASE WHEN status IN ('FINAL_BUDGET', 'INSTALLED', 'COMPLETED') THEN 1 ELSE 0 END) AS contracts_signed_projects
    FROM
        project
    WHERE
        is_active = true
),
FinancialSummary AS (
    SELECT
        acquisition_channel,
        SUM(total_project_cost) AS total_cost_by_channel,
        ROW_NUMBER() OVER (ORDER BY SUM(total_project_cost) DESC) as rn
    FROM
        VIEW_ANALYSIS_PROJECT_FINANCE
    GROUP BY
        acquisition_channel
)
SELECT
    (SELECT SUM(profit_margin) FROM VIEW_ANALYSIS_PROJECT_FINANCE) AS total_profit_margin,
    (SELECT acquisition_channel FROM FinancialSummary WHERE rn = 1) AS most_costly_channel,
    (PC.completed_projects * 100.0 / NULLIF(PC.total_projects, 0)) AS project_completion_rate,
    (PC.contracts_signed_projects * 100.0 / NULLIF(PC.new_projects, 0)) AS funnel_conversion_rate
FROM
    ProjectCounts PC;

CREATE OR REPLACE VIEW VIEW_ANALYSIS_ACQUISITION_CHANNELS AS
WITH ChannelCounts AS (
    SELECT
        acquisition_channel,
        COUNT(id_project) AS channel_project_count
    FROM
        VIEW_ANALYSIS_PROJECT_FINANCE
    GROUP BY
        acquisition_channel
),
TotalProjects AS (
    SELECT COUNT(id_project) AS total_projects FROM project WHERE is_active = true
)
SELECT
    CC.acquisition_channel AS nome,
    CC.channel_project_count,
    (CC.channel_project_count * 100.0 / NULLIF((SELECT total_projects FROM TotalProjects), 0)) AS percentual
FROM
    ChannelCounts CC
ORDER BY
    percentual DESC;

CREATE OR REPLACE VIEW VIEW_ANALYSIS_PROFIT_COST_MONTHLY AS
SELECT
    YEAR(created_at) AS ano,
    MONTH(created_at) AS mes,
    SUM(total_project_cost) AS total_cost,
    SUM(profit_margin) AS total_profit
FROM
    VIEW_ANALYSIS_PROJECT_FINANCE
GROUP BY
    YEAR(created_at), MONTH(created_at)
ORDER BY
    ano ASC, mes ASC;

CREATE OR REPLACE VIEW VIEW_ANALYSIS_PROJECTS_STATUS_SUMMARY AS
SELECT
    CASE p.status
        WHEN 'COMPLETED' THEN 'Finalizado'
        WHEN 'NEGOTIATION_FAILED' THEN 'Finalizado'
        WHEN 'SCHEDULED_TECHNICAL_VISIT' THEN 'Agendado'
        WHEN 'SCHEDULED_INSTALLING_VISIT' THEN 'Agendado'
        WHEN 'NEW' THEN 'Novo'
        ELSE 'Em andamento'
    END AS status_group,
    COUNT(p.id_project) AS quantidade
FROM
    project p
WHERE
    p.is_active = true
GROUP BY
    status_group;

CREATE OR REPLACE VIEW VIEW_ANALYSIS_SALES_FUNNEL_STAGES AS
SELECT
    CASE p.status
        WHEN 'PRE_BUDGET' THEN 'Pré-Orçamento'
        WHEN 'FINAL_BUDGET' THEN 'Proposta Enviada' 
        WHEN 'INSTALLED' THEN 'Instalado'
        WHEN 'COMPLETED' THEN 'Finalizado/Entregue'
        ELSE 'Outras Etapas'
    END AS etapa,
    COUNT(p.id_project) AS valor
FROM
    project p
WHERE
    p.is_active = true AND p.status IN ('PRE_BUDGET', 'FINAL_BUDGET', 'INSTALLED', 'COMPLETED')
GROUP BY
    etapa;

--------------------------------------------
-- ADDITIONAL TEST DATA FOR DASHBOARD
--------------------------------------------

INSERT INTO project (status, status_weight, preview_status, is_active, fk_client, fk_responsible, fk_address, created_at, deadline, system_type, project_from, name, description)
VALUES
    -- Completed Projects (Revenue)
    ('COMPLETED', 10, 'INSTALLED', TRUE, 1, 1, 1, DATEADD('MONTH', -1, NOW()), DATEADD('DAY', 10, NOW()), 'ON_GRID', 'SITE_BUDGET_FORM', 'Instalação Residencial Solar', 'Instalação completa 5kWp'),
    ('COMPLETED', 10, 'INSTALLED', TRUE, 2, 1, 1, DATEADD('MONTH', -2, NOW()), DATEADD('DAY', 10, NOW()), 'OFF_GRID', 'WHATSAPP_BOT', 'Sistema Off-Grid Sítio', 'Baterias e Paineis'),
    ('COMPLETED', 10, 'INSTALLED', TRUE, 7, 1, 1, DATEADD('DAY', -15, NOW()), DATEADD('DAY', 10, NOW()), 'ON_GRID', 'INTERNAL_MANUAL_ENTRY', 'Comercial Padaria', 'Sistema de 10kWp'),

    -- In Progress
    ('INSTALLED', 9, 'SCHEDULED_INSTALLING_VISIT', TRUE, 8, 1, 1, DATEADD('DAY', -5, NOW()), DATEADD('DAY', 20, NOW()), 'ON_GRID', 'WHATSAPP_BOT', 'Instalação em Andamento', 'Equipe em campo'),
    ('FINAL_BUDGET', 6, 'PRE_BUDGET', TRUE, 9, 1, 1, DATEADD('DAY', -2, NOW()), DATEADD('DAY', 10, NOW()), 'ON_GRID', 'SITE_BUDGET_FORM', 'Proposta Aceita', 'Aguardando assinatura'),

    -- Lost/Negotiation Failed
    ('NEGOTIATION_FAILED', 11, 'PRE_BUDGET', TRUE, 10, 1, 1, DATEADD('MONTH', -1, NOW()), DATEADD('DAY', 10, NOW()), 'ON_GRID', 'WHATSAPP_BOT', 'Desistência Preço', 'Achou caro'),

    -- New Leads
    ('NEW', 3, NULL, TRUE, 11, 1, 1, NOW(), DATEADD('DAY', 5, NOW()), 'ON_GRID', 'WHATSAPP_BOT', 'Lead Recente 1', 'Interessado em redução de conta'),
    ('NEW', 3, NULL, TRUE, 12, 1, 1, NOW(), DATEADD('DAY', 5, NOW()), 'OFF_GRID', 'SITE_BUDGET_FORM', 'Lead Recente 2', 'Fazenda');

INSERT INTO budget (total_cost, material_cost, service_cost, discount, final_budget, created_at, fk_project)
VALUES
    (25000.00, 15000.00, 5000.00, 0.00, TRUE, DATEADD('MONTH', -1, NOW()), (SELECT id_project FROM project WHERE name = 'Instalação Residencial Solar')),
    (15000.00, 10000.00, 3000.00, 0.00, TRUE, DATEADD('MONTH', -2, NOW()), (SELECT id_project FROM project WHERE name = 'Sistema Off-Grid Sítio')),
    (45000.00, 30000.00, 10000.00, 1000.00, TRUE, DATEADD('DAY', -15, NOW()), (SELECT id_project FROM project WHERE name = 'Comercial Padaria')),
    (22000.00, 12000.00, 5000.00, 0.00, TRUE, DATEADD('DAY', -5, NOW()), (SELECT id_project FROM project WHERE name = 'Instalação em Andamento')),
    (18000.00, 10000.00, 4000.00, 500.00, TRUE, DATEADD('DAY', -2, NOW()), (SELECT id_project FROM project WHERE name = 'Proposta Aceita'));
