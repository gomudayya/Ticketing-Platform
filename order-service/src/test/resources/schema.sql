create table orders
(
    created_time datetime(6)                                     null,
    id           bigint auto_increment
        primary key,
    show_id      bigint                                          null,
    updated_time datetime(6)                                     null,
    user_id      bigint                                          null,
    order_status enum ('CANCEL', 'PENDING', 'REFUND', 'SUCCESS') null
);

create table payment
(
    amount         int         null,
    payment_status tinyint     null,
    created_time   datetime(6) null,
    id             bigint auto_increment
        primary key,
    order_id       bigint      null,
    updated_time   datetime(6) null,
    constraint FKlouu98csyullos9k25tbpk4va
        foreign key (order_id) references orders (id),
    check (`payment_status` between 0 and 1)
);

create table ticket
(
    is_deleted    bit                                    not null,
    price         int                                    null,
    created_time  datetime(6)                            null,
    id            bigint auto_increment
        primary key,
    updated_time  datetime(6)                            null,
    user_id       bigint                                 null,
    code          varchar(255)                           null,
    ticket_status enum ('AVAILABLE', 'SELECTED', 'SOLD') null
);

create table order_ticket
(
    created_time datetime(6) null,
    id           bigint auto_increment
        primary key,
    order_id     bigint      null,
    ticket_id    bigint      null,
    updated_time datetime(6) null,
    constraint FK5wkauw4f43qi6wxx4femc1q8f
        foreign key (order_id) references orders (id),
    constraint FKar99sc5nmwtkgpw22pnijl5sd
        foreign key (ticket_id) references ticket (id)
);