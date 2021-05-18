DROP schema IF EXISTS product_management cascade ;
create schema product_management;
SET search_path = product_management;

create sequence hibernate_sequence;

alter sequence hibernate_sequence owner to postgres;

create table product
(
    id bigint not null
        constraint product_pkey
            primary key,
    deleted boolean not null,
    description varchar(255),
    name varchar(255) not null,
    price numeric(19,2) not null,
    view_count bigint not null
);

alter table product owner to postgres;

create index mostvisitedproductsidx
	on product (view_count desc, deleted asc)
    where view_count > 0 and deleted is false;
