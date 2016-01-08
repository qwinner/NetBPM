INSERT INTO NBPM_ACTOR VALUES('ae','User',NULL,NULL,NULL,'Albert','Einstein','ae@localhost');
INSERT INTO NBPM_ACTOR VALUES('cg','User',NULL,NULL,NULL,'Carl','Gauss','cg@localhost');
INSERT INTO NBPM_ACTOR VALUES('ed','User',NULL,NULL,NULL,'Edsger','Dijkstra','ed@localhost');
INSERT INTO NBPM_ACTOR VALUES('group A','Group','group-assignment-test-group A','hierarchy2',NULL,NULL,NULL,NULL);
INSERT INTO NBPM_ACTOR VALUES('group B','Group','group-assignment-test-group B','hierarchy2','group A',NULL,NULL,NULL);
INSERT INTO NBPM_ACTOR VALUES('group C','Group','group-assignment-test-group C','hierarchy2','group A',NULL,NULL,NULL);
INSERT INTO NBPM_ACTOR VALUES('group D','Group','group-assignment-test-group D','hierarchy2','group C',NULL,NULL,NULL);
INSERT INTO NBPM_ACTOR VALUES('group-top','Group','netbpm-enterprises','hierarchy',NULL,NULL,NULL,NULL);
INSERT INTO NBPM_ACTOR VALUES('group-acc','Group','Accounting','hierarchy','group-top',NULL,NULL,NULL);
INSERT INTO NBPM_ACTOR VALUES('group-hr','Group','Human Resources','hierarchy','group-top',NULL,NULL,NULL);
INSERT INTO NBPM_ACTOR VALUES('group-ms','Group','Marketing & Sales','hierarchy','group-top',NULL,NULL,NULL);
INSERT INTO NBPM_ACTOR VALUES('group-rd','Group','Research & Development','hierarchy','group-top',NULL,NULL,NULL);
INSERT INTO NBPM_ACTOR VALUES('in','User',NULL,NULL,NULL,'Isaac','Newton','in@localhost');
INSERT INTO NBPM_ACTOR VALUES('netbpm-domain','Group','All users in the j2ee-security-domain ''netbpm-domain''','security',NULL,NULL,NULL,NULL);
INSERT INTO NBPM_ACTOR VALUES('pf','User',NULL,NULL,NULL,'Pierre','De Fermat','pf@localhost');
INSERT INTO NBPM_ACTOR VALUES('uaoga','User',NULL,NULL,NULL,'user a of group a','user a of group a','uaoga@localhost');
INSERT INTO NBPM_ACTOR VALUES('uaogb','User',NULL,NULL,NULL,'user a of group b','user a of group b','uaogb@localhost');
INSERT INTO NBPM_ACTOR VALUES('uaogc','User',NULL,NULL,NULL,'user a of group c','user a of group c','uaogc@localhost');
INSERT INTO NBPM_ACTOR VALUES('uaogd','User',NULL,NULL,NULL,'user a of group d','user a of group d','uaogd@localhost');
INSERT INTO NBPM_ACTOR VALUES('uboga','User',NULL,NULL,NULL,'user b of group a','user b of group a','uboga@localhost');
INSERT INTO NBPM_ACTOR VALUES('ubogb','User',NULL,NULL,NULL,'user b of group b','user b of group b','ubogb@localhost');
INSERT INTO NBPM_ACTOR VALUES('ubogc','User',NULL,NULL,NULL,'user b of group c','user b of group c','ubogc@localhost');
INSERT INTO NBPM_ACTOR VALUES('ubogd','User',NULL,NULL,NULL,'user b of group d','user b of group d','ubogd@localhost');
INSERT INTO NBPM_ACTOR VALUES('ucoga','User',NULL,NULL,NULL,'user c of group a','user c of group a','ucoga@localhost');
INSERT INTO NBPM_ACTOR VALUES('ucogb','User',NULL,NULL,NULL,'user c of group b','user c of group b','ucogb@localhost');
INSERT INTO NBPM_ACTOR VALUES('ucogc','User',NULL,NULL,NULL,'user c of group c','user c of group c','ucogc@localhost');
INSERT INTO NBPM_ACTOR VALUES('ucogd','User',NULL,NULL,NULL,'user c of group d','user c of group d','ucogd@localhost');

