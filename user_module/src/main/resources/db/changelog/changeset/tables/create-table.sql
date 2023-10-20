create table IF NOT EXISTS role_buyer
(
    role_id bigserial
        primary key,
    name    varchar(255) not null
        unique
);


