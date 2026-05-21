CREATE ALIAS IF NOT EXISTS UNACCENT
FOR "com.solarize.solarizeWebBackend.shared.config.TextFunctions.unaccent";

--------------------------------------------
-- 1. PERMISSION GROUP
--------------------------------------------
INSERT INTO permission_group (role, main_module, access_client, access_project, access_budget, access_schedule, access_material, access_configuration) VALUES
('ADMIN',      'PROJECT', 15, 15, 15, 15, 15, 15),
('TÉCNICO',    'SCHEDULE',      1,  7,  1, 15,  1,  1),
('SECRETÁRIA', 'CLIENT',  15,  3, 15,  1,  1,  1);

--------------------------------------------
-- 2. COWORKER
--------------------------------------------
INSERT INTO coworker (first_name, last_name, email, phone, password, is_active, fk_permission_group) VALUES
('Sálvio', 'Nobrega', 'salvio.admin@solarize.com.br', '11987654321', '$2a$12$dUlemf8rtZhoMu/nH.5XtOmerR.uxfLp5vmVbYVrzduguD.d/jhWG', TRUE, 1),
('Cristiano', 'Ribeiro', 'cristiano.eng@solarize.com.br', '11912345678', '$2a$12$dUlemf8rtZhoMu/nH.5XtOmerR.uxfLp5vmVbYVrzduguD.d/jhWG', TRUE, 2),
('Maria', 'Gomes', 'maria.tec@solarize.com.br', '11998765432', '$2a$12$dUlemf8rtZhoMu/nH.5XtOmerR.uxfLp5vmVbYVrzduguD.d/jhWG', TRUE, 2),
('Ana', 'Vendas', 'ana.sales@solarize.com.br', '11955554444', '$2a$12$dUlemf8rtZhoMu/nH.5XtOmerR.uxfLp5vmVbYVrzduguD.d/jhWG', TRUE, 3),
('Bryan', 'Rocha', 'bryangomesrocha@gmail.com', '11964275054', '$2a$12$dUlemf8rtZhoMu/nH.5XtOmerR.uxfLp5vmVbYVrzduguD.d/jhWG', TRUE, 1);

--------------------------------------------
-- 3. ADDRESS
--------------------------------------------
INSERT INTO address (postal_code, street_name, number, neighborhood, city, state, type) VALUES
('13010-050', 'Rua XV de Novembro', '123', 'Centro', 'Campinas', 'SP', 'RESIDENTIAL'),
('01311-000', 'Av. Paulista', '2000', 'Bela Vista', 'São Paulo', 'SP', 'BUILDING'),
('88015-000', 'Rua Bocaiúva', '90', 'Centro', 'Florianópolis', 'SC', 'COMMERCIAL'),
('22021-001', 'Av. Atlântica', '500', 'Copacabana', 'Rio de Janeiro', 'RJ', 'RESIDENTIAL'),
('30130-000', 'Rua da Bahia', '1000', 'Centro', 'Belo Horizonte', 'MG', 'COMMERCIAL'),
('70000-000', 'Asa Norte', 'SQN 102', 'Plano Piloto', 'Brasília', 'DF', 'RESIDENTIAL');

