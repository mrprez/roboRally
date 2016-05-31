DROP TABLE CARD IF EXISTS;
DROP TABLE ROBOT IF EXISTS;
DROP TABLE SQUARE IF EXISTS;
DROP TABLE GAME IF EXISTS;
DROP TABLE BOARD IF EXISTS;
DROP TABLE ACCOUNT IF EXISTS;

CREATE TABLE ACCOUNT (
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
	FOREIGN KEY (OWNER) REFERENCES ACCOUNT(USERNAME),
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
	DIRECTION VARCHAR(5) NOT NULL,
	HEALTH INTEGER NOT NULL,
	USER_NAME VARCHAR(32) NULL,
	PRIMARY KEY (NUMBER, GAME_ID),
	FOREIGN KEY (GAME_ID) REFERENCES GAME(ID)
);

CREATE TABLE CARD (
	GAME_ID INTEGER NOT NULL,
	RAPIDITY INTEGER NOT NULL,
	TRANSLATION INTEGER NOT NULL,
	ROTATION INTEGER NOT NULL,
	ROBOT_NB INTEGER NULL,
	DISCARDED BOOLEAN,
	PRIMARY KEY (GAME_ID, RAPIDITY),
	FOREIGN KEY (GAME_ID) REFERENCES GAME(ID),
	FOREIGN KEY (GAME_ID,ROBOT_NB) REFERENCES ROBOT(GAME_ID,NUMBER)
);

--CREATE TABLE ROUND (
--	GAME_ID INTEGER NOT NULL,
--	NUMBER INTEGER NOT NULL,
--	PRIMARY KEY (NUMBER, GAME_ID),
--	FOREIGN KEY (GAME_ID) REFERENCES GAME(ID)
--);
--
--CREATE TABLE TURN (
--	GAME_ID INTEGER NOT NULL,
--	ROUND_NB INTEGER NOT NULL,
--	NUMBER INTEGER NOT NULL,
--	PRIMARY KEY (NUMBER, GAME_ID, NUMBER),
--	FOREIGN KEY (GAME_ID, ROUND_NB) REFERENCES ROUND(GAME_ID, NUMBER)
--);
--
--CREATE TABLE STEP (
--	GAME_ID INTEGER NOT NULL,
--	ROUND_NB INTEGER NOT NULL,
--	TURN_NB INTEGER NOT NULL,
--	NUMBER INTEGER NOT NULL,
--	PRIMARY KEY (NUMBER, GAME_ID, TURN_NB, NUMBER),
--	FOREIGN KEY (GAME_ID, ROUND_NB, TURN_NB) REFERENCES TURN(GAME_ID, ROUND_NB, NUMBER)
--);
--
--CREATE TABLE MOVE (
--	GAME_ID INTEGER NOT NULL,
--	ROUND_NB INTEGER NOT NULL,
--	TURN_NB INTEGER NOT NULL,
--	STEP_NB INTEGER NOT NULL,
--	NUMBER INTEGER NOT NULL,
--	ROBOT_NB INTEGER NOT NULL,
--	ROTATION INTEGER NULL,
--	TRANSLATION INTEGER NULL,
--	PRIMARY KEY (NUMBER, GAME_ID, TURN_NB, STEP_NB, NUMBER),
--	FOREIGN KEY (GAME_ID, ROUND_NB, TURN_NB, STEP_NB) REFERENCES STEP(GAME_ID, ROUND_NB, TURN_NB, NUMBER)
--);

INSERT INTO ACCOUNT(USERNAME,PASSWORD) VALUES('mrprez', '135db287cad29417f6cdea20b7e359a3');
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

INSERT INTO ROBOT(NUMBER,GAME_ID,X,Y,DIRECTION,HEALTH,USER_NAME) VALUES (1,0,1,1,'UP',9,'mrprez');
INSERT INTO ROBOT(NUMBER,GAME_ID,X,Y,DIRECTION,HEALTH,USER_NAME) VALUES (2,0,2,1,'RIGHT',9,null);

INSERT INTO CARD(GAME_ID,RAPIDITY,TRANSLATION,ROTATION,ROBOT_NB,DISCARDED) VALUES (0,1, 1, 0,1,false);
INSERT INTO CARD(GAME_ID,RAPIDITY,TRANSLATION,ROTATION,ROBOT_NB,DISCARDED) VALUES (0,2, 2, 0,1,false);
INSERT INTO CARD(GAME_ID,RAPIDITY,TRANSLATION,ROTATION,ROBOT_NB,DISCARDED) VALUES (0,3, 3, 0,1,false);
INSERT INTO CARD(GAME_ID,RAPIDITY,TRANSLATION,ROTATION,ROBOT_NB,DISCARDED) VALUES (0,4,-1, 0,1,false);
INSERT INTO CARD(GAME_ID,RAPIDITY,TRANSLATION,ROTATION,ROBOT_NB,DISCARDED) VALUES (0,5, 0, 1,1,false);
INSERT INTO CARD(GAME_ID,RAPIDITY,TRANSLATION,ROTATION,ROBOT_NB,DISCARDED) VALUES (0,6, 0, 2,1,false);
INSERT INTO CARD(GAME_ID,RAPIDITY,TRANSLATION,ROTATION,ROBOT_NB,DISCARDED) VALUES (0,7, 0,-1,1,false);
INSERT INTO CARD(GAME_ID,RAPIDITY,TRANSLATION,ROTATION,ROBOT_NB,DISCARDED) VALUES (0,8, 1, 0,1,false);
INSERT INTO CARD(GAME_ID,RAPIDITY,TRANSLATION,ROTATION,ROBOT_NB,DISCARDED) VALUES (0,9, 2, 0,1,false);
