create table insumos (
     id varchar(36) not null,
     nome varchar(255) not null,
     descricao text,
     fabricante varchar(255),
     cnpj_fabricante varchar(14),
     data_fabricacao date,
     data_vencimento date not null,
     lote varchar(100) not null,
     data_criacao datetime not null,
     data_atualizacao datetime,
     primary key(id),
     unique (cnpj_fabricante),
     unique (lote)
);