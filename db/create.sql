create table transaction (id integer not null, timestamp datetime(6), actor varchar(100), type varchar(100), primary key (id)) engine=InnoDB;
create table transaction_data (transaction_id integer not null, data_key varchar(255) not null, data_value varchar(255), primary key (transaction_id, data_key)) engine=InnoDB;
alter table transaction_data add constraint FK2fsg2ip18u3lanvkxr8bui6sb foreign key (transaction_id) references transaction (id);
