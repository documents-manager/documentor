create sequence assetseq;

alter sequence assetseq owner to postgres;

create sequence documentseq;

alter sequence documentseq owner to postgres;

create sequence epicseq;

alter sequence epicseq owner to postgres;

create sequence labelseq;

alter sequence labelseq owner to postgres;

create sequence metadataseq;

alter sequence metadataseq owner to postgres;

create table epic
(
    id   bigint not null
        primary key,
    name varchar(255)
);

alter table epic
    owner to postgres;

create table document
(
    id          bigint    not null
        primary key,
    created     timestamp not null,
    description text,
    lastupdated timestamp,
    title       varchar(255),
    epic_id     bigint
        constraint fkgxyiq99r2y6s5cijjvusmjd9a
            references epic
);

alter table document
    owner to postgres;

create table asset
(
    id          bigint not null
        primary key,
    contenttype varchar(255),
    created     timestamp,
    filename    varchar(255),
    filesize    bigint,
    hash        varchar(255),
    language    varchar(255),
    ocrcontent  text,
    document_id bigint
        constraint fkqkj5kicyplem4ddnpu5g9baxc
            references document
);

alter table asset
    owner to postgres;

create table documentreference
(
    source_id     bigint not null
        constraint fkhhvdk4ifroek7vksyy8sjlfm0
            references document,
    target_id     bigint not null
        constraint fkg9qws9y5srhb6cgstj98a0w64
            references document,
    referencetype varchar(255),
    primary key (source_id, target_id)
);

alter table documentreference
    owner to postgres;

create table label
(
    id   bigint not null
        primary key,
    name varchar(255)
);

alter table label
    owner to postgres;

create table document_label
(
    label_id    bigint not null
        constraint fkec7brcy597992cmatolvg5syi
            references label,
    document_id bigint not null
        constraint fk7mh19a918bcadrhs7fxv28rwp
            references document
);

alter table document_label
    owner to postgres;

create table metadata
(
    id       bigint not null
        primary key,
    key      varchar(255),
    value    varchar(255),
    asset_id bigint
        constraint fkksinagw76yhoma0ccku2k41it
            references asset
);

alter table metadata
    owner to postgres;

