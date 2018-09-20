-- we don't know how to generate schema tyrannophone (class Schema) :(
create table options
(
	id bigint auto_increment
		primary key,
	connection_price int null,
	is_available bit null,
	monthly_price int null,
	name varchar(255) not null,
	constraint UK_9o64ripk4tkpj6osqw3d3mpcf
	unique (name)
)
;

create table incompatible_options
(
	option_id bigint not null,
	inc_option bigint not null,
	primary key (option_id, inc_option),
	constraint UK_mbobuv3ra5msop612d1d3tm7w
	unique (inc_option),
	constraint FK20sxvmy4avfkj3bshera1jyrv
	foreign key (option_id) references options (id),
	constraint FK65c6p877lpjabsqbb8c2d9ieb
	foreign key (inc_option) references options (id)
)
;

create table plan
(
	id bigint auto_increment
		primary key,
	connection_price int not null,
	is_connection_available bit not null,
	monthly_price int not null,
	plan_name varchar(255) not null,
	constraint UK_hn0vvcykk4bpi9t12ggjbem6a
	unique (plan_name)
)
;

create table contract
(
	contract_number bigint not null
		primary key,
	is_active bit not null,
	plan_id bigint not null,
	constraint FKipgbb01dxgs8lad7kdxsiip76
	foreign key (plan_id) references plan (id)
)
;

create table plan_option
(
	plan_id bigint not null,
	option_id bigint not null,
	primary key (plan_id, option_id),
	constraint FK6jg97wu1oplf491389ny1tx0u
	foreign key (plan_id) references plan (id),
	constraint FKapbls5kcr3cxkpb94jq5pkvhc
	foreign key (option_id) references options (id)
)
;

create table role
(
	role_id bigint auto_increment
		primary key,
	role varchar(255) null,
	constraint UK_bjxn5ii7v7ygwx39et0wawu0q
	unique (role)
)
;

create table user
(
	id bigint auto_increment
		primary key,
	create_ts datetime(6) not null,
	email varchar(255) not null,
	is_active bit not null,
	last_activity_ts datetime(6) null,
	password varchar(255) not null,
	update_ts datetime(6) not null,
	user_name varchar(255) not null,
	constraint UK_lqjrcobrh9jc8wpcar64q1bfh
	unique (user_name),
	constraint UK_ob8kqyqqgmefl0aco34akdtpe
	unique (email)
)
;

create table block_details
(
	id bigint auto_increment
		primary key,
	block_ts datetime(6) not null,
	blocked_before datetime(6) null,
	reason varchar(255) null,
	blocked_by_user_id bigint null,
	constraint FKh931a61y3dpitm2c135nsa65e
	foreign key (blocked_by_user_id) references user (id)
)
;

create table contract_block_details
(
	block_details_id bigint null,
	contract_number bigint not null
		primary key,
	constraint FK4i7p8kln2hr9hibx306xiqmnq
	foreign key (block_details_id) references block_details (id),
	constraint FK9bp9qx8os2qphih1jbhnoerrm
	foreign key (contract_number) references contract (contract_number)
)
;

create table customer
(
	id bigint not null
		primary key,
	address varchar(255) not null,
	first_name varchar(255) not null,
	last_name varchar(255) not null,
	passport varchar(255) not null,
	balance int not null,
	constraint UK_hw2jswlycpuue4ouamem85anm
	unique (passport),
	constraint FK_70051146qn2xmjo5t81exjga0
	foreign key (id) references user (id)
)
;

create table customer_contract
(
	customer_id bigint null,
	contract_number bigint not null
		primary key,
	constraint FK4b1blvkxvyogu79ngc79829y
	foreign key (customer_id) references customer (id),
	constraint FKpt1t13hipgfmg10klghd4urla
	foreign key (contract_number) references contract (contract_number)
)
;

create table employee
(
	id bigint not null
		primary key,
	address varchar(255) not null,
	first_name varchar(255) not null,
	last_name varchar(255) not null,
	passport varchar(255) not null,
	position varchar(255) not null,
	manager_id bigint null,
	constraint UK_fevdc34rl99kapqki0dv54lap
	unique (passport),
	constraint FK_nhli7owi6ferubmgll5umph0
	foreign key (id) references user (id),
	constraint FKou6wbxug1d0qf9mabut3xqblo
	foreign key (manager_id) references employee (id)
)
;

create table employee_contract
(
	employee_id bigint not null,
	contract_number bigint not null,
	primary key (employee_id, contract_number),
	constraint UK_kuuf2o8ri2d3q2th6nmcoa8vh
	unique (contract_number),
	constraint FK7p0cv4illl7dk6sonogs4c9lh
	foreign key (contract_number) references contract (contract_number),
	constraint FKg5xyt16am2rjlyu89c0vg9eq2
	foreign key (employee_id) references employee (id)
)
;

create table user_block_details
(
	block_id bigint null,
	user_id bigint not null
		primary key,
	constraint UK_h34c32dr7rushiriirf3jyyhy
	unique (block_id),
	constraint FK3s364ufeomea78ohlw8wnea26
	foreign key (user_id) references user (id),
	constraint FKgslrqavtixwyhathhowos3o07
	foreign key (block_id) references block_details (id)
)
;

create table user_role
(
	user_id bigint not null,
	role_id bigint not null,
	primary key (user_id, role_id),
	constraint FK859n2jvi8ivhui0rl0esws6o
	foreign key (user_id) references user (id),
	constraint FKa68196081fvovjhkek5m97n3y
	foreign key (role_id) references role (role_id)
)
;

