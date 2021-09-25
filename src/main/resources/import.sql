-- Set up some testing data

insert into epic (id, name, version)
values (1, 'Epic 1', 1);


insert into epic (id, name, version)
values (2, 'Epic 2', 1);


insert into document (id, created, description, lastupdated,
                      title, version, epic_id)
values (1, now(), 'Some Stupid description', now(), 'Document 1', 1, 1);


insert into document (id, created, description, lastupdated,
                      title, version, epic_id)
values (2, now(), 'Some Stupid description', now(), 'Document 2', 1, 2);


insert into document (id, created, description, lastupdated,
                      title, version, epic_id)
values (3, now(), 'Some Stupid description', now(), 'Document 3', 1, 1);


insert into label (id, name, version)
values (1, 'Label 1', 1);


insert into label (id, name, version)
values (2, 'Label 2', 1);


insert into asset (id, created, filename, filesize,
                   contentType, language, ocrcontent, document_id)
values (1, now(), 'asset1.pdf', 9100, 'application/json', 'de', 'WE DONT KNOW', 1);


insert into asset (id, created, filename, filesize,
                   contentType, language, ocrcontent, document_id)
values (2, now(), 'asset2.jpg', 19000, 'image/jpg', 'en', 'WE DONT KNOW', 2);


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

insert into metadata (id, key, value, asset_id)
values (4, 'ICC:Green Colorant', '(0.3851, 0.7169, 0.0971)', 2);
insert into metadata (id, key, value, asset_id)
values (5, 'ICC:Color space', 'RGB', 2);
insert into metadata (id, key, value, asset_id)
values (6, 'Content-Type', 'image/jpeg', 2);
insert into metadata (id, key, value, asset_id)
values (7, 'ICC:XYZ values', '0.964 1 0.825', 2);
insert into metadata (id, key, value, asset_id)
values (8, 'ICC:Profile Description', 'Artifex Software sRGB ICC Profile', 2);
insert into metadata (id, key, value, asset_id)
values (9, 'ICC:Blue Colorant', '(0.1431, 0.0606, 0.7141)', 2);
insert into metadata (id, key, value, asset_id)
values (10, 'tiff:ImageLength', '599', 2);
insert into metadata (id, key, value, asset_id)
values (11, 'Image Height', '599 pixels', 2);
insert into metadata (id, key, value, asset_id)
values (12, 'tiff:ImageWidth', '463', 2);
insert into metadata (id, key, value, asset_id)
values (13, 'Data Precision', '8 bits', 2);
insert into metadata (id, key, value, asset_id)
values (14, 'ICC:Red TRC',
        '0.0, 0.0000763, 0.0001526, 0.0002289, 0.0003052, 0.0003815, 0.0004578, 0.0005341, 0.0006104, 0.0006867, 0.000763, 0.0008392, 0.0009003, 0.0009766, 0.0010529, 0.0011292, 0.0012055, 0.0012818, 0.0013581, 0.0014343, 0.0015106, 0.0015869, 0.0016632, 0.0017395',
        2);
insert into metadata (id, key, value, asset_id)
values (15, 'Number of Tables', '2 Huffman tables', 2);
insert into metadata (id, key, value, asset_id)
values (16, 'ICC:Blue TRC',
        '0.0, 0.0000763, 0.0001526, 0.0002289, 0.0003052, 0.0003815, 0.0004578, 0.0005341, 0.0006104, 0.0006867, 0.000763, 0.0008392, 0.0009003, 0.0009766, 0.0010529, 0.0011292, 0.0012055, 0.0012818, 0.0013581, 0.0014343, 0.0015106, 0.0015869, 0.0016632, 0.0017395',
        2);
insert into metadata (id, key, value, asset_id)
values (17, 'ICC:Green TRC',
        '0.0, 0.0000763, 0.0001526, 0.0002289, 0.0003052, 0.0003815, 0.0004578, 0.0005341, 0.0006104, 0.0006867, 0.000763, 0.0008392, 0.0009003, 0.0009766, 0.0010529, 0.0011292, 0.0012055, 0.0012818, 0.0013581, 0.0014343, 0.0015106, 0.0015869, 0.0016632, 0.0017395',
        2);
