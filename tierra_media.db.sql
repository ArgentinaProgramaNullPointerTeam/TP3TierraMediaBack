CREATE TABLE IF NOT EXISTS "tipo_atraccion" (
	"id_tipo_atraccion"	INTEGER,
	"nombre"	TEXT NOT NULL CHECK(length("nombre") > 2),
	"status"	INTEGER NOT NULL DEFAULT 1,
	PRIMARY KEY("id_tipo_atraccion" AUTOINCREMENT)
);
INSERT INTO tipo_atraccion(nombre) VALUES
('Aventura'),
('Paisaje'),
('Degustacion');

CREATE TABLE IF NOT EXISTS "usuario" (
	"id_usuario"	INTEGER,
	"nombre"	TEXT NOT NULL CHECK(length("nombre") > 2),
	"dinero_disponible"	INTEGER NOT NULL DEFAULT 0 CHECK("dinero_disponible" >= 0),
	"tiempo_disponible"	INTEGER NOT NULL DEFAULT 0 CHECK("tiempo_disponible" >= 0),
	"id_tipo_atraccion"	INTEGER NOT NULL,
	"is_admin" INTEGER NOT NULL DEFAULT 0,
	"status"	INTEGER NOT NULL DEFAULT 1,
	FOREIGN KEY("id_tipo_atraccion") REFERENCES "tipo_atraccion"("id_tipo_atraccion")
	PRIMARY KEY("id_usuario" AUTOINCREMENT)
);

INSERT INTO usuario(nombre, dinero_disponible, tiempo_disponible, id_tipo_atraccion) VALUES
 ('Eowyn', '10', '8','1'),
 ('Gandalf', '100', '5', '2'),
 ('Sam', '36', '8','3'), 
 ('Galandriel', '5', '6','2'),
 ('Millonario', '1000', '1000', '1');


CREATE TABLE IF NOT EXISTS "atraccion" (
	"id_atraccion"	INTEGER NOT NULL UNIQUE,
	"nombre"	TEXT NOT NULL CHECK(length("nombre") >= 3) UNIQUE,
	"costo_visita"	INTEGER NOT NULL CHECK("costo_visita" > 0),
	"cupo"	INTEGER NOT NULL CHECK("cupo" >= 0),
	"tiempo_visita"	INTEGER NOT NULL CHECK("tiempo_visita" > 0),
	"id_tipo_atraccion"	INTEGER NOT NULL,
	"status"	INTEGER NOT NULL DEFAULT 1,
	FOREIGN KEY("id_tipo_atraccion") REFERENCES "tipo_atraccion"("id_tipo_atraccion")
	PRIMARY KEY("id_atraccion" AUTOINCREMENT)
);

INSERT INTO atraccion(nombre, costo_visita,cupo,tiempo_visita, id_tipo_atraccion) VALUES
 ('Moria', '10', '6', '2', '1'),
 ('Minas Tirith', '5', '25', '2.5', '2'),
 ('La comarca', '3', '150', '6.5', '3'),
 ('Mordor', '25', '4', '3', '1'), 
 ('Abismo de Helm', '5', '15', '2', '2'),
 ('Lothlórien', '35', '30', '1', '3'),
 ('Erebor', '12', '32', '3', '2'), 
 ('Bosque Negro', '3', '12', '4','1'),
 ('La Acotada', '5', '3', '1', '1');


CREATE TABLE IF NOT EXISTS "promocion" (
	"id_promocion"	INTEGER NOT NULL UNIQUE,
	"nombre"	TEXT NOT NULL CHECK(length("nombre") > 3),
	"tipo_promocion"	TEXT NOT NULL,
	"descuento_AXB"	INTEGER CHECK("descuento_AXB" > 0),
	"descuento_porcentual"	INTEGER CHECK("descuento_porcentual" > 0),
	"descuento_absoluta"	INTEGER CHECK("descuento_absoluta" > 0),
	"id_tipo_atraccion"	INTEGER NOT NULL,
	"atraccion1"	INT,
	"atraccion2"	INT,
	"atraccion3"	INT,
	"status"	INTEGER NOT NULL DEFAULT 1,
	PRIMARY KEY("id_promocion" AUTOINCREMENT),
	FOREIGN KEY("id_tipo_atraccion") REFERENCES "tipo_atraccion"("id_tipo_atraccion")
	FOREIGN KEY("descuento_AXB") REFERENCES "atraccion"("id_atraccion")
);



INSERT INTO promocion(nombre,tipo_promocion, descuento_porcentual, id_tipo_atraccion,atraccion1, atraccion2) VALUES
 ('Pack Aventura', 'Porcentual', '0.2','1','8','4');

INSERT INTO promocion(nombre,tipo_promocion, descuento_absoluta, id_tipo_atraccion,atraccion1, atraccion2) VALUES
   ('Pack Degustacion', 'Absoluta', '36', '3','6','3');

INSERT INTO promocion(nombre, tipo_promocion, descuento_AXB, id_tipo_atraccion, atraccion1, atraccion2, atraccion3) VALUES
('Pack Paisaje', 'AXB', 7, '2','2','5','7'),
('Pack Aventura Recargado', 'AXB', 9, '1', '8','4','9');


CREATE TABLE IF NOT EXISTS "itinerario" (
	"id_itinerario"	INTEGER NOT NULL,
	"id_usuario"	INTEGER NOT NULL,
	"id_atraccion"	INTEGER CHECK("id_atraccion" IS null AND "id_promocion" IS NOT null OR "id_atraccion" IS NOT null AND "id_promocion" IS null),
	"id_promocion"	INTEGER CHECK("id_promocion" IS null AND "id_atraccion" IS NOT null OR "id_promocion" IS NOT null AND "id_atraccion" IS null),
	PRIMARY KEY("id_itinerario"),
	FOREIGN KEY("id_usuario") REFERENCES "usuario"("id_usuario"),
	FOREIGN KEY("id_atraccion") REFERENCES "atraccion"("id_atraccion"),
	FOREIGN KEY("id_promocion") REFERENCES "promocion"("id_promocion"),
	UNIQUE("id_itinerario","id_atraccion"),
	UNIQUE("id_itinerario","id_promocion")
);