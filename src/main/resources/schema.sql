--------------------------------------------
-- 1. PERMISSION GROUP
--------------------------------------------
INSERT INTO permission_group (id_permission_group, role, main_module, access_client, access_project, access_budget, access_schedule) VALUES
(1, 'ADMIN', 'PROJECT_LIST', 15, 15, 15, 15),
(2, 'TECHNICAL', 'SCHEDULE', 1, 7, 1, 15),
(3, 'SALES', 'CLIENT_LIST', 15, 3, 15, 1);

--------------------------------------------
-- 2. COWORKER
--------------------------------------------
INSERT INTO coworker (id_coworker, first_name, last_name, email, phone, password, is_active, fk_permission_group) VALUES
(1, 'Sálvio', 'Nobrega', 'salvio.admin@solarize.com.br', '11987654321', '$2a$12$dUlemf8rtZhoMu/nH.5XtOmerR.uxfLp5vmVbYVrzduguD.d/jhWG', TRUE, 1),
(2, 'Cristiano', 'Ribeiro', 'cristiano.eng@solarize.com.br', '11912345678', '$2a$12$dUlemf8rtZhoMu/nH.5XtOmerR.uxfLp5vmVbYVrzduguD.d/jhWG', TRUE, 2),
(3, 'Maria', 'Gomes', 'maria.tec@solarize.com.br', '11998765432', '$2a$12$dUlemf8rtZhoMu/nH.5XtOmerR.uxfLp5vmVbYVrzduguD.d/jhWG', TRUE, 2),
(4, 'Ana', 'Vendas', 'ana.sales@solarize.com.br', '11955554444', '$2a$12$dUlemf8rtZhoMu/nH.5XtOmerR.uxfLp5vmVbYVrzduguD.d/jhWG', TRUE, 3),
(5, 'Bryan', 'Rocha', 'bryangomesrocha@gmail.com', '11964275054', '$2a$12$dUlemf8rtZhoMu/nH.5XtOmerR.uxfLp5vmVbYVrzduguD.d/jhWG', TRUE, 1);

--------------------------------------------
-- 3. ADDRESS
--------------------------------------------
INSERT INTO address (id_address, postal_code, street_name, number, neighborhood, city, state, type) VALUES
(1, '13010-050', 'Rua XV de Novembro', '123', 'Centro', 'Campinas', 'SP', 'RESIDENTIAL'),
(2, '01311-000', 'Av. Paulista', '2000', 'Bela Vista', 'São Paulo', 'SP', 'BUILDING'),
(3, '88015-000', 'Rua Bocaiúva', '90', 'Centro', 'Florianópolis', 'SC', 'COMMERCIAL'),
(4, '22021-001', 'Av. Atlântica', '500', 'Copacabana', 'Rio de Janeiro', 'RJ', 'RESIDENTIAL'),
(5, '30130-000', 'Rua da Bahia', '1000', 'Centro', 'Belo Horizonte', 'MG', 'COMMERCIAL'),
(6, '70000-000', 'Asa Norte', 'SQN 102', 'Plano Piloto', 'Brasília', 'DF', 'RESIDENTIAL');

--------------------------------------------
-- 4. CLIENT
--------------------------------------------
-- 4.1 Critical Clients (IDs 1-5) linked to Projects
INSERT INTO client (first_name, last_name, document_number, document_type, created_at, phone, email, fk_main_address, status) VALUES
('João', 'Silva', '12345678901', 'CPF', '2025-08-01 10:00:00', '1933233431', 'joao.silva@example.com', 1, 'ACTIVE'),
('Maria', 'Oliveira', '12345678902', 'CPF', '2025-09-10 14:30:00', '2199865432', 'maria.oliveira@example.com', 2, 'ACTIVE'),
('Pedro', 'Santos', '11222333000144', 'CNPJ', '2025-10-05 09:00:00', '4899123456', 'pedro.santos@example.com', 3, 'ACTIVE'),
('Lucia', 'Ferreira', '98765432100', 'CPF', '2025-10-20 11:00:00', '21988887777', 'lucia.ferreira@example.com', 4, 'ACTIVE'),
('Empresa Tech', 'Solar', '55666777000199', 'CNPJ', '2025-11-01 15:45:00', '3133334444', 'contato@techsolar.com', 5, 'ACTIVE');