--------------------------------------------
-- 4. CLIENT
--------------------------------------------
INSERT INTO client (first_name, last_name, document_number, document_type, created_at, phone, email, fk_main_address, status) VALUES
('João', 'Silva', '12345678901', 'CPF', '2025-08-01 10:00:00', '1933233431', 'joao.silva@example.com', 1, 'ACTIVE'),
('Maria', 'Oliveira', '12345678902', 'CPF', '2025-09-10 14:30:00', '2199865432', 'maria.oliveira@example.com', 2, 'ACTIVE'),
('Pedro', 'Santos', '11222333000144', 'CNPJ', '2025-10-05 09:00:00', '4899123456', 'pedro.santos@example.com', 3, 'ACTIVE'),
('Lucia', 'Ferreira', '98765432100', 'CPF', '2025-10-20 11:00:00', '21988887777', 'lucia.ferreira@example.com', 4, 'ACTIVE'),
('Empresa Tech', 'Solar', '55666777000199', 'CNPJ', '2025-11-01 15:45:00', '3133334444', 'contato@techsolar.com', 5, 'ACTIVE');

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
-- PROJECTS
INSERT INTO project (id_project, name, description, status, status_weight, preview_status, is_active, system_type, project_from, created_at, deadline, fk_client, fk_responsible, fk_address) VALUES
(1, 'Residência João Silva', 'Instalação 5kWp', 'SCHEDULED_TECHNICAL_VISIT', 5, 'CLIENT_AWAITING_CONTACT', TRUE, 'ON_GRID', 'SITE_BUDGET_FORM', DATEADD('DAY', -5, CURRENT_TIMESTAMP), DATEADD('DAY', 30, '2025-09-15'), 1, 2, 1),
(2, 'Clínica Maria Oliveira', 'Backup Off-grid', 'INSTALLED', 10, 'SCHEDULED_INSTALLING_VISIT', TRUE, 'OFF_GRID', 'WHATSAPP_BOT', DATEADD('DAY', -60, CURRENT_TIMESTAMP), DATEADD('DAY', 30, '2025-09-20'), 2, 3, 2),
(3, 'Comércio Pedro Santos', 'Sistema Comercial', 'COMPLETED', 13, 'INSTALLED', TRUE, 'ON_GRID', 'INTERNAL_MANUAL_ENTRY', DATEADD('DAY', -70, CURRENT_TIMESTAMP), DATEADD('DAY', 30, '2025-10-02'), 3, 1, 3),
(4, 'Casa de Praia Lucia', 'Off-grid simples', 'FINAL_BUDGET', 7, 'TECHNICAL_VISIT_COMPLETED', TRUE, 'OFF_GRID', 'SITE_BUDGET_FORM', DATEADD('DAY', -45, CURRENT_TIMESTAMP), DATEADD('DAY', 30, '2025-10-15'), 4, 4, 4),
(5, 'Tech Solar Sede', 'Alta demanda', 'NEW', 3, NULL, TRUE, 'ON_GRID', 'INTERNAL_MANUAL_ENTRY', DATEADD('DAY', -1, CURRENT_TIMESTAMP), DATEADD('DAY', 30, '2025-10-28'), 5, 2, 5),
(6, 'Expansão João Silva', 'Adição de painéis', 'PRE_BUDGET', 4, 'NEW', TRUE, 'ON_GRID', 'WHATSAPP_BOT', DATEADD('DAY', -10, CURRENT_TIMESTAMP), DATEADD('DAY', 30, '2025-11-05'), 1, 2, 1),
(7, 'Estacionamento Shopping', 'Carport Solar', 'SCHEDULED_INSTALLING_VISIT', 6, 'AWAITING_MATERIALS', TRUE, 'ON_GRID', 'SITE_BUDGET_FORM', DATEADD('DAY', -30, CURRENT_TIMESTAMP), DATEADD('DAY', 30, '2025-11-10'), 3, 3, 3),
(8, 'Sítio Recanto', 'Bombeamento Solar', 'NEGOTIATION_FAILED', 12, 'FINAL_BUDGET', TRUE, 'OFF_GRID', 'WHATSAPP_BOT', DATEADD('DAY', -40, CURRENT_TIMESTAMP), DATEADD('DAY', 30, '2025-11-12'), 2, 4, 2),
(9, 'Condomínio Flores', 'Área comum', 'CLIENT_AWAITING_CONTACT', 1, 'PRE_BUDGET', TRUE, 'ON_GRID', 'SITE_BUDGET_FORM', DATEADD('DAY', -2, CURRENT_TIMESTAMP), DATEADD('DAY', 30, '2025-11-20'), 4, 1, 2);

