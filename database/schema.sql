DROP TABLE IF EXISTS sep_page CASCADE;
DROP TABLE IF EXISTS page_text CASCADE;

DROP SEQUENCE IF EXISTS sep_page_id_seq;

CREATE SEQUENCE sep_page_id_seq
INCREMENT BY 1
NO MAXVALUE
NO MINVALUE
CACHE 1;

CREATE TABLE sep_page (
page_id serial,
page_name varchar(50) NOT NULL,
--author varchar(50) NOT NULL,                    --future improvements
--publish_year integer NOT NULL,                  
CONSTRAINT pk_sep_page_id PRIMARY KEY (page_id)
);

CREATE TABLE page_text (
page_id integer NOT NULL,
page_text text NOT NULL,
page_text_tokens TSVECTOR,
CONSTRAINT fk_page_text_page_id FOREIGN KEY (page_id) REFERENCES sep_page (page_id)
);