-- 4.2 Additional Volume Clients (IDs Generated, No Address)
INSERT INTO client (first_name, last_name, phone, email, status, document_number, document_type, created_at, updated_at) VALUES
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
-- 5. PROJECT
--------------------------------------------
INSERT INTO project (id_project, name, description, status, status_weight, preview_status, is_active, system_type, project_from, created_at, deadline, fk_client, fk_responsible, fk_address) VALUES
(1, 'Residência João Silva', 'Instalação 5kWp', 'SCHEDULED_TECHNICAL_VISIT', 5, 'CLIENT_AWAITING_CONTACT', TRUE, 'ON_GRID', 'SITE_BUDGET_FORM', '2025-09-15 09:00:00', DATEADD('DAY', 30, '2025-09-15'), 1, 2, 1),
(2, 'Clínica Maria Oliveira', 'Backup Off-grid', 'INSTALLED', 10, 'SCHEDULED_INSTALLING_VISIT', TRUE, 'OFF_GRID', 'WHATSAPP_BOT', '2025-09-20 10:30:00', DATEADD('DAY', 30, '2025-09-20'), 2, 3, 2),
(3, 'Comércio Pedro Santos', 'Sistema Comercial', 'COMPLETED', 13, 'INSTALLED', TRUE, 'ON_GRID', 'INTERNAL_MANUAL_ENTRY', '2025-10-02 14:00:00', DATEADD('DAY', 30, '2025-10-02'), 3, 1, 3),
(4, 'Casa de Praia Lucia', 'Off-grid simples', 'FINAL_BUDGET', 7, 'TECHNICAL_VISIT_COMPLETED', TRUE, 'OFF_GRID', 'SITE_BUDGET_FORM', '2025-10-15 11:00:00', DATEADD('DAY', 30, '2025-10-15'), 4, 4, 4),
(5, 'Tech Solar Sede', 'Alta demanda', 'NEW', 3, NULL, TRUE, 'ON_GRID', 'INTERNAL_MANUAL_ENTRY', '2025-10-28 16:00:00', DATEADD('DAY', 30, '2025-10-28'), 5, 2, 5),
(6, 'Expansão João Silva', 'Adição de painéis', 'PRE_BUDGET', 4, 'NEW', TRUE, 'ON_GRID', 'WHATSAPP_BOT', '2025-11-05 08:30:00', DATEADD('DAY', 30, '2025-11-05'), 1, 2, 1),
(7, 'Estacionamento Shopping', 'Carport Solar', 'SCHEDULED_INSTALLING_VISIT', 6, 'AWAITING_MATERIALS', TRUE, 'ON_GRID', 'SITE_BUDGET_FORM', '2025-11-10 13:00:00', DATEADD('DAY', 30, '2025-11-10'), 3, 3, 3),
(8, 'Sítio Recanto', 'Bombeamento Solar', 'NEGOTIATION_FAILED', 12, 'FINAL_BUDGET', TRUE, 'OFF_GRID', 'WHATSAPP_BOT', '2025-11-12 09:00:00', DATEADD('DAY', 30, '2025-11-12'), 2, 4, 2),
(9, 'Condomínio Flores', 'Área comum', 'CLIENT_AWAITING_CONTACT', 1, 'PRE_BUDGET', TRUE, 'ON_GRID', 'SITE_BUDGET_FORM', '2025-11-20 15:00:00', DATEADD('DAY', 30, '2025-11-20'), 4, 1, 4);

--------------------------------------------
-- 6. BUDGET
--------------------------------------------
INSERT INTO budget (id_budget, total_cost, discount, material_cost, service_cost, final_budget, fk_project, created_at) VALUES
(1, 18000.00, 500.00, 10000.00, 5000.00, TRUE, 1, CURRENT_TIMESTAMP),
(2, 45000.00, 2000.00, 25000.00, 12000.00, TRUE, 2, CURRENT_TIMESTAMP),
(3, 8500.00, 0.00, 5000.00, 2000.00, FALSE, 4, CURRENT_TIMESTAMP),
(4, 22000.00, 1000.00, 12000.00, 6000.00, TRUE, 3, CURRENT_TIMESTAMP),
(5, 50000.00, 0.00, 30000.00, 10000.00, TRUE, 7, CURRENT_TIMESTAMP),
(6, 12000.00, 0.00, 8000.00, 4000.00, FALSE, 6, CURRENT_TIMESTAMP),
(7, 30000.00, 1500.00, 15000.00, 8000.00, TRUE, 8, CURRENT_TIMESTAMP),
(8, 0.00, 0.00, 0.00, 0.00, FALSE, 5, CURRENT_TIMESTAMP),
(9, 0.00, 0.00, 0.00, 0.00, FALSE, 9, CURRENT_TIMESTAMP);

