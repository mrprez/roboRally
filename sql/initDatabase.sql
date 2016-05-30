DROP TABLE GAME IF EXISTS;
DROP TABLE BOARD IF EXISTS;
DROP TABLE USER IF EXISTS;

CREATE TABLE USER (
	USERNAME VARCHAR(32) NOT NULL PRIMARY KEY,
	PASSWORD VARCHAR(32) NOT NULL
);

CREATE TABLE BOARD (
	ID INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
	TYPE VARCHAR(5) NOT NULL,
	SIZE_X INTEGER NOT NULL,
	SIZE_Y INTEGER NOT NULL,
	START_X INTEGER NOT NULL,
	START_Y INTEGER NOT NULL
);

CREATE TABLE GAME (
	ID INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
	OWNER VARCHAR(30) NOT NULL,
	NAME VARCHAR(32) NOT NULL,
	BOARD_ID INTEGER NOT NULL,
	FOREIGN KEY (OWNER) REFERENCES USER(USERNAME),
	FOREIGN KEY (BOARD_ID) REFERENCES BOARD(ID)
);

CREATE TABLE SQUARE (
	BOARD_ID INTEGER NOT NULL,
	X INTEGER NOT NULL,
	Y INTEGER NOT NULL,
	CLASS VARCHAR(128) NOT NULL,
	ARGS VARCHAR(64) NULL,
	PRIMARY KEY (BOARD_ID, X, Y)
);

CREATE TABLE ROBOT (
	NUMBER INTEGER NOT NULL,
	GAME_ID INTEGER NOT NULL,
	X INTEGER NOT NULL,
	Y INTEGER NOT NULL,
	HEALTH INTEGER NOT NULL,
	USER VARCHAR(32) NULL,
	PRIMARY KEY (NUMBER, GAME_ID)
);

INSERT INTO USER(USERNAME,PASSWORD) VALUES('mrprez', '135db287cad29417f6cdea20b7e359a3');
INSERT INTO BOARD(TYPE,SIZE_X,SIZE_Y,START_X,START_Y) VALUES ('Game', 4, 4, 0, 0);
INSERT INTO BOARD(TYPE,SIZE_X,SIZE_Y,START_X,START_Y) VALUES ('Game', 32, 16, 2, 2);
INSERT INTO GAME(OWNER,NAME,BOARD_ID) VALUES ('mrprez', 'test0', 0);
INSERT INTO GAME(OWNER,NAME,BOARD_ID) VALUES ('mrprez', 'test1', 1);


INSERT INTO SQUARE(BOARD_ID,X,Y,CLASS,ARGS) VALUES (0,0,0,'com.mrprez.roborally.model.square.EmptySquare',null);
INSERT INTO SQUARE(BOARD_ID,X,Y,CLASS,ARGS) VALUES (0,0,1,'com.mrprez.roborally.model.square.EmptySquare',null);
INSERT INTO SQUARE(BOARD_ID,X,Y,CLASS,ARGS) VALUES (0,0,2,'com.mrprez.roborally.model.square.EmptySquare',null);
INSERT INTO SQUARE(BOARD_ID,X,Y,CLASS,ARGS) VALUES (0,0,3,'com.mrprez.roborally.model.square.EmptySquare',null);
INSERT INTO SQUARE(BOARD_ID,X,Y,CLASS,ARGS) VALUES (0,1,0,'com.mrprez.roborally.model.square.EmptySquare',null);
INSERT INTO SQUARE(BOARD_ID,X,Y,CLASS,ARGS) VALUES (0,1,1,'com.mrprez.roborally.model.square.EmptySquare',null);
INSERT INTO SQUARE(BOARD_ID,X,Y,CLASS,ARGS) VALUES (0,1,2,'com.mrprez.roborally.model.square.EmptySquare',null);
INSERT INTO SQUARE(BOARD_ID,X,Y,CLASS,ARGS) VALUES (0,1,3,'com.mrprez.roborally.model.square.EmptySquare',null);
INSERT INTO SQUARE(BOARD_ID,X,Y,CLASS,ARGS) VALUES (0,2,0,'com.mrprez.roborally.model.square.EmptySquare',null);
INSERT INTO SQUARE(BOARD_ID,X,Y,CLASS,ARGS) VALUES (0,2,1,'com.mrprez.roborally.model.square.EmptySquare',null);
INSERT INTO SQUARE(BOARD_ID,X,Y,CLASS,ARGS) VALUES (0,2,2,'com.mrprez.roborally.model.square.EmptySquare',null);
INSERT INTO SQUARE(BOARD_ID,X,Y,CLASS,ARGS) VALUES (0,2,3,'com.mrprez.roborally.model.square.EmptySquare',null);
INSERT INTO SQUARE(BOARD_ID,X,Y,CLASS,ARGS) VALUES (0,3,0,'com.mrprez.roborally.model.square.EmptySquare',null);
INSERT INTO SQUARE(BOARD_ID,X,Y,CLASS,ARGS) VALUES (0,3,1,'com.mrprez.roborally.model.square.EmptySquare',null);
INSERT INTO SQUARE(BOARD_ID,X,Y,CLASS,ARGS) VALUES (0,3,2,'com.mrprez.roborally.model.square.EmptySquare',null);
INSERT INTO SQUARE(BOARD_ID,X,Y,CLASS,ARGS) VALUES (0,3,3,'com.mrprez.roborally.model.square.EmptySquare',null);

INSERT INTO ROBOT(NUMBER,GAME_ID,X,Y,HEALTH) VALUES (1,0,1,1,9);
INSERT INTO ROBOT(NUMBER,GAME_ID,X,Y,HEALTH) VALUES (2,0,2,1,9);
