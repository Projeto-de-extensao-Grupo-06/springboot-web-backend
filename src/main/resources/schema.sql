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
                                      0XF,
                                      0XF,
                                      0XF,
                                      0XF
                                     );

INSERT INTO coworker (first_name, last_name, email, phone, password, fk_permission_group)
VALUES (
        'Bryan',
        'Rocha',
        'bryangomesrocha@gmail.com',
        '11964275054',
        '$2a$12$dUlemf8rtZhoMu/nH.5XtOmerR.uxfLp5vmVbYVrzduguD.d/jhWG',
        1
       );