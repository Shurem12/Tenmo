DROP TABLE IF EXISTS tenmo_user, account, transfer;
DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id, seq_transfer_id;
DROP TYPE transfer_status;

BEGIN;

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
	account_id int NOT NULL DEFAULT nextval('seq_account_id') primary key,
	user_id int NOT NULL,
	balance decimal(13, 2) NOT NULL default 1000.00,
	FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id),
	unique (user_id, account_id)
);

-- Sequence to start transfer_id values at 3001 instead of 1
CREATE SEQUENCE seq_transfer_id
  INCREMENT BY 1
  START WITH 3001
  NO MAXVALUE;

create type transfer_status as enum ('CANCELLED','PENDING','APPROVED','REJECTED');
create table transfer(
        transfer_id int not null default nextval('seq_transfer_id') primary key,
        sender_account_id int not null,
        recipient_account_id int not null,
        amount decimal(13, 2) not null,
        status transfer_status not null default 'PENDING',
        transfer_timestamp timestamp not null default current_timestamp,
--        transfer_date date not null default current_date,
--        transfer_time time not null default current_time,
        foreign key (sender_account_id) references account(account_id),
        foreign key (recipient_account_id) references account(account_id),
        unique (transfer_id, sender_account_id, recipient_account_id)
);

COMMIT;
