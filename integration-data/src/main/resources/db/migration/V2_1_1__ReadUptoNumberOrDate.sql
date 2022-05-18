alter table integrating_entity_status
    rename column read_upto to read_upto_numeric;
alter table integrating_entity_status
    add column read_upto_date_time TIMESTAMP WITHOUT TIME ZONE null;

alter table integrating_entity_status
    add constraint all_read_upto_cannot_be_null check ((read_upto_numeric is not null) or (read_upto_date_time is not null));

alter table mapping_metadata
    rename column bahmni_value to int_system_value;

update error_record_log
set error_type = 'NoIntEntityWithId'
where error_type = 'NoPatientWithId';
update error_record_log
set error_type = 'IntEntityIdChanged'
where error_type = 'PatientIdChanged';
update error_record_log
set error_type = 'NotAvniEntityFound'
where error_type = 'NotACommunityMember';

create table integration_system
(
    id   SERIAL PRIMARY KEY,
    name CHARACTER VARYING(250)
);

create table integrating_entity_type
(
    id   SERIAL PRIMARY KEY,
    name CHARACTER VARYING(250),
    integration_system_id int not null references integration_system(id)
);

alter table error_record add column integration_system_id int null references integration_system(id);
