import java.nio.file.Files
import java.nio.file.Paths
import java.sql.DriverManager
import java.util.*

fun main() {
    val props = Properties()
    props.load(Files.newBufferedReader(Paths.get("props.ini")))
    val connection = DriverManager.getConnection(props["url"].toString(), props)
    val db = DBMain(connection)
    do {
        println("Choose number from menu:")
        println("1 - show all")
        println("2 - add client")
        println("3 - delete client")
        println("4 - find client")
        println("5 - edit client")
        println("0 - exit")
        val s = readLine()!!.toInt()
        when (s) {
            1 -> db.showAll()
            2 -> {
                println("New Client")
                print("name: ")
                val name = readLine()
                print("address: ")
                val address = readLine()
                print("phone: ")
                val phone = readLine()
                print("intercomType: ")
                val intercomType = readLine()
                print("contractDate: ")
                val contractDate = readLine()
                print("status: ")
                val status = readLine()!!.toBoolean()
                print("tariff: ")
                val tariff = readLine()!!.toInt()
                print("balance: ")
                val balance = readLine()!!.toInt()
                db.add(name, address, phone, intercomType, contractDate, status, tariff, balance)
            }
            3 -> {
                println("Delete client. Enter id:")
                val id = readLine()!!.toInt()
                db.delete(id)
            }
            4 -> {
                println("Find client. Enter id:")
                val id = readLine()!!.toInt()
                val client = db.find(id)
                if (client.id != 0) {
                    println(client)
                } else {
                    println("Client with id $id doesn't exist")
                }
            }
            5 -> {
                println("Edit client. Enter id:")
                val id = readLine()!!.toInt()
                var client = db.find(id)
                if (client.id != 0) {
                    println(client)
                    print("new tariff: ")
                    val newTariff = readLine()!!.toInt()
                    db.updateTariff(client, newTariff)
                    client = db.find(id)
                    println(client)
                }
            }
        }
        println("======================================")
    } while (s>0)
}