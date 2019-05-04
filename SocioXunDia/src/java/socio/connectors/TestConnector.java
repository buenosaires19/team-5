package socio.connectors;

import socio.connectors.ConnectorMySQL;

public class TestConnector {

	public static void main(String[] args) throws Exception {
    
		ConnectorMySQL.getConnection().createStatement().execute(
            "insert into clientes "
            + "(nombre,apellido,tipoDocumento,numeroDocumento,direccion) values "
            + "('Juan','Gomez','DNI',2222222,'Medrano 161')"
        );
        ConnectorMySQL.getConnection().createStatement().execute(
            "insert into clientes "
            + "(nombre,apellido,tipoDocumento,numeroDocumento,direccion) values "
            + "('Laura','Perez','DNI',3333333,'Medrano 163')"
        );
        ConnectorMySQL.getConnection().createStatement().execute(
            "insert into clientes "
            + "(nombre,apellido,tipoDocumento,numeroDocumento,direccion) values "
            + "('Cristina','Mendez','DNI',44444444,'Lima 222')"
        );
        ConnectorMySQL.getConnection().createStatement().execute(
            "insert into clientes "
            + "(nombre,apellido,tipoDocumento,numeroDocumento,direccion) values "
            + "('Diego','Segovia','DNI',5555555,'Medrano 161')"
        );
        ConnectorMySQL.getConnection().createStatement().execute(
            "insert into clientes "
            + "(nombre,apellido,tipoDocumento,numeroDocumento,direccion) values "
            + "('Carlos','Ríos','DNI',66666666,'Medrano 181')"
        );
        ConnectorMySQL.getConnection().createStatement().execute(
            "insert into clientes "
            + "(nombre,apellido,tipoDocumento,numeroDocumento,direccion) values "
            + "('Carolina','Leon','DNI',7777777,'Medrano 161')"
        );
        ConnectorMySQL.getConnection().createStatement().execute(
            "insert into clientes "
            + "(nombre,apellido,tipoDocumento,numeroDocumento,direccion) values "
            + "('Karina','Vargaz','DNI',8888888,'Medrano 161')"
        );
    }
}