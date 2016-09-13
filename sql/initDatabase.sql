DROP TABLE ROBOT_STATE IF EXISTS;
DROP TABLE MOVE IF EXISTS;
DROP TABLE ACTION IF EXISTS;
DROP TABLE INVITATION IF EXISTS;
DROP TABLE CARD IF EXISTS;
DROP TABLE ROBOT IF EXISTS;
DROP TABLE TARGET IF EXISTS;
DROP TABLE SQUARE IF EXISTS;
DROP TABLE GAME IF EXISTS;
DROP TABLE BOARD IF EXISTS;
DROP TABLE ACCOUNT IF EXISTS;

CREATE TABLE ACCOUNT (
	USERNAME VARCHAR(32) NOT NULL PRIMARY KEY,
	PASSWORD VARCHAR(32) NOT NULL,
	E_MAIL VARCHAR(128) NOT NULL
);

CREATE TABLE BOARD (
	ID INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
	TYPE VARCHAR(5) NOT NULL,
	SIZE_X INTEGER NOT NULL,
	SIZE_Y INTEGER NOT NULL
);

CREATE TABLE BUILDING_BOARD (
	BOARD_ID INTEGER NOT NULL PRIMARY KEY,
	OWNER VARCHAR(30) NOT NULL,
	FOREIGN KEY (BOARD_ID) REFERENCES BOARD(ID),
	FOREIGN KEY (OWNER) REFERENCES ACCOUNT(USERNAME)
);

CREATE TABLE GAME (
	ID INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
	OWNER VARCHAR(30) NOT NULL,
	NAME VARCHAR(32) NOT NULL,
	STATE VARCHAR(16) NOT NULL,
	BOARD_ID INTEGER NOT NULL,
	FOREIGN KEY (OWNER) REFERENCES ACCOUNT(USERNAME),
	FOREIGN KEY (BOARD_ID) REFERENCES BOARD(ID)
);

CREATE TABLE INVITATION (
	GAME_ID INTEGER NOT NULL,
	E_MAIL VARCHAR(128) NOT NULL,
	TOKEN VARCHAR(32) NOT NULL,
	PRIMARY KEY (GAME_ID, E_MAIL),
	FOREIGN KEY (GAME_ID) REFERENCES GAME(ID)
);

CREATE TABLE SQUARE (
	BOARD_ID INTEGER NOT NULL,
	X INTEGER NOT NULL,
	Y INTEGER NOT NULL,
	CLASS VARCHAR(128) NOT NULL,
	ARGS VARCHAR(64) NULL,
	WALL_UP BOOLEAN NOT NULL,
	WALL_DOWN BOOLEAN NOT NULL,
	WALL_LEFT BOOLEAN NOT NULL,
	WALL_RIGHT BOOLEAN NOT NULL,
	PRIMARY KEY (BOARD_ID, X, Y)
);

CREATE TABLE TARGET (
	BOARD_ID INTEGER NOT NULL,
	TARGET_NB INTEGER NOT NULL,
	X INTEGER NOT NULL,
	Y INTEGER NOT NULL,
	PRIMARY KEY (BOARD_ID, TARGET_NB),
	FOREIGN KEY (BOARD_ID,X,Y) REFERENCES SQUARE(BOARD_ID,X,Y)
);

CREATE TABLE ROBOT (
	NUMBER INTEGER NOT NULL,
	GAME_ID INTEGER NOT NULL,
	X INTEGER NULL,
	Y INTEGER NULL,
	DIRECTION VARCHAR(5) NOT NULL,
	HEALTH INTEGER NOT NULL,
	GHOST BOOLEAN NOT NULL,
	POWER_DOWN_STATE VARCHAR(32) NOT NULL,
	TARGET_NB INTEGER NULL,
	USER_NAME VARCHAR(32) NULL,
	HAS_PLAYED BOOLEAN DEFAULT FALSE NOT NULL,
	PRIMARY KEY (NUMBER, GAME_ID),
	FOREIGN KEY (GAME_ID) REFERENCES GAME(ID)
);

CREATE TABLE CARD (
	GAME_ID INTEGER NOT NULL,
	RAPIDITY INTEGER NOT NULL,
	TRANSLATION INTEGER NOT NULL,
	ROTATION INTEGER NOT NULL,
	ROBOT_NB INTEGER NULL,
	CARD_INDEX INTEGER NULL,
	DISCARDED BOOLEAN NOT NULL,
	PRIMARY KEY (GAME_ID, RAPIDITY),
	FOREIGN KEY (GAME_ID) REFERENCES GAME(ID),
	FOREIGN KEY (GAME_ID,ROBOT_NB) REFERENCES ROBOT(GAME_ID,NUMBER)
);

CREATE TABLE ACTION (
	GAME_ID INTEGER NOT NULL,
	ROUND_NB INTEGER NOT NULL,
	STAGE_NB INTEGER NOT NULL,
	ACTION_NB INTEGER NOT NULL,
	CARD_RAPIDITY INTEGER NULL,
	SQUARE_X INTEGER NULL,
	SQUARE_Y INTEGER NULL,
	PRIMARY KEY (GAME_ID, ROUND_NB, STAGE_NB, ACTION_NB),
	FOREIGN KEY (GAME_ID) REFERENCES GAME(ID),
	FOREIGN KEY (GAME_ID, CARD_RAPIDITY) REFERENCES CARD(GAME_ID, RAPIDITY)
	-- No foreign key for SQUARE because link is not on only one table
);

CREATE TABLE MOVE (
	GAME_ID INTEGER NOT NULL,
	ROUND_NB INTEGER NOT NULL,
	STAGE_NB INTEGER NOT NULL,
	ACTION_NB INTEGER NOT NULL,
	STEP_NB INTEGER NOT NULL,
	MOVE_NB INTEGER NOT NULL,
	ROBOT_NB INTEGER NOT NULL,
	TYPE VARCHAR(32) NOT NULL,
	ARGS VARCHAR(128) NULL,
	PRIMARY KEY (GAME_ID, ROUND_NB, STAGE_NB, ACTION_NB, STEP_NB, MOVE_NB),
	FOREIGN KEY (GAME_ID, ROUND_NB, STAGE_NB, ACTION_NB) REFERENCES ACTION(GAME_ID, ROUND_NB, STAGE_NB, ACTION_NB)
);

CREATE TABLE ROBOT_STATE (
	GAME_ID INTEGER NOT NULL,
	ROBOT_NB INTEGER NOT NULL,
	ROUND_NB INTEGER NOT NULL,
	X INTEGER NULL,
	Y INTEGER NULL,
	DIRECTION VARCHAR(5) NOT NULL,
	HEALTH INTEGER NOT NULL,
	GHOST BOOLEAN NOT NULL,
	POWER_DOWN_STATE VARCHAR(32) NOT NULL,
	PRIMARY KEY (GAME_ID, ROBOT_NB, ROUND_NB),
	FOREIGN KEY (GAME_ID, ROBOT_NB) REFERENCES ROBOT (GAME_ID, NUMBER)
);

INSERT INTO ACCOUNT(USERNAME,PASSWORD,E_MAIL) VALUES('mrprez', '135db287cad29417f6cdea20b7e359a3', 'guillaume.gagneret@gmail.com');