INSERT INTO NBPM_MEMBERSHIP VALUES(10,'architect','hierarchy','group-rd','ae');
INSERT INTO NBPM_MEMBERSHIP VALUES(11,'boss','hierarchy','group-rd','cg');
INSERT INTO NBPM_MEMBERSHIP VALUES(12,'member','hierarchy','group-rd','in');
INSERT INTO NBPM_MEMBERSHIP VALUES(13,'hr-responsible','hierarchy','group-rd','pf');
INSERT INTO NBPM_MEMBERSHIP VALUES(14,'cfo','hierarchy','group-rd','ed');
INSERT INTO NBPM_MEMBERSHIP VALUES(15,'ccaps-responsible','service','group-rd','ed');
INSERT INTO NBPM_MEMBERSHIP VALUES(16,'ccaps-responsible','service','group-ms','ed');
INSERT INTO NBPM_MEMBERSHIP VALUES(17,'group-assignment-test-uaoga','hierarchy2','group A','uaoga');
INSERT INTO NBPM_MEMBERSHIP VALUES(18,'group-assignment-test-uboga','hierarchy2','group A','uboga');
INSERT INTO NBPM_MEMBERSHIP VALUES(19,'group-assignment-test-ucoga','hierarchy2','group A','ucoga');
INSERT INTO NBPM_MEMBERSHIP VALUES(20,'group-assignment-test-uaogb','hierarchy2','group B','uaogb');
INSERT INTO NBPM_MEMBERSHIP VALUES(21,'group-assignment-test-ubogb','hierarchy2','group B','ubogb');
INSERT INTO NBPM_MEMBERSHIP VALUES(22,'group-assignment-test-ucogb','hierarchy2','group B','ucogb');
INSERT INTO NBPM_MEMBERSHIP VALUES(23,'group-assignemnt-test-uaogc','hierarchy2','group C','uaogc');
INSERT INTO NBPM_MEMBERSHIP VALUES(24,'group-assignment-test-ubogc','hierarchy2','group C','ubogc');
INSERT INTO NBPM_MEMBERSHIP VALUES(25,'group-assignment-test-ucogc','hierarchy2','group C','ucogc');
INSERT INTO NBPM_MEMBERSHIP VALUES(26,'group-assignment-test-uaogd','hierarchy2','group D','uaogd');
INSERT INTO NBPM_MEMBERSHIP VALUES(27,'group-assignment-test-ubogd','hierarchy2','group D','ubogd');
INSERT INTO NBPM_MEMBERSHIP VALUES(28,'group-assignment-test-ucogd','hierarchy2','group D','ucogd');
INSERT INTO NBPM_MEMBERSHIP VALUES(40,'user','security','netbpm-domain','ae');
INSERT INTO NBPM_MEMBERSHIP VALUES(41,'developer','security','netbpm-domain','ae');
INSERT INTO NBPM_MEMBERSHIP VALUES(42,'admin','security','netbpm-domain','ae');
INSERT INTO NBPM_MEMBERSHIP VALUES(43,'user','security','netbpm-domain','cg');
INSERT INTO NBPM_MEMBERSHIP VALUES(44,'user','security','netbpm-domain','in');
INSERT INTO NBPM_MEMBERSHIP VALUES(45,'user','security','netbpm-domain','pf');
INSERT INTO NBPM_MEMBERSHIP VALUES(46,'user','security','netbpm-domain','ed');
INSERT INTO NBPM_MEMBERSHIP VALUES(47,'user','security','netbpm-domain','uaoga');
INSERT INTO NBPM_MEMBERSHIP VALUES(48,'user','security','netbpm-domain','uboga');
INSERT INTO NBPM_MEMBERSHIP VALUES(49,'user','security','netbpm-domain','ucoga');
INSERT INTO NBPM_MEMBERSHIP VALUES(50,'user','security','netbpm-domain','uaogb');
INSERT INTO NBPM_MEMBERSHIP VALUES(51,'user','security','netbpm-domain','ubogb');
INSERT INTO NBPM_MEMBERSHIP VALUES(52,'user','security','netbpm-domain','ucogb');
INSERT INTO NBPM_MEMBERSHIP VALUES(53,'user','security','netbpm-domain','uaogc');
INSERT INTO NBPM_MEMBERSHIP VALUES(54,'user','security','netbpm-domain','ubogc');
INSERT INTO NBPM_MEMBERSHIP VALUES(55,'user','security','netbpm-domain','ucogc');
INSERT INTO NBPM_MEMBERSHIP VALUES(56,'user','security','netbpm-domain','uaogd');
INSERT INTO NBPM_MEMBERSHIP VALUES(57,'user','security','netbpm-domain','ubogd');
INSERT INTO NBPM_MEMBERSHIP VALUES(58,'user','security','netbpm-domain','ucogd');