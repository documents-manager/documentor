-- Set up some testing data

INSERT INTO epic (id, name, VERSION)
VALUES (1, 'Epic 1', 1);


INSERT INTO epic (id, name, VERSION)
VALUES (2, 'Epic 2', 1);


INSERT INTO document (id, created, description, lastupdated, title, VERSION, epic_id)
VALUES (1, now(), 'Some Stupid description', now(), 'Document 1', 1, 1);


INSERT INTO document (id, created, description, lastupdated, title, VERSION, epic_id)
VALUES (2, now(), 'Some Stupid description', now(), 'Document 2', 1, 2);


INSERT INTO document (id, created, description, lastupdated, title, VERSION, epic_id)
VALUES (3, now(), 'Some Stupid description', now(), 'Document 3', 1, 1);


INSERT INTO label (id, name, VERSION)
VALUES (1, 'Label 1', 1);


INSERT INTO label (id, name, VERSION)
VALUES (2, 'Label 2', 1);


INSERT INTO asset (id, created, filename, filesize, mimetype, ocrcontent, document_id)
VALUES (1, now(), 'asset1.pdf', 9100, 'application/json', 'WE DONT KNOW', 1);


INSERT INTO asset (id, created, filename, filesize, mimetype, ocrcontent, document_id)
VALUES (2, now(), 'asset2.pdf', 19000, 'image/jpg', 'WE DONT KNOW', 2);


INSERT INTO document_label (document_id, label_id)
VALUES (1, 1);


INSERT INTO document_label (document_id, label_id)
VALUES (2, 2);


INSERT INTO document_label (document_id, label_id)
VALUES (1, 2);


INSERT INTO documentreference (source_id, target_id, referencetype)
VALUES (1, 2, 'MENTION');


INSERT INTO documentreference (source_id, target_id, referencetype)
VALUES (2, 3, 'RELATED');