--------------------------------------------
-- 7. MATERIAL
--------------------------------------------
INSERT INTO material (id_material, name, metric, hidden, description) VALUES
(1, 'Painel Solar 550W (Solar Center)', 'UNIT', FALSE, 'Ficha Técnica Painel'),
(2, 'Inversor On-Grid 5kW (Painel Forte)', 'UNIT', FALSE, 'Manual Inversor'),
(3, 'Cabo Solar 6mm', 'METER', FALSE, NULL),
(4, 'Bateria 5kWh (EcoSolar)', 'UNIT', FALSE, 'Certificação Bateria'),
(5, 'Estrutura de Fixação Telhado', 'UNIT', FALSE, NULL);

--------------------------------------------
-- 8. MATERIAL URL
--------------------------------------------
INSERT INTO material_url (id_material_url, url, fk_material, price, hidden) VALUES
(1, 'https://solarcenter.com/fichas/painel550w.pdf', 1, 900.00, FALSE),
(2, 'https://painelforte.com.br/manual/inversor5kw.pdf', 2, 3500.00, FALSE),
(3, 'https://ecosolar.com.br/docs/bateria5kwh.pdf', 4, 2800.00, FALSE),
(4, 'https://solarcenter.com/fichas/cabo6mm.pdf', 3, 12.00, FALSE);

--------------------------------------------
-- 9. BUDGET MATERIAL
--------------------------------------------
INSERT INTO budget_material (fk_budget, fk_material_url, quantity, price) VALUES
(1, 1, 10, 900.00),
(1, 4, 50, 12.00),
(2, 2, 2, 3500.00),
(2, 3, 4, 2800.00),
(4, 1, 20, 900.00);

--------------------------------------------
-- 10. COWORKER PROJECT
--------------------------------------------
INSERT INTO coworker_project (fk_coworker, fk_project, is_responsible) VALUES
(2, 1, TRUE),
(3, 2, TRUE),
(1, 3, TRUE),
(4, 4, TRUE),
(2, 5, TRUE),
(2, 6, FALSE),
(3, 7, TRUE);

--------------------------------------------
-- 11. SCHEDULE
--------------------------------------------
INSERT INTO schedule (id_schedule, title, description, start_date, end_date, type, status, is_active, fk_project, fk_coworker) VALUES
(1, 'Visita Técnica João', 'Medição de telhado', '2025-09-25 10:00:00', '2025-09-25 12:00:00', 'TECHNICAL_VISIT', 'FINISHED', TRUE, 1, 2),
(2, 'Instalação Maria', 'Instalação Off-grid', '2025-10-01 08:00:00', '2025-10-03 18:00:00', 'INSTALL_VISIT', 'FINISHED', TRUE, 1, 3),
(3, 'Visita Técnica Lucia', 'Avaliação local', '2025-10-18 14:00:00', '2025-10-18 16:00:00', 'TECHNICAL_VISIT', 'FINISHED', TRUE, 4, 4),
(4, 'Instalação Shopping', 'Montagem Carport', '2025-12-05 08:00:00', '2025-12-10 18:00:00', 'INSTALL_VISIT', 'MARKED', TRUE, 7, 3);

--------------------------------------------
-- 12. PORTFOLIO
--------------------------------------------
INSERT INTO portfolio (id_portfolio, title, description, image_path, fk_project) VALUES
(1, 'Residência Sustentável', 'Sistema 5kWp em telhado cerâmico', '/images/portfolio/joao_v1.jpg', 1),
(2, 'Backup Hospitalar', 'Sistema de segurança energética', '/images/portfolio/maria_clinic.jpg', 2);

--------------------------------------------
-- 13. RETRY QUEUE
--------------------------------------------
INSERT INTO retry_queue (scheduled_date, retrying, fk_project) VALUES
(DATEADD('DAY', -1, NOW()), FALSE, (SELECT id_project FROM project WHERE name = 'Retorno de Lead Frio')),
(DATEADD('DAY', 1, NOW()), FALSE, (SELECT id_project FROM project WHERE name = 'Lead Futuro'));

DROP TABLE IF EXISTS VIEW_ANALYSIS_PROJECT_FINANCE CASCADE;

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