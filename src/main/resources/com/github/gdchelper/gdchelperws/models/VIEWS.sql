    CREATE OR REPLACE VIEW View_Atendimento_Score AS 
SELECT atd.*, score
   FROM Atendimento atd join ScoreAtendimento score on atd.id = score.idAtendimento
   WHERE dataInicio > date_sub(now(), INTERVAL 180 DAY)
;

CREATE OR REPLACE VIEW View_Cliente AS 
SELECT Cliente.*, sc.sistemas FROM (
	SELECT Cliente.*, AVG(score) AS score_media FROM Cliente INNER JOIN View_Atendimento_Score Score ON Cliente.codigo = Score.cliente
	GROUP BY codigo
	UNION
	SELECT Cliente.*, 0 AS score_media FROM Cliente
) Cliente   
LEFT JOIN (SELECT codigoCliente, GROUP_CONCAT(descricao SEPARATOR ', ') AS sistemas FROM SistemaContratado GROUP BY codigoCliente) AS sc
ON Cliente.codigo = sc.codigoCliente
GROUP BY codigo;

CREATE OR REPLACE VIEW View_Atendimento_Periodo AS    
    SELECT
        Tecnico.apelido as Tecnico,
        DATE_FORMAT(Atendimento.DataInicio, "%m/%Y") AS Periodo,
        DATE_FORMAT(Atendimento.DataInicio, "%Y-%m") AS Periodo_USA,
        FORMAT(SUM(Atendimento.Segundos / 60 / 60), 2) AS Tempo,
        COUNT(*) AS Quantidade
    FROM
        Atendimento
	INNER JOIN 
		Tecnico ON (Atendimento.tecnico = Tecnico.codigo)
	WHERE 
		Atendimento.DataInicio >= date_sub(now(), INTERVAL 12 MONTH)
    GROUP BY
        Tecnico.apelido, Periodo, Periodo_USA
    ORDER BY
        Periodo_USA DESC;

CREATE OR REPLACE VIEW View_Atendimento_Periodo_Tecnico AS    
    SELECT 
	Tecnico,
	Periodo,
	Periodo_USA,
	FORMAT(Tempo, 2) AS Tempo,
	Quantidade 
    FROM 
	View_Atendimento_Periodo
    UNION
    SELECT 
        '',
        Periodo,
        Periodo_USA,
        FORMAT(SUM(Tempo), 2) AS Tempo,
        SUM(Quantidade) AS Quantidade
    FROM 
        View_Atendimento_Periodo
    GROUP BY 
        Periodo, Periodo_USA;

CREATE OR REPLACE VIEW View_Score_Atendimento AS
SELECT atd.*, score
   FROM Atendimento atd join ScoreAtendimento score on atd.id = score.idAtendimento;

CREATE OR REPLACE VIEW View_Cliente_Atendimento AS
    SELECT 
        Atendimento.dataInicio as Data,
        Atendimento.cliente,
        Atendimento.id,
        Atendimento.mensagem,
        Atendimento.contato as codigoContato,
        Tecnico.Apelido as Tecnico,
        SistemaContratado.descricao as Sistema,
        Contato.nome as Contato,
        scoreatendimento.score as score
    FROM Atendimento
    LEFT JOIN 
            Tecnico ON (Tecnico.codigo = Atendimento.tecnico)
    LEFT JOIN 
            SistemaContratado ON (
                    Atendimento.sistema = SistemaContratado.codigoSistema AND 
            Atendimento.cliente = SistemaContratado.codigoCliente
            )
    LEFT JOIN 
            Contato ON (
                    Atendimento.contato = Contato.codigo AND
            Atendimento.cliente = Contato.codigoCliente
        )	
    LEFT JOIN 
            scoreatendimento ON (Atendimento.id = scoreatendimento.idAtendimento)
    ;

CREATE OR REPLACE VIEW View_Contato AS
SELECT contato.*, IFNULL(scores.score, 0) AS score, MAX(Atendimento.dataInicio) as DataUltimoAtendimento FROM contato 
   LEFT JOIN (SELECT cliente, contato, AVG(score) AS score FROM View_Atendimento_Score GROUP BY cliente, contato) scores ON contato.codigoCliente = scores.cliente AND contato.id = scores.contato
   LEFT JOIN Atendimento ON (Atendimento.contato = Contato.codigo AND Atendimento.cliente = Contato.codigoCliente)
   GROUP BY Contato.codigo, Contato.codigoCliente;

CREATE OR REPLACE VIEW View_Sistema_Contratado AS
SELECT sistema.*, IFNULL(scores.score, 0) AS score FROM sistemacontratado sistema
   LEFT JOIN (SELECT cliente, sistema, AVG(score) AS score FROM View_Atendimento_Score GROUP BY cliente, sistema) scores ON sistema.codigoCliente = scores.cliente AND sistema.codigoSistema = scores.sistema;

Create index atualizacao_cliente_contato_idx ON Atendimento (cliente, contato)  ;