-- BUDGETS
INSERT INTO budget (id_budget, subtotal, total_cost, discount, material_cost, service_cost, created_at, discount_type, final_budget, fk_project) VALUES
(10, 18000.00, 18000.00, 0.00, 12900.00, 0.00, DATEADD('DAY', -3, CURRENT_TIMESTAMP), 'AMOUNT', FALSE, 1),
(2, 24535.10, 24000.00, 535.10, 19535.10, 5000.00, DATEADD('DAY', -45, CURRENT_TIMESTAMP), 'AMOUNT', TRUE, 2),
(3, 44387.30, 43000.00, 1387.30, 36387.30, 8000.00, DATEADD('DAY', -55, CURRENT_TIMESTAMP), 'AMOUNT', TRUE, 3),
(4, 18292.55, 18000.00, 292.55, 15292.55, 3000.00, DATEADD('DAY', -30, CURRENT_TIMESTAMP), 'AMOUNT', TRUE, 4),
(6, 10000.00, 9500.00, 5.00, 8000.00, 2000.00, DATEADD('DAY', -2, CURRENT_TIMESTAMP), 'PERCENT', FALSE, 6),
(7, 76025.50, 75000.00, 1025.50, 61025.50, 15000.00, DATEADD('DAY', -15, CURRENT_TIMESTAMP), 'AMOUNT', TRUE, 7),
(8, 38270.20, 38270.20, 0.00, 32270.20, 6000.00, DATEADD('DAY', -25, CURRENT_TIMESTAMP), 'AMOUNT', TRUE, 8),
(9, 15000.00, 15000.00, 0.00, 10000.00, 5000.00, DATEADD('DAY', -1, CURRENT_TIMESTAMP), 'AMOUNT', FALSE, 9);

-- SCHEDULES
INSERT INTO schedule (id_schedule, title, description, start_date, end_date, type, status, is_active, fk_project, fk_coworker) VALUES
(1, 'Visita Técnica João', 'Medição de telhado', DATEADD('HOUR', 24, CURRENT_TIMESTAMP), DATEADD('HOUR', 26, CURRENT_TIMESTAMP), 'TECHNICAL_VISIT', 'MARKED', TRUE, 1, 2),
(2, 'Visita Técnica Maria', 'Análise de local', DATEADD('DAY', -50, CURRENT_TIMESTAMP), DATEADD('DAY', -50, CURRENT_TIMESTAMP), 'TECHNICAL_VISIT', 'FINISHED', TRUE, 2, 3),
(3, 'Instalação Maria', 'Instalação Off-grid', DATEADD('DAY', -20, CURRENT_TIMESTAMP), DATEADD('DAY', -18, CURRENT_TIMESTAMP), 'INSTALL_VISIT', 'FINISHED', TRUE, 2, 3),
(4, 'Visita Técnica Pedro', 'Medição comercial', DATEADD('DAY', -60, CURRENT_TIMESTAMP), DATEADD('DAY', -60, CURRENT_TIMESTAMP), 'TECHNICAL_VISIT', 'FINISHED', TRUE, 3, 1),
(5, 'Instalação Pedro', 'Instalação On-grid', DATEADD('DAY', -30, CURRENT_TIMESTAMP), DATEADD('DAY', -25, CURRENT_TIMESTAMP), 'INSTALL_VISIT', 'FINISHED', TRUE, 3, 1),
(6, 'Visita Técnica Lucia', 'Análise Off-grid', DATEADD('DAY', -40, CURRENT_TIMESTAMP), DATEADD('DAY', -40, CURRENT_TIMESTAMP), 'TECHNICAL_VISIT', 'FINISHED', TRUE, 4, 4),
(7, 'Visita Técnica Shopping', 'Avaliação Carport', DATEADD('DAY', -20, CURRENT_TIMESTAMP), DATEADD('DAY', -20, CURRENT_TIMESTAMP), 'TECHNICAL_VISIT', 'FINISHED', TRUE, 7, 3),
(8, 'Instalação Shopping', 'Montagem Carport', DATEADD('DAY', 5, CURRENT_TIMESTAMP), DATEADD('DAY', 15, CURRENT_TIMESTAMP), 'INSTALL_VISIT', 'MARKED', TRUE, 7, 3),
(9, 'Visita Técnica Sítio', 'Medição bombeamento', DATEADD('DAY', -30, CURRENT_TIMESTAMP), DATEADD('DAY', -30, CURRENT_TIMESTAMP), 'TECHNICAL_VISIT', 'FINISHED', TRUE, 8, 4);

