alter table artist
    add column date_notification boolean default false;

alter table writer
    add column date_notification boolean default false;

alter table musician
    add column date_notification boolean default false;