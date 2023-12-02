BEGIN TRANSACTION;

DROP TABLE IF EXISTS tenmo_user, account, transfer;

DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id, seq_transfer_id;

-- Sequence to start user_id values at 1001 instead of 1
CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

CREATE TABLE tenmo_user (
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);

-- Sequence to start account_id values at 2001 instead of 1
-- Note: Use similar sequences with unique starting values for additional tables
CREATE SEQUENCE seq_account_id
  INCREMENT BY 1
  START WITH 2001
  NO MAXVALUE;

CREATE TABLE account (
	account_id int NOT NULL DEFAULT nextval('seq_account_id'),
	user_id int NOT NULL,
	balance decimal(13, 2) NOT NULL,
	CONSTRAINT PK_account PRIMARY KEY (account_id),
	CONSTRAINT FK_account_tenmo_user FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id)
);

-- Sequence to start transfer_id values at 3001 instead of 1
CREATE SEQUENCE seq_transfer_id
  INCREMENT BY 1
  START WITH 3001
  NO MAXVALUE;

CREATE TYPE status AS ENUM ('CANCELLED','PENDING','APPROVED','REJECTED');
CREATE table transfer(
        transfer_id int not null default nextval('seq_transfer_id') primary key,
        sender_user_id int not null,
        sender_account_id int not null,
        recipient_user_id int not null,
        recipient_account_id int not null,
        transfer_timestamp timestamp not null,
        amount_sent numeric not null,
        transfer_status status not null,
        foreign key (sender_user_id) references tenmo_user(user_id),
        foreign key (sender_account_id) references account(account_id),
        foreign key (recipient_user_id) references tenmo_user(user_id),
        foreign key (recipient_account_id) references account(account_id),
        unique (transfer_id, sender_account_id, recipient_account_id)
);

COMMIT;
