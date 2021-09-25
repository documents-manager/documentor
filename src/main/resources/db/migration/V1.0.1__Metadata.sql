create table metadata
(
    id       bigint not null
        constraint metadata_pkey
            primary key,
    key      varchar(255),
    value    varchar(255),
    asset_id bigint
        constraint fkksinagw76yhoma0ccku2k41it
            references asset
);
