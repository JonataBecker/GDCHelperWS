CREATE OR REPLACE VIEW View_Atendimento_ScoreAS 
SELECT atd.*, score,
       score * (greatest(0, 180 - datediff(now(), dataInicio)) / 180) as score_peso
   FROM atendimento atd join scoreatendimento score on atd.id = score.idAtendimento
;
CREATE OR REPLACE VIEW View_Cliente AS 
SELECT Cliente.* FROM (
	SELECT Cliente.*, AVG(score_peso) AS score_peso FROM cliente INNER JOIN View_Atendimento_Score Score ON Cliente.codigo = Score.cliente
	GROUP BY codigo
	UNION
	SELECT Cliente.*, 0 AS score_peso FROM cliente
) Cliente
GROUP BY codigo
