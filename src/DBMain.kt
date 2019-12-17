import java.sql.Connection

class DBMain(connection: Connection) {
    val conn: Connection = connection

    fun showAll() {
        val statement = conn.createStatement()
        val client = mutableListOf<Client>()
        statement.executeQuery("select * from client").use { resultSet ->
            while (resultSet.next()) {
                val id = resultSet.getInt("id")
                val name = resultSet.getString("name")
                val address = resultSet.getString("address")
                val phone = resultSet.getString("phone")
                val intercomType = resultSet.getString("intercomType")
                val contractDate = resultSet.getString("contractDate")
                val status  = resultSet.getBoolean("status")
                val tariff = resultSet.getInt("tariff")
                val balance = resultSet.getInt("balance")

                println("$id $name $address $phone $intercomType $contractDate $status $tariff $balance")
                client.add(Client(id, name, address, phone, intercomType, contractDate, status, tariff, balance))
            }
        }
        client.forEach { println(it) }
    }

    fun add(name: String?, address: String?, phone: String?, intercomType: String?, contractDate: String?, status: Boolean, tariff: Int, balance: Int ) {
        val ps = conn.prepareStatement("insert INTO client (name, address, phone, intercomType, contractDate, status, tariff, balance) VALUES (?,?,?,?,?,?,?,?)")
        ps.setString(1, name)
        ps.setString(2, address)
        ps.setString(3, phone)
        ps.setString(4, intercomType)
        ps.setString(5, contractDate)
        ps.setBoolean(6, status)
        ps.setInt(7, tariff)
        ps.setInt(8, balance)
        ps.executeUpdate()
    }

    fun delete(id: Int) {
        val ps = conn.prepareStatement("delete from client where id = ?")
        ps.setInt(1, id)
        ps.executeUpdate()
    }

    fun find(id: Int) : Client {
        val ps = conn.prepareStatement("select * from client where id = ?")
        ps.setInt(1, id)
        val rs = ps.executeQuery()
        return if (rs.next()) {
            Client(rs.getInt("id"), rs.getString("name"), rs.getString("address"),
                rs.getString("phone"), rs.getString("intercomType"), rs.getString("contractDate"),
                rs.getBoolean("status"), rs.getInt("tariff"), rs.getInt("balance"))
        } else Client(0,"someone else", "", "", "", "", false, 0, 0)
    }

    fun updateTariff(client: Client, newTariff: Int) {
        val ps = conn.prepareStatement("update client set tariff = ? where id = ?")
        ps.setInt(1, newTariff)
        ps.setInt(2, client.id)
        ps.executeUpdate()
    }
}