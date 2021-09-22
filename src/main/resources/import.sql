-- Set up some testing data

insert into epic (id, name, VERSION)
values (1, 'Epic 1', 1);


insert into epic (id, name, VERSION)
values (2, 'Epic 2', 1);


insert into document (id, created, description, lastupdated,
                      title, VERSION, epic_id)
values (1, now(), 'Some Stupid description', now(), 'Document 1', 1, 1);


insert into document (id, created, description, lastupdated,
                      title, VERSION, epic_id)
values (2, now(), 'Some Stupid description', now(), 'Document 2', 1, 2);


insert into document (id, created, description, lastupdated,
                      title, VERSION, epic_id)
values (3, now(), 'Some Stupid description', now(), 'Document 3', 1, 1);


insert into label (id, name, VERSION)
values (1, 'Label 1', 1);


insert into label (id, name, VERSION)
values (2, 'Label 2', 1);


insert into asset (id, created, filename, filesize,
                   mimetype, ocrcontent, document_id)
values (1, now(), 'asset1.pdf', 9100, 'application/json', 'WE DONT KNOW', 1);


insert into asset (id, created, filename, filesize,
                   mimetype, ocrcontent, document_id)
values (2, now(), 'asset2.pdf', 19000, 'image/jpg', 'WE DONT KNOW', 2);


insert into document_label (document_id, label_id)
values (1, 1);


insert into document_label (document_id, label_id)
values (2, 2);


insert into document_label (document_id, label_id)
values (1, 2);


insert into documentreference (source_id, target_id, referencetype)
values (1, 2, 'MENTION');


insert into documentreference (source_id, target_id, referencetype)
values (2, 3, 'RELATED');
