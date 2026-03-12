-- Parte 2 - SQL
-- Clientes que tienen inscrito algun producto disponible solo en las sucursales que visitan.

SELECT DISTINCT c.nombre
FROM Cliente c
JOIN Inscripcion i
  ON i.idCliente = c.id
WHERE EXISTS (
    SELECT 1
    FROM Visitan v
    JOIN Disponibilidad d
      ON d.idSucursal = v.idSucursal
     AND d.idProducto = i.idProducto
    WHERE v.idCliente = c.id
)
AND NOT EXISTS (
    SELECT 1
    FROM Disponibilidad d2
    WHERE d2.idProducto = i.idProducto
      AND NOT EXISTS (
          SELECT 1
          FROM Visitan v2
          WHERE v2.idCliente = c.id
            AND v2.idSucursal = d2.idSucursal
      )
);
