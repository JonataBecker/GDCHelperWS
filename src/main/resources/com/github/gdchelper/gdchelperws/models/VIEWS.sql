CREATE OR REPLACE VIEW View_Atendimento_Score AS 
SELECT atd.*, score
   FROM atendimento atd join scoreatendimento score on atd.id = score.idAtendimento
   WHERE dataInicio > date_sub(now(), INTERVAL 180 DAY)
;

CREATE OR REPLACE VIEW View_Cliente AS 
SELECT Cliente.* FROM (
	SELECT Cliente.*, AVG(score) AS score_media FROM cliente INNER JOIN View_Atendimento_Score Score ON Cliente.codigo = Score.cliente
	GROUP BY codigo
	UNION
	SELECT Cliente.*, 0 AS score_media FROM cliente
) Cliente
GROUP BY codigo;

CREATE OR REPLACE VIEW View_Atendimento_Periodo_Quantidade AS
    SELECT
        DATE_FORMAT(DataInicio, "%m/%Y") AS Periodo,
        DATE_FORMAT(DataInicio, "%Y-%m") AS Periodo_USA,
        COUNT(*) AS Quantidade
    FROM
        Atendimento
    GROUP BY
        Periodo, Periodo_USA
    ORDER BY
        Periodo_USA DESC
    LIMIT 12;
