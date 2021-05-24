TRUNCATE TABLE USER_DETAILS;
TRUNCATE TABLE CLIENT_METADATA;

 
INSERT INTO CLIENT_METADATA(ID,client_code,client_name,logo_url,service_url) VALUES(1,'CL01', 'Client 1','file:d/om/logo','http://omob/test');
INSERT INTO USER_DETAILS(ID,client_metadata_id,username,password,admin) VALUES(1,1,'katherbasha','$2a$10$.wExXtpCZ8wGUaTuyiZ4hO.Bk6.3SVcL9J9UMZe1Vl1OCGF2hPHsa',1);