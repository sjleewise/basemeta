/* psm 창 열기 

CREATE OR REPLACE TRIGGER m9Session_identity - 리포지토리 접속 세션을 관리 용도임
CREATE OR REPLACE TRIGGER m9Permission_identity - 리포지토리 사용자 권한 관리 용도임
CREATE OR REPLACE TRIGGER m9Profile_identity - 리포지토리 권한정보 관리 용도임
CREATE OR REPLACE TRIGGER m9Option_identity - 리포지토리 사용자정보 관리 용도임

*/

CREATE OR REPLACE TRIGGER m9Profile_identity
BEFORE INSERT
ON m9Profile
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT m9Profile_sequence.nextval INTO :NEW.P_Id FROM dual;
END;

CREATE OR REPLACE TRIGGER m9Permission_identity
BEFORE INSERT
ON m9Permission
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT m9Permission_sequence.nextval INTO :NEW.Permission_Id FROM dual;
END;


CREATE OR REPLACE TRIGGER m9Session_identity
BEFORE INSERT
ON m9Session
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT m9Session_sequence.nextval INTO :NEW.S_Id FROM dual;
SELECT SYSDATE INTO :NEW.S_START FROM DUAL;
END;

CREATE OR REPLACE TRIGGER m9Option_identity
BEFORE INSERT
ON m9Option
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT m9Option_sequence.nextval INTO :NEW.O_Id FROM dual;
END;
