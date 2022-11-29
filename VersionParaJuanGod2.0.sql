-- create database proyecto;
use test;
select * from USUARIO;
select * from bitacora;
-- use sql10579893;
create table USUARIO
(
   ID_USUARIO           int not null AUTO_INCREMENT,
   ID_PERFIL            int,
   PASSWORD             varchar(32) not null,
   NOMBREUSUARIO        varchar(32) not null,
   ESADMIN              bool not null default false,
   ESACTIVO             bool not null default false,
   CARRERA				varchar(32),
   CREADO               TIMESTAMP not null default NOW(),
   primary key (ID_USUARIO)
   /*,FOREIGN KEY (ID_PERFIL) REFERENCES PERFIL(ID_PERFIL)*/	
);
insert into USUARIO (PASSWORD,NOMBREUSUARIO,ESADMIN,CARRERA) values ("admin","admin",true,"Moderador");
create table RECUPERARCONTRA
(
   ID_SOLICITUD         int not null AUTO_INCREMENT,
   ID_USUARIO           int not null,
   CODIGO               varchar(50) not null,
   SOLICITUDCRE         TIMESTAMP not null default NOW(),
   primary key (ID_SOLICITUD)
   /*,FOREIGN KEY (ID_USUARIO) REFERENCES USUARIO(ID_USUARIO)*/	
);
/*
create table PAIS
(
   ID_PAIS              varchar(3) not null,
   NOMBREPAIS           varchar(30) not null,
   primary key (ID_PAIS)
);*/
create table PERFIL
(
   ID_PERFIL            int not null AUTO_INCREMENT,
   ID_PAIS              varchar(5) default null,
   CUMPLEANOS		    TIMESTAMP,
   GENERO               varchar(1) not null,
   BIOGRAFIA            text(1500),
   TELEFONO             varchar(20), -- poendiente el cambio a solo null--
   DIRECCION            varchar(60),
   EMAILPUBLICO         varchar(60), -- poendiente el cambio a solo null--
   primary key (ID_PERFIL)
   /*,FOREIGN KEY (ID_PAIS) REFERENCES PAIS(ID_PAIS)*/	
);
create table POST
/*alter table POST modify column CREADOPOST TIMESTAMP not null default NOW()*/
(
   ID_POST              int not null AUTO_INCREMENT,
   ID_PERFIL            int not null, -- deberia ser ID_USUARIO--
   ID_FORO              int not null,
   CONTENIDO            text(1500) not null,
   TITULO               varchar(32) not null,
   ENABLED		bool not null default false,
   CREADOPOST           TIMESTAMP not null default NOW(),
   primary key (ID_POST)
   /*,FOREIGN KEY (ID_PERFIL) REFERENCES PERFIL(ID_PERFIL) -- Y ACA referenciar a USUARIO(ID_USUARIO)*/	
   /*,FOREIGN KEY (ID_FORO) REFERENCES FORO(ID_FORO)*/	
);
create table NOTICIA
(
   ID_NOTICIA           int not null AUTO_INCREMENT,
   ID_POST 		int not null,
   primary key (ID_NOTICIA)
   /*,FOREIGN KEY (ID_POST) REFERENCES POST(ID_POSTL)*/
);
/*alter table EVENTO modify column CREADOEVENTOS TIMESTAMP not null default NOW()*/

create table FORO
(
   ID_FORO            	int not null AUTO_INCREMENT,
   FECHA                timestamp default NOW(), -- yo lo tengo como not null pero la verdad es que no importa (siempre dejamos el dato para el NOW) --
   NOMBRE               varchar(32) not null,
   CATEGORIA 		varchar(32) not null,
   primary key (ID_FORO)
);

insert into FORO (NOMBRE,CATEGORIA) values ("Deportes","Deportes");

create table LIKES 
(
	ID_LIKES  		int not null AUTO_INCREMENT,
	ID_USUARIO		int not null,
	ID_POST			int not null,
	primary key (ID_LIKES)
	/*,FOREIGN KEY (ID_USUARIO) REFERENCES USUARIO(ID_USUARIO)*/
	/*,FOREIGN KEY (ID_POST) REFERENCES POST(ID_POST)*/
);
create table COMENTARIO
(
/*alter table COMENTARIO modify column CREADOCOM TIMESTAMP not null default NOW()*/
	ID_COMENTARIO		int not null AUTO_INCREMENT,
	ID_USUARIO		int not null,
	ID_POST 			int not null,
	CREADOCOM        	TIMESTAMP not null default NOW(),
	CONTENIDO  		varchar(32),
	primary key (ID_COMENTARIO)
	/*,FOREIGN KEY (ID_USUARIO) REFERENCES USUARIO(ID_USUARIO)*/
    /*,FOREIGN KEY (ID_POST) REFERENCES POST(ID_POST)*/
);

