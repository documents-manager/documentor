create table epic
(
    id bigint not null
        constraint epic_pkey
            primary key,
    name varchar(255),
    version integer
);

create table document
(
    id bigint not null
        constraint document_pkey
            primary key,
    created timestamp not null,
    description text,
    lastupdated timestamp,
    title varchar(255),
    version integer,
    epic_id bigint
        constraint fkgxyiq99r2y6s5cijjvusmjd9a
            references epic
);

create table asset
(
    id bigint not null
        constraint asset_pkey
            primary key,
    created timestamp,
    filename varchar(255),
    filesize bigint,
    hash varchar(255),
    mimetype varchar(255),
    ocrcontent text,
    document_id bigint
        constraint fkqkj5kicyplem4ddnpu5g9baxc
            references document
);

create table documentreference
(
    source_id bigint not null
        constraint fkhhvdk4ifroek7vksyy8sjlfm0
            references document,
    target_id bigint not null
        constraint fkg9qws9y5srhb6cgstj98a0w64
            references document,
    referencetype varchar(255),
    constraint documentreference_pkey
        primary key (source_id, target_id)
);

create table epic_document
(
    epic_id bigint not null
        constraint fk6planssgp79n3yl7prpruswu8
            references epic,
    associateddocuments_id bigint not null
        constraint uk_2k7oho87ckm6lycc7s7kw77s3
            unique
        constraint fkilgtll0xcm3decn9wtly7h2tm
            references document
);

create table label
(
    id bigint not null
        constraint label_pkey
            primary key,
    name varchar(255),
    version integer
);

create table document_label
(
    document_id bigint not null
        constraint fk7mh19a918bcadrhs7fxv28rwp
            references document,
    label_id bigint not null
        constraint fkec7brcy597992cmatolvg5syi
            references label
);