insert into metadata (id, key, value, asset_id)
values (18, 'ICC:Red Colorant', '(0.4361, 0.2225, 0.0139)', 2);
insert into metadata (id, key, value, asset_id)
values (19, 'Compression Type', 'Baseline', 2);
insert into metadata (id, key, value, asset_id)
values (20, 'ICC:Tag Count', '10', 2);
insert into metadata (id, key, value, asset_id)
values (21, 'ICC:Profile Copyright', 'Copyright Artifex Software 2011', 2);
insert into metadata (id, key, value, asset_id)
values (22, 'File Modified Date', 'Sat Sep 25 15:55:00 +02:00 2021', 2);
insert into metadata (id, key, value, asset_id)
values (23, 'language', 'en', 2);
insert into metadata (id, key, value, asset_id)
values (24, 'ICC:Media White Point', '(0.9505, 1, 1.0891)', 2);
insert into metadata (id, key, value, asset_id)
values (25, 'File Name', 'apache-tika-17060286938165369817.tmp', 2);
insert into metadata (id, key, value, asset_id)
values (26, 'X-Parsed-By', 'org.apache.tika.parser.CompositeParser', 2);
insert into metadata (id, key, value, asset_id)
values (27, 'ICC:Primary Platform', 'Apple Computer, Inc.', 2);
insert into metadata (id, key, value, asset_id)
values (28, 'ICC:Class', 'Display Device', 2);
insert into metadata (id, key, value, asset_id)
values (29, 'ICC:Profile Connection Space', 'XYZ', 2);
insert into metadata (id, key, value, asset_id)
values (30, 'tiff:BitsPerSample', '8', 2);
insert into metadata (id, key, value, asset_id)
values (31, 'Image Width', '463 pixels', 2);
insert into metadata (id, key, value, asset_id)
values (32, 'Component 1', 'Y component: Quantization table 0, Sampling factors 2 horiz/2 vert', 2);
insert into metadata (id, key, value, asset_id)
values (33, 'ICC:Profile Size', '2576', 2);
insert into metadata (id, key, value, asset_id)
values (34, 'ICC:Signature', 'acsp', 2);
insert into metadata (id, key, value, asset_id)
values (35, 'ICC:Media Black Point', '(0, 0, 0)', 2);
insert into metadata (id, key, value, asset_id)
values (36, 'Number of Components', '1', 2);
insert into metadata (id, key, value, asset_id)
values (37, 'File Size', '19159 bytes', 2);
insert into metadata (id, key, value, asset_id)
values (38, 'pdf:unmappedUnicodeCharsPerPage', '0', 1);
insert into metadata (id, key, value, asset_id)
values (39, 'xmpTPg:NPages', '1', 1);
insert into metadata (id, key, value, asset_id)
values (40, 'meta:author', 'Mario Zeppin', 1);
insert into metadata (id, key, value, asset_id)
values (41, 'producer', 'OpenOffice.org 2.3', 1);
insert into metadata (id, key, value, asset_id)
values (42, 'language', 'de', 1);
insert into metadata (id, key, value, asset_id)
values (43, 'xmp:CreatorTool', 'Writer', 1);
insert into metadata (id, key, value, asset_id)
values (44, 'pdf:docinfo:producer', 'OpenOffice.org 2.3', 1);
insert into metadata (id, key, value, asset_id)
values (45, 'access_permission:can_print_degraded', 'true', 1);
insert into metadata (id, key, value, asset_id)
values (46, 'Author', 'Mario Zeppin', 1);
insert into metadata (id, key, value, asset_id)
values (47, 'meta:creation-date', '2007-12-17T14:29:54Z', 1);
insert into metadata (id, key, value, asset_id)
values (48, 'access_permission:can_print', 'true', 1);
insert into metadata (id, key, value, asset_id)
values (49, 'pdf:hasMarkedContent', 'false', 1);
insert into metadata (id, key, value, asset_id)
values (50, 'access_permission:modify_annotations', 'true', 1);
insert into metadata (id, key, value, asset_id)
values (51, 'access_permission:fill_in_form', 'true', 1);
insert into metadata (id, key, value, asset_id)
values (52, 'access_permission:extract_for_accessibility', 'true', 1);
insert into metadata (id, key, value, asset_id)
values (53, 'pdf:PDFVersion', '1.4', 1);
insert into metadata (id, key, value, asset_id)
values (54, 'dc:creator', 'Mario Zeppin', 1);
insert into metadata (id, key, value, asset_id)
values (55, 'pdf:hasXMP', 'false', 1);
insert into metadata (id, key, value, asset_id)
values (56, 'Content-Type', 'application/pdf', 1);
insert into metadata (id, key, value, asset_id)
values (57, 'Creation-Date', '2007-12-17T14:29:54Z', 1);
insert into metadata (id, key, value, asset_id)
values (58, 'dc:format', 'application/pdf; version=1.4', 1);
insert into metadata (id, key, value, asset_id)
values (59, 'dcterms:created', '2007-12-17T14:29:54Z', 1);
insert into metadata (id, key, value, asset_id)
values (60, 'pdf:charsPerPage', '23', 1);
insert into metadata (id, key, value, asset_id)
values (61, 'pdf:docinfo:created', '2007-12-17T14:29:54Z', 1);
insert into metadata (id, key, value, asset_id)
values (62, 'X-Parsed-By', 'org.apache.tika.parser.CompositeParser', 1);
insert into metadata (id, key, value, asset_id)
values (63, 'pdf:encrypted', 'false', 1);
insert into metadata (id, key, value, asset_id)
values (64, 'access_permission:assemble_document', 'true', 1);
insert into metadata (id, key, value, asset_id)
values (65, 'pdf:docinfo:creator_tool', 'Writer', 1);
insert into metadata (id, key, value, asset_id)
values (66, 'access_permission:can_modify', 'true', 1);
insert into metadata (id, key, value, asset_id)
values (67, 'access_permission:extract_content', 'true', 1);
insert into metadata (id, key, value, asset_id)
values (68, 'pdf:docinfo:creator', 'Mario Zeppin', 1);
insert into metadata (id, key, value, asset_id)
values (69, 'created', '2007-12-17T14:29:54Z', 1);
insert into metadata (id, key, value, asset_id)
values (70, 'pdf:hasXFA', 'false', 1);
insert into metadata (id, key, value, asset_id)
values (71, 'creator', 'Mario Zeppin', 1);
