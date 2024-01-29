/*insert into users (email, first_name, is_enabled, is_locked, last_name, password, penalty_points, user_role, city, company_info, country, occupation, phone_number)
values ('kovacevic.anja2002@gmail.com', 'Anja', true, false, 'Kovacevic', '$2a$10$8zpGNS6paziQhFVcHY0eK.X3S45KOJ1U7JzQTbmMf8jhQv64BGDWe', 0, 'USER', 'Kursumlija', 'Kompanija', 'Srbija', 'student', '0665748394');
insert into users (email, first_name, is_enabled, is_locked, last_name, password, penalty_points, user_role, city, company_info, country, occupation, phone_number)
values ('anaa.radovanovic2001@gmail.com', 'Ana', true, false, 'Radovanovic', '$2a$10$EwJPrNmPt501yieGlXXIUerWBH9z2CUWtNQB/TbzsBQ1dt307O55a', 0, 'USER', 'Novi Sad', 'Medicinska oprema', 'Srbija', 'student', '0658372947');
insert into users (email, first_name, is_enabled, is_locked, last_name, password, penalty_points, user_role, city, company_info, country, occupation, phone_number)
values ('kristinazelic@gmail.com', 'Kristina', true, false, 'Zelic', '$2a$10$hxZCabNylbUKlrXj/2yvke2mtSI6Mb8PFHuiqGmSyifpWNq1B3I1a', 0, 'USER', 'Novi Sad', 'Medicinska kompanija', 'Srbija', 'student', '0685948274');
insert into users (email, first_name, is_enabled, is_locked, last_name, password, penalty_points, user_role, city, company_info, country, occupation, phone_number)
values ('peraperic@gmail.com', 'Pera', true, false, 'Peric', '$2a$10$hxZCabNylbUKlrXj/2yvke2mtSI6Mb8PFHuiqGmSyifpWNq1B3I1a', 0, 'USER', 'Novi Sad', 'Medical company', 'Srbija', 'student', '0657382947');
insert into users (email, first_name, is_enabled, is_locked, last_name, password, penalty_points, user_role, city, company_info, country, occupation, phone_number)
values ('zikazikic@gmail.com', 'Zika', true, false, 'Zikic', '$2a$10$hxZCabNylbUKlrXj/2yvke2mtSI6Mb8PFHuiqGmSyifpWNq1B3I1a', 0, 'USER', 'Novi Sad', 'Medic', 'Srbija', 'student', '06574833957');

insert into company (address, average_grade, description, name)
values ('Olge hadzic 17',4.6,'Kompanija za prodaju medicinske opreme','MedicCompany');
insert into company (address, average_grade, description, name)
values ('Rumenacka 145',3.4,'Prodaja medicinskih pomagala','Trivax');
insert into company (address, average_grade, description, name)
values ('Bulevar oslobodjenja 25',4.9,'Prodaja medicinskih uniforma','Medipro');
insert into company (address, average_grade, description, name)
values ('Sime Milosevica 5',2.3,'Prodaja medicinskih aparata','Medisal');

insert into company_admin (company_id, user_id)
values (1,1);
insert into company_admin (company_id, user_id)
values (1,2);
insert into company_admin (company_id, user_id)
values (2,3);

insert into equipment (description, grade, name, price, type)
values ('Pasterova pipeta', 4.5,'Pipeta',240,'Posudje');
insert into equipment (description, grade, name, price, type)
values ('Epruveta za mikroelemente', 4.2,'Epruveta',360,'Posudje');
insert into equipment (description, grade, name, price, type)
values ('Elektrode ekstremiteta', 3.2,'Elektroda',1020,'EKG aparati');
insert into equipment (description, grade, name, price, type)
values ('Inhalator za bebe', 2.0,'Inhalator',520,'Inhalatori');

insert into company_equipment (equipment_id, company_id)
values (1,1);
insert into company_equipment (equipment_id, company_id)
values (2,2);
insert into company_equipment (equipment_id, company_id)
values (3,2);
*/

