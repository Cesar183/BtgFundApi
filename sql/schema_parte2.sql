-- Parte 2 - Esquema SQL relacional (BD: BTG)

-- Crear base de datos
CREATE DATABASE BTG;

CREATE TABLE Cliente (
    id BIGINT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    ciudad VARCHAR(100) NOT NULL
);

CREATE TABLE Sucursal (
    id BIGINT PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    ciudad VARCHAR(100) NOT NULL
);

CREATE TABLE Producto (
    id BIGINT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    tipoProducto VARCHAR(80) NOT NULL
);

CREATE TABLE Inscripcion (
    idProducto BIGINT NOT NULL,
    idCliente BIGINT NOT NULL,
    PRIMARY KEY (idProducto, idCliente),
    CONSTRAINT fk_inscripcion_producto FOREIGN KEY (idProducto) REFERENCES Producto(id),
    CONSTRAINT fk_inscripcion_cliente FOREIGN KEY (idCliente) REFERENCES Cliente(id)
);

CREATE TABLE Disponibilidad (
    idSucursal BIGINT NOT NULL,
    idProducto BIGINT NOT NULL,
    PRIMARY KEY (idSucursal, idProducto),
    CONSTRAINT fk_disponibilidad_sucursal FOREIGN KEY (idSucursal) REFERENCES Sucursal(id),
    CONSTRAINT fk_disponibilidad_producto FOREIGN KEY (idProducto) REFERENCES Producto(id)
);

CREATE TABLE Visitan (
    idSucursal BIGINT NOT NULL,
    idCliente BIGINT NOT NULL,
    fechaVisita DATE NOT NULL,
    PRIMARY KEY (idSucursal, idCliente),
    CONSTRAINT fk_visitan_sucursal FOREIGN KEY (idSucursal) REFERENCES Sucursal(id),
    CONSTRAINT fk_visitan_cliente FOREIGN KEY (idCliente) REFERENCES Cliente(id)
);
