---создание таблицы с вопросами:
CREATE TABLE public.questions (
id_question serial4 PRIMARY KEY,
question_text varchar(1000) NOT NULL
);

SELECT * FROM public.questions;

---создание таблицы с ответами:
CREATE TABLE public.answers (
id serial4 NOT NULL,
answer_text varchar(100) NOT NULL,
is_correct_answer boolean,
id_question integer NOT NULL,
CONSTRAINT id_answer PRIMARY KEY (id),
FOREIGN KEY (id_question) REFERENCES questions (id_question)
);

SELECT * FROM public.answers;

--добавление вопросов и ответов:
INSERT INTO public.questions (question_text) VALUES ('Протоколом TCP/IP транспортного уровня является');
INSERT INTO public.answers (answer_text, is_correct_answer, id_question) VALUES ('TCP', TRUE, 1), ('UDP', TRUE, 1);

INSERT INTO public.questions (question_text) VALUES ('К успешным кодам ответов HTTP относится');
INSERT INTO public.answers (answer_text, is_correct_answer, id_question) VALUES ('100', FALSE, 2), ('303', FALSE, 2), ('500', FALSE, 2), ('201', FALSE, 2);

INSERT INTO public.questions (question_text) VALUES ('Протокол с установкой соединения между узлами');
INSERT INTO public.answers (answer_text, is_correct_answer, id_question) VALUES ('TCP', TRUE, 3), ('UDP', FALSE, 3);


INSERT INTO public.questions (question_text) VALUES ('Какой метод у объекта типа Thread приостанавливает текущий поток');
INSERT INTO public.answers (answer_text, is_correct_answer, id_question) VALUES ('join', TRUE, 4), ('close', FALSE, 4);

INSERT INTO public.questions (question_text) VALUES ('Специальный класс, используемый для перечисления');
INSERT INTO public.answers (answer_text, is_correct_answer, id_question) VALUES ('Enum', TRUE, 5), ('ArrayList', FALSE, 5), ('Map', FALSE, 5);

INSERT INTO public.questions (question_text) VALUES ('Какие виды исключений должны быть обработаны с помощью try-catch');
INSERT INTO public.answers (answer_text, is_correct_answer, id_question) VALUES ('Uncheked', FALSE, 6), ('Checked', TRUE, 5);

--правильные ответы:
SELECT 
t.themes_name AS "Тема",
q.question_text AS "Вопрос",
a.answer_text AS "Правильные ответы"
FROM public.questions q 
LEFT OUTER JOIN public.answers a ON q.id_question = a.id_question 
LEFT OUTER JOIN public.themes t ON t.id_theme = q.id_theme 
WHERE a.is_correct_answer IS TRUE;

---создание таблицы с темами:
CREATE TABLE public.themes (
id_theme serial4 PRIMARY KEY,
themes_name varchar(100) NOT NULL
);

SELECT * FROM public.themes;

--добавление связи вопросов и тем:
ALTER TABLE public.questions ADD COLUMN id_theme int;
ALTER TABLE public.questions ADD CONSTRAINT questions_themes_fk FOREIGN KEY (id_theme) REFERENCES public.themes(id_theme);

--добавление тем:
INSERT INTO public.themes (themes_name) VALUES ('Java Basic'), ('SQL');

--проставление тем:
UPDATE public.questions SET id_theme = 1 WHERE id_question BETWEEN 1 AND 6;

--добавление вопросов по теме SQL:
INSERT INTO public.questions (question_text, id_theme) VALUES ('Какие операторы относятся к DML', 2);
INSERT INTO public.answers (answer_text, is_correct_answer, id_question) VALUES ('INSERT', TRUE, 7), ('UPDATE', TRUE, 7), ('DELETE', TRUE, 7), ('CREATE', FALSE, 7), ('ALTER', FALSE, 7);

INSERT INTO public.questions (question_text, id_theme) VALUES ('Какие операторы относятся к DDL', 2);
INSERT INTO public.answers (answer_text, is_correct_answer, id_question) VALUES ('INSERT', FALSE, 8), ('DROP', TRUE, 8), ('DELETE', FALSE, 8), ('CREATE', TRUE, 8), ('ALTER', TRUE, 8);