/*ID_USUARIO M SIGNIFICA ID_USUARIO MENSAJERO (o que envia la solicitud), ID_USUARIOR SIGINIFICA ID_USUARIO RECEPTOR de la solicitud*/
create table AMIGO
(	
	ID_AMIGO		int not null AUTO_INCREMENT,
	ID_USUARIOM		int not null,
	ID_USUARIOR		int not null,
	primary key (ID_AMIGO)	
);
create table POST_GUARDADO
(
	ID_POSTGUARDADO	int not null auto_increment,
	ID_USUARIO		int not null,
	ID_POST			int not null,
	primary key (ID_POSTGUARDADO)
);
/*
/* BITACORA */
DROP TABLE IF EXISTS BITACORA;
create table BITACORA
(
	ID_BITACORA		int not null AUTO_INCREMENT,
	FECHA			datetime,
	TABLA			varchar(20) DEFAULT NULL,
	excutedSQL		varchar(2000) DEFAULT NULL,
	reverseSQL 		varchar(2000) DEFAULT NULL,
	primary key (ID_BITACORA)
);
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
/*INSERT POST TRIGGER*/
DROP TRIGGER IF EXISTS after_insert_post;
create TRIGGER after_insert_post
	AFTER INSERT ON POST
	FOR EACH ROW
	insert into BITACORA (FECHA, TABLA, excutedSQL, reverseSQL)
	values(
		now(),"POST",
		CONCAT("INSERT INTO POST (ID_POST, ID_PERFIL, ID_FORO, CONTENIDO, TITULO, ENABLED, CREADOPOST) VALUES (",NEW.ID_POST,",",NEW.ID_PERFIL,",",NEW.ID_FORO,",""",NEW.CONTENIDO,""",""",NEW.TITULO,""",",NEW.ENABLED,",""",NEW.CREADOPOST,""");"),
		CONCAT("DELETE FROM POST WHERE ID_POST = ", NEW.ID_POST,";")
	);
    
/*UPDATE POST TRIGGER*/ -- Este update trigger fué arreglado pero los demás no --
DROP TRIGGER IF EXISTS after_update_post;
create TRIGGER after_update_post
	AFTER UPDATE ON POST
	FOR EACH ROW
	insert into BITACORA(FECHA, TABLA, excutedSQL, reverseSQL)
	values(
		now(),"POST",
		CONCAT("UPDATE POST set  ID_PERFIL=",NEW.ID_PERFIL,",ID_FORO=",NEW.ID_FORO,",CONTENIDO=""",NEW.CONTENIDO,""",TITULO=""",NEW.TITULO,""",ENABLED=",NEW.ENABLED,",CREADOPOST=""",NEW.CREADOPOST,""" where ID_POST = ",NEW.ID_POST,";"),
		CONCAT("UPDATE POST set  ID_PERFIL=",OLD.ID_PERFIL,",ID_FORO=",OLD.ID_FORO,",CONTENIDO=""",OLD.CONTENIDO,""",TITULO=""",OLD.TITULO,""",ENABLED=",OLD.ENABLED,",CREADOPOST=""",OLD.CREADOPOST,""" where ID_POST = ",OLD.ID_POST,";")
	);
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

/*INSERT USUARIO TRIGGER*/
DROP TRIGGER IF EXISTS after_insert_usuario;
create TRIGGER after_insert_usuario
	AFTER INSERT ON USUARIO
	FOR EACH ROW
	insert into BITACORA(FECHA, TABLA, excutedSQL, reverseSQL)
	values(
		now(),"USUARIO",
		CONCAT("INSERT INTO USUARIO (ID_USUARIO, ID_PERFIL, PASSWORD, NOMBREUSUARIO, ESADMIN, ESACTIVO, CARRERA, CREADO) VALUES (",NEW.ID_USUARIO,",","null",",""",NEW.PASSWORD,""",""",NEW.NOMBREUSUARIO,""",",NEW.ESADMIN,",",NEW.ESACTIVO,",""",NEW.CARRERA,""",""",NEW.CREADO,""");"),
		CONCAT("DELETE FROM USUARIO WHERE ID_USUARIO = ", NEW.ID_USUARIO,";")
	);-- Acá habia un error con NEW.ID_PERFIL --
/*DELETE USUARIO TRIGGER*/
DROP TRIGGER IF EXISTS after_delete_usuario;
create TRIGGER after_delete_usuario
	AFTER DELETE ON USUARIO
	FOR EACH ROW
	insert into BITACORA(FECHA, TABLA, excutedSQL, reverseSQL)
	values(
		now(),"USUARIO",
		CONCAT("DELETE FROM USUARIO WHERE ID_USUARIO = ", OLD.ID_USUARIO,";"),
		CONCAT("INSERT INTO USUARIO (ID_USUARIO, ID_PERFIL, PASSWORD, NOMBREUSUARIO, ESADMIN, ESACTIVO, CARRERA, CREADO) VALUES (",OLD.ID_USUARIO,",","null",",""",OLD.PASSWORD,""",""",OLD.NOMBREUSUARIO,""",",OLD.ESADMIN,",",OLD.ESACTIVO,",""",OLD.CARRERA,""",""",OLD.CREADO,""");")
	);-- Quiete entonces old.ID_PERFIL --
    
/*UPDATE USUARIO TRIGGER*/ -- Este trigger update fué arreglado pero los demás update no --
DROP TRIGGER IF EXISTS after_update_usuario;
create TRIGGER after_update_usuario
	AFTER UPDATE ON USUARIO
	FOR EACH ROW
	insert into BITACORA(FECHA, TABLA, excutedSQL, reverseSQL)
	values(
		now(),"USUARIO",
		CONCAT("UPDATE USUARIO set  ID_PERFIL=",0,",PASSWORD=""",NEW.PASSWORD,""",NOMBREUSUARIO=""",NEW.NOMBREUSUARIO,""",ESADMIN=",NEW.ESADMIN,",ESACTIVO=",NEW.ESACTIVO,",CARRERA=""",NEW.CARRERA,""",CREADO=""",NEW.CREADO,""" where ID_USUARIO = ",NEW.ID_USUARIO,";"),
		CONCAT("UPDATE USUARIO set  ID_PERFIL=",0,",PASSWORD=""",OLD.PASSWORD,""",NOMBREUSUARIO=""",OLD.NOMBREUSUARIO,""",ESADMIN=",OLD.ESADMIN,",ESACTIVO=",OLD.ESACTIVO,",CARRERA=""",OLD.CARRERA,""",CREADO=""",OLD.CREADO,""" where ID_USUARIO = ",OLD.ID_USUARIO,";")
	);    -- old idperfil y new idperfil retirados por erroes aveces aparecina y aveces no pero no se porque aveces no--

/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

select * from bitacora;
/*INSERT PERFIL TRIGGER*/
DROP TRIGGER IF EXISTS after_insert_perfil;
create TRIGGER after_insert_perfil
	AFTER INSERT ON PERFIL
	FOR EACH ROW
	insert into BITACORA(FECHA, TABLA, excutedSQL, reverseSQL)
	values(
		now(),"PERFIL",
		CONCAT("INSERT INTO PERFIL(ID_PERFIL, ID_PAIS, CUMPLEANOS, GENERO, BIOGRAFIA, TELEFONO, DIRECCION, EMAILPUBLICO) VALUES (",NEW.ID_PERFIL,",""",""",""",NEW.CUMPLEANOS,""",""",NEW.GENERO,""",""",NEW.BIOGRAFIA,""",""",NEW.TELEFONO,""",""",NEW.DIRECCION,""",""",NEW.EMAILPUBLICO,""");"),
		CONCAT("DELETE FROM PERFIL WHERE ID_PERFIL = ", NEW.ID_PERFIL,";")
	);
-- Algo sucedia con NEw.ID_PAIS --
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/


/*INSERT FORO TRIGGER*/
DROP TRIGGER IF EXISTS after_insert_foro;
create TRIGGER after_insert_foro
	AFTER INSERT ON FORO
	FOR EACH ROW
	insert into BITACORA(FECHA, TABLA, excutedSQL, reverseSQL)
	values(
		now(),"EVENTO",
		CONCAT("INSERT INTO FORO(ID_FORO, FECHA, NOMBRE, CATEGORIA) VALUES (",NEW.ID_FORO,",""",NEW.FECHA,""",""",NEW.NOMBRE,""",""",NEW.FECHA,""",""",NEW.CATEGORIA,""");"),
		CONCAT("DELETE FROM FORO WHERE ID_FORO = ", NEW.ID_FORO,";")
	);


/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/


/*INSERT LIKES TRIGGER*/
DROP TRIGGER IF EXISTS after_insert_likes;
create TRIGGER after_insert_likes
	AFTER INSERT ON LIKES
	FOR EACH ROW
	insert into BITACORA(FECHA, TABLA, excutedSQL, reverseSQL)
	values(
		now(),"LIKES",
		CONCAT("INSERT INTO LIKES(ID_LIKES, ID_USUARIO, ID_POST) VALUES (",NEW.ID_LIKES,",",NEW.ID_USUARIO,",",NEW.ID_POST,");"),
		CONCAT("DELETE FROM LIKES WHERE ID_LIKES = ", NEW.ID_LIKES,";")
	);

/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/


/*INSERT COMENTARIO TRIGGER*/
DROP TRIGGER IF EXISTS after_insert_comentario;
create TRIGGER after_insert_comentario
	AFTER INSERT ON COMENTARIO
	FOR EACH ROW
	insert into BITACORA(FECHA, TABLA, excutedSQL, reverseSQL)
	values(
		now(),"COMENTARIO",
		CONCAT("INSERT INTO COMENTARIO (ID_COMENTARIO, ID_USUARIO, ID_POST, CREADOCOM, CONTENIDO) VALUES (",NEW.ID_COMENTARIO,",",NEW.ID_USUARIO,",",NEW.ID_POST,",""",NEW.CREADOCOM,""",""",NEW.CONTENIDO,""");"),
		CONCAT("DELETE FROM COMENTARIO WHERE ID_COMENTARIO  = ", NEW.ID_COMENTARIO,";")
	);

/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