-- COMMENTS
INSERT INTO project_comment (id_project_comment, comment, created_at, fk_project, fk_coworker) VALUES
(1, 'Visita Técnica concluída com sucesso. Local preparado para instalação off-grid.', DATEADD('DAY', -49, CURRENT_TIMESTAMP), 2, 3),
(2, 'Instalação Off-grid finalizada e testada. Cliente satisfeito.', DATEADD('DAY', -15, CURRENT_TIMESTAMP), 2, 3),
(3, 'Documentação de homologação enviada e aprovada pela concessionária.', DATEADD('DAY', -5, CURRENT_TIMESTAMP), 7, 3),
(4, 'Visita técnica no comércio realizada. Estrutura suporta os painéis.', DATEADD('DAY', -59, CURRENT_TIMESTAMP), 3, 1),
(5, 'Instalação On-grid finalizada e sistema operando.', DATEADD('DAY', -25, CURRENT_TIMESTAMP), 3, 1),
(6, 'Homologação aprovada.', DATEADD('DAY', -5, CURRENT_TIMESTAMP), 3, 1),
(7, 'Projeto Elétrico elaborado e aprovado.', DATEADD('DAY', -44, CURRENT_TIMESTAMP), 2, 3),
(8, 'Visita técnica concluída. Cliente solicitou alteração no escopo.', DATEADD('DAY', -38, CURRENT_TIMESTAMP), 4, 4),
(9, 'Estrutura metálica do estacionamento vistoriada.', DATEADD('DAY', -19, CURRENT_TIMESTAMP), 7, 3);

-- FILES
INSERT INTO project_file (id_project_file, filename, original_filename, created_at, mb_size, check_sum, homologation_doc, content_type, fk_project, fk_coworker) VALUES
(1, 'homologacao_shopping.pdf', 'Homologação Copel.pdf', DATEADD('DAY', -5, CURRENT_TIMESTAMP), 2, 'abc123checksum', TRUE, 'application/pdf', 7, 3),
(2, 'homologacao_maria.pdf', 'Homologação CPFL Maria.pdf', DATEADD('DAY', -5, CURRENT_TIMESTAMP), 1, 'def456checksum', TRUE, 'application/pdf', 2, 3),
(3, 'homologacao_pedro.pdf', 'Homologação Elektro Pedro.pdf', DATEADD('DAY', -5, CURRENT_TIMESTAMP), 3, 'ghi789checksum', TRUE, 'application/pdf', 3, 1),
(4, 'projeto_eletrico_maria.pdf', 'Projeto Eletrico.pdf', DATEADD('DAY', -44, CURRENT_TIMESTAMP), 4, 'jkl012checksum', FALSE, 'application/pdf', 2, 3);

-- PAYMENTS
INSERT INTO payment (id_payment, method, installments_count, total_amount, created_at, fk_budget) VALUES
(1, 'PIX', 1, 24000.00, DATEADD('DAY', -45, CURRENT_TIMESTAMP), 2),
(2, 'CREDIT_CARD', 3, 43000.00, DATEADD('DAY', -55, CURRENT_TIMESTAMP), 3),
(3, 'FINANCING', 60, 75000.00, DATEADD('DAY', -15, CURRENT_TIMESTAMP), 7);

-- PAYMENT INSTALLMENTS
INSERT INTO payment_installment (id_payment_installment, installment_number, amount, due_date, paid_at, status, fk_payment) VALUES
(1, 1, 24000.00, DATEADD('DAY', -40, CURRENT_TIMESTAMP), DATEADD('DAY', -44, CURRENT_TIMESTAMP), 'PAID', 1),
(2, 1, 14333.33, DATEADD('DAY', -50, CURRENT_TIMESTAMP), DATEADD('DAY', -54, CURRENT_TIMESTAMP), 'PAID', 2),
(3, 2, 14333.33, DATEADD('DAY', -20, CURRENT_TIMESTAMP), DATEADD('DAY', -19, CURRENT_TIMESTAMP), 'PAID', 2),
(4, 3, 14333.34, DATEADD('DAY', 10, CURRENT_TIMESTAMP), NULL, 'PENDING', 2),
(5, 1, 1250.00, DATEADD('DAY', 15, CURRENT_TIMESTAMP), NULL, 'PENDING', 3),
(6, 2, 1250.00, DATEADD('DAY', 45, CURRENT_TIMESTAMP), NULL, 'PENDING', 3),
(7, 3, 1250.00, DATEADD('DAY', 75, CURRENT_TIMESTAMP), NULL, 'PENDING', 3);



INSERT INTO material (name, metric, hidden, description) VALUES
('Painel Solar 550W (Solar Center)', 'UNIT', FALSE, 'Ficha Técnica Painel'),
('Inversor On-Grid 5kW (Painel Forte)', 'UNIT', FALSE, 'Manual Inversor'),
('Cabo Solar 6mm', 'METER', FALSE, NULL),
('Bateria 5kWh (EcoSolar)', 'UNIT', FALSE, 'Certificação Bateria'),
('Estrutura de Fixação Telhado', 'UNIT', FALSE, NULL);

INSERT INTO material_url (url, fk_material, price, hidden) VALUES
('https://solarcenter.com/fichas/painel550w.pdf', 1, 900.00, FALSE),
('https://painelforte.com.br/manual/inversor5kw.pdf', 2, 3500.00, FALSE),
('https://ecosolar.com.br/docs/bateria5kwh.pdf', 4, 2800.00, FALSE),
('https://solarcenter.com/fichas/cabo6mm.pdf', 3, 12.00, FALSE),
('https://www.mercadolivre.com.br/painel-placa-solar-fotovoltaica-50w--controlador--cabos/up/MLBU1167249603#polycard_client=search-desktop&search_layout=grid&position=2&type=product&tracking_id=b9346980-69e9-42ee-bd8b-13f7d2334d32&wid=MLB690995630&sid=search', 1, 820.00, FALSE),
('https://www.mercadolivre.com.br/inversor-growatt-min-5000tl-x2-5kw-gerador-220v-afci/p/MLB41505710?pdp_filters=item_id%3AMLB5495151532&from=gshop&matt_tool=47518833&matt_word=&matt_source=google&matt_campaign_id=22090354007&matt_ad_group_id=173090504316&matt_match_type=&matt_network=g&matt_device=c&matt_creative=727882725753&matt_keyword=&matt_ad_position=&matt_ad_type=pla&matt_merchant_id=735098660&matt_product_id=MLB41505710-product&matt_product_partition_id=2388858354208&matt_target_id=aud-1966857867496:pla-2388858354208&cq_src=google_ads&cq_cmp=22090354007&cq_net=g&cq_plt=gp&cq_med=pla&gad_source=1&gad_campaignid=22090354007&gclid=Cj0KCQjwy_fOBhC6ARIsAHKFB7_9MV1jjRU_f-S7dKRMRt--OTSMVnrvloMuIFZbN9WJ_2jnDvghkFQaAg2ZEALw_wcB', 2, 2999.00, FALSE),
('https://www.mercadolivre.com.br/cabo-solar-6mm-flexivel-100-metros-pretovermelho-cabel-preto/p/MLB61100209?pdp_filters=item_id:MLB4587600165#is_advertising=true&searchVariation=MLB61100209&backend_model=search-backend&position=1&search_layout=grid&type=pad&tracking_id=709c4c4a-38d8-46b0-bc66-396a6b6a665d&ad_domain=VQCATCORE_LST&ad_position=1&ad_click_id=MGExMjViMDktMGU5NS00NDA3LTkzYzItNzBiNTFjMWE1NmQ4', 3, 285.00, FALSE),
('https://www.mercadolivre.com.br/bateria-solar-felicity-lifepo4-48v-100ah-alta-performance/p/MLB2072983676#polycard_client=search-desktop&search_layout=grid&position=1&type=product&tracking_id=e8684cee-65bc-406a-9885-7aacb1740c4f&wid=MLB3927023235&sid=search', 4, 5975.00, FALSE),
('https://www.mercadolivre.com.br/kit-16-parafuso-estrutura-painel-solar-telha-fibro-madeira/p/MLB67069391#polycard_client=search-desktop&search_layout=grid&position=1&type=product&tracking_id=98cdb6db-e2c5-4f5f-af97-cae489e22705&wid=MLB6505776872&sid=search', 5, 417.55, FALSE);

INSERT INTO coworker_project (fk_coworker, fk_project, is_responsible) VALUES
(2, 1, TRUE),
(3, 2, TRUE),
(1, 3, TRUE),
(4, 4, TRUE),
(2, 5, TRUE);

INSERT INTO portfolio (title, description, image_path, fk_project) VALUES
('Residência Sustentável', 'Sistema 5kWp em telhado cerâmico', '/images/portfolio/joao_v1.jpg', 1),
('Backup Hospitalar', 'Sistema de segurança energética', '/images/portfolio/maria_clinic.jpg', 2);

INSERT INTO budget_material (fk_budget, fk_material_url, quantity, price) VALUES
(2, 1, 10, 900.00),
(2, 2, 1, 3500.00),
(2, 3, 2, 2800.00),
(2, 4, 50, 12.00),
(2, 9, 2, 417.55),
(3, 5, 30, 820.00),
(3, 6, 3, 2999.00),
(3, 7, 1, 285.00),
(3, 9, 6, 417.55),
(4, 1, 6, 900.00),
(4, 2, 1, 3500.00),
(4, 8, 1, 5975.00),
(4, 9, 1, 417.55),
(7, 5, 50, 820.00),
(7, 6, 5, 2999.00),
(7, 7, 3, 285.00),
(7, 9, 10, 417.55),
(8, 1, 20, 900.00),
(8, 2, 2, 3500.00),
(8, 3, 2, 2800.00),
(8, 9, 4, 417.55);


INSERT INTO budget_parameter (name, description, metric, is_pre_budget, fixed_value, active, created_at) VALUES
('Tipo de Telhado', 'Define o material do telhado da instalação', 'un', TRUE, 0.00, TRUE, CURRENT_TIMESTAMP),
('Potência do Sistema', 'Potência total do sistema solar em kWp', 'kWp', TRUE, 0.00, TRUE, CURRENT_TIMESTAMP),
('Mão de Obra', 'Custo por hora de mão de obra da equipe', 'R$/h', FALSE, 150.00, TRUE, CURRENT_TIMESTAMP),
('Deslocamento', 'Custo de deslocamento da equipe até o local', 'km', FALSE, 2.50, TRUE, CURRENT_TIMESTAMP),
('Engenheiro', 'Valor cobrado pelo serviço de engenharia', 'R$', FALSE, 800.00, TRUE, CURRENT_TIMESTAMP),
('Tipo de Estrutura', 'Define o tipo de estrutura de fixação dos painéis', 'un', TRUE, 0.00, FALSE, CURRENT_TIMESTAMP);

--------------------------------------------
-- PARAMETER OPTION
--------------------------------------------
INSERT INTO parameter_option (type, addition_tax, fixed_cost, fk_budget_parameter) VALUES
('Cerâmico', 0.08, 500.00, 1),
('Metálico', 0.12, 800.00, 1),
('Fibrocimento', 0.05, 300.00, 1),
('até 5kWp', 0.00, 0.00, 2),
('5kWp a 10kWp', 0.10, 0.00, 2),
('acima de 10kWp', 0.20, 0.00, 2),
('Solo', 0.15, 1200.00, 6),
('Telhado Inclinado', 0.05, 400.00, 6),
('Telhado Plano', 0.08, 600.00, 6);