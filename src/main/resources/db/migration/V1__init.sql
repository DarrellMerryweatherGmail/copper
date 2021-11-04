create table if not exists account (
    id character varying(20) not null primary key,
    type character varying(255),
    username character varying(255),
    email character varying(255)
)


