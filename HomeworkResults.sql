
-- DROP TABLE HomeworkResults

CREATE TABLE HomeworkResults
( UNID                  VARCHAR2(10 BYTE),
  AssignmentNumber      NUMBER(2,0),
  QuestionNumber        NUMBER(2,0),
  Executes              CHAR(1 BYTE),
  MeetsLogicCriteria    CHAR(1 BYTE),
  ResultSetsMatch       CHAR(1 BYTE),
  Score                 NUMBER(18,5),
  InsertedTimeStamp     TIMESTAMP(0),
  UpdatedTimeStamp      TIMESTAMP(0),
  CONSTRAINT PK_HomeworkResults PRIMARY KEY ( UNID, AssignmentNumber, QuestionNumber )
) ;

