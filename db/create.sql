create table transaction (id integer not null, timestamp datetime(6), actor varchar(100), type varchar(100), primary key (id)) engine=InnoDB;
create table transaction_data (data_id integer not null auto_increment, transaction_id integer, data_key varchar(255), data_value varchar(255), primary key (data_id)) engine=InnoDB;
alter table transaction_data add constraint FK2fsg2ip18u3lanvkxr8bui6sb foreign key (transaction_id) references transaction (id